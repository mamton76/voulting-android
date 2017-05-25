package com.example.mamton.testapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.LruCache;

import com.example.mamton.testapp.model.AbstractEntity;
import com.example.mamton.testapp.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import timber.log.Timber;

public abstract class AbstractFacade<T extends AbstractEntity> {

    /* todo mamton Использовать конкуртентый кэш + сделать кэширование отдельно
    ConcurrentMap<K, V> cache = new ConcurrentLinkedHashMap.Builder<K, V>()
            .maximumWeightedCapacity(1000)
            .build();*/

    @NonNull
    private final SQLiteDatabase db;
    @NonNull
    private final Context context;
    private LruCache<Long, T> localCache = new LruCache<Long, T>(100);

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
    public abstract T createInstance(@NonNull Cursor cursor);

    private final ContentValues buildContentValues(@NonNull T object) {
        ContentValues values = new ContentValues();
        if (object.getId() != -1) {
            values.put(DB.FIELD_COMMON_ID, object.getId());
            values.put(DB.FIELD_COMMON_LOCAL_VERSION, object.getId());
        }
        values.put(DB.FIELD_COMMON_SERVER_ID, object.getServerId());
        values.put(DB.FIELD_COMMON_SERVER_VERSION, object.getServerVersion());

        appendContentValues(object, values);
        return values;
    }

    @NonNull
    protected abstract ContentValues appendContentValues(@NonNull T object,
            @NonNull ContentValues values);

    @NonNull
    protected Context getContext() {
        return context;
    }

    @NonNull
    private SQLiteDatabase getDb() {
        return db;
    }

    public final long insert(@NonNull T object) {
        long result;
        ContentValues values = buildContentValues(object);
        try {
            result = db.insertWithOnConflict(getTableName(), null, values,
                    SQLiteDatabase.CONFLICT_REPLACE);
            localCache.put(result, object);
        } catch (SQLiteException e) {
            Timber.e(e, e.getMessage());
            result = -1;
        }
        return result;
    }

    @Nullable
    public final T getRecordByLocalId(@NonNull Long id) {
        List<T> records = getRecords(
                DB.FIELD_COMMON_ID + "=?", new String[]{String.valueOf(id)}, null, null);
        return (CollectionUtils.isEmpty(records)) ? null : records.get(0);
    }

    public final boolean removeByLocalId(@NonNull Long value) {
        return remove(DB.FIELD_COMMON_ID, value);
    }


    public int update(@NonNull T object, @NonNull ContentValues values) {
        return db.update(getTableName(), values, DB.FIELD_COMMON_ID + "=?",
                new String[]{String.valueOf((object).getId())});
    }

    private boolean remove(@NonNull String key, @NonNull Long value) {
        return db.delete(getTableName(), key + "=?", new String[]{String.valueOf(value)}) > 0;
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
