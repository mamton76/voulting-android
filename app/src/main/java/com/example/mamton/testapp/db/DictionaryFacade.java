package com.example.mamton.testapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.example.mamton.testapp.model.dbmodel.DBEntity;

import java.util.List;

//todo implement
public class DictionaryFacade extends AbstractFacade<DBEntity> {


    public DictionaryFacade(@NonNull final Context context) {
        super(context);
    }

    @NonNull
    @Override
    protected String getTableName() {
        return null;
    }

    @NonNull
    @Override
    protected List<String> getColumns() {
        return null;
    }

    @NonNull
    @Override
    protected DBEntity createInstance(@NonNull final Cursor cursor) {
        return null;
    }

    @NonNull
    @Override
    protected ContentValues createContentValues(@NonNull final DBEntity object) {
        return null;
    }
}
