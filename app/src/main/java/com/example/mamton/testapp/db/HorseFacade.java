package com.example.mamton.testapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.example.mamton.testapp.model.Horse;

import java.util.Collections;
import java.util.List;

public class HorseFacade extends AbstractFacade<Horse> {


    protected HorseFacade(@NonNull final Context context) {
        super(context);
    }

    @NonNull
    @Override
    protected String getTableName() {
        return DB.TABLE_HORSE;
    }

    @NonNull
    @Override
    protected List<String> getColumns() {
        return Collections.singletonList(DB.FIELD_HORSE_NAME);
    }

    @NonNull
    @Override
    public Horse createInstance(@NonNull final Cursor cursor) {
        return null;
        /*Horse res = new Horse()
        return cursor.getLong(cursor.getColumnIndexOrThrow());
        MediaStore.Images.Thumbnails.getThumbnail()
        Intent.ACTION_GET_CONTENT*/
    }

    @NonNull
    @Override
    protected ContentValues appendContentValues(@NonNull final Horse object,
            @NonNull final ContentValues values) {
        return values;
    }

}
