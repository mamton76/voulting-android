package com.example.mamton.testapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.mamton.testapp.model.AbstractEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import timber.log.Timber;

public abstract class AbstractFacade<T extends AbstractEntity> {

    @NonNull
    private final SQLiteDatabase db;

    @NonNull
    private final Context context;

    protected AbstractFacade(@NonNull final Context context) {
        this.context = context.getApplicationContext();
        this.db = DB.instance(this.context).getDb();
    }

    @NonNull
    protected abstract String getTableName();

    @NonNull
    protected abstract List<String> getColumns();

    @NonNull
    private String[] getAllColumns() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList(
                DB.FIELD_COMMON_ID,
                DB.FIELD_COMMON_LOCAL_VERSION,
                DB.FIELD_COMMON_SERVER_ID,
                DB.FIELD_COMMON_SERVER_VERSION));

        list.addAll(getColumns());
        return list.toArray(new String[list.size()]);
    }


    @NonNull
    protected abstract T createInstance(@NonNull Cursor cursor);

    @NonNull
    protected abstract ContentValues createContentValues(@NonNull T object);

    @NonNull
    protected Context getContext() {
        return context;
    }

    @NonNull
    private SQLiteDatabase getDb() {
        return db;
    }

    public long insert(@NonNull T object) {
        long result;
        ContentValues values = createContentValues(object);
        try {
            result = db.insertWithOnConflict(getTableName(), null, values,
                    SQLiteDatabase.CONFLICT_REPLACE);
        } catch (SQLiteException e) {
            Timber.e(e, e.getMessage());
            result = -1;
        }
        return result;
    }

    public boolean remove(@NonNull String key, @NonNull String value) {
        return db.delete(getTableName(), key + "=?", new String[]{value}) > 0;
    }

    public int update(@NonNull T object, @NonNull ContentValues values) {
        return db.update(getTableName(), values, DB.FIELD_COMMON_ID + "=?",
                new String[]{String.valueOf((object).getId())});
    }

    //todo remove?
    @NonNull
    public List<T> getRecords(@Nullable String selection, @Nullable String selectionArgs[],
            @Nullable String orderBy, @Nullable String limit) {

        List<T> result = null;
        try {
            final Cursor cursor = getCursor(selection, selectionArgs, orderBy, limit);
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    result = new ArrayList<>(cursor.getCount());
                    do {
                        result.add(createInstance(cursor));
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
        } catch (SQLiteException e) {
            Timber.e(e, e.getMessage());
        }
        if (result == null) {
            // возвращайте изменяемую коллекцию
            result = new ArrayList<>();
        }
        return result;
    }

    public Cursor getCursor(final @Nullable String selection,
            final @Nullable String[] selectionArgs, final @Nullable String orderBy,
            final @Nullable String limit) {
        return db.query(getTableName(), getAllColumns(),
                selection, selectionArgs, null, null, orderBy, limit);
    }

    public int removeAll() {
        return db.delete(getTableName(), null, null);
    }

    public int countOf(@NonNull String nativeQuery, @Nullable String[] selection) {
        int result = 0;
        Cursor cursor = null;
        try {
            cursor = getDb().rawQuery(nativeQuery, selection);
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    result = cursor.getInt(0);
                }
            }
        } catch (SQLiteException e) {
            Timber.e(e, "query was failed");
            result = 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }
}
