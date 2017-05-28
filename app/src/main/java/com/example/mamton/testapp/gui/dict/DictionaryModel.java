package com.example.mamton.testapp.gui.dict;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.mamton.testapp.db.DictionaryFacade;
import com.example.mamton.testapp.model.dbmodel.DBEntity;

import rx.Observable;

public class DictionaryModel {

    @NonNull
    private final DictionaryFacade facade;


    public DictionaryModel(@NonNull final DictionaryFacade facade) {
        this.facade = facade;
    }

    @NonNull
    public Observable<Cursor> getItems(@Nullable final String selection,
            @Nullable final String[] selectionArgs) {
        return Observable.fromCallable(() -> facade.getCursor(selection, selectionArgs, null, null));
    }

    @NonNull
    public Observable<DBEntity> getItem(long id) {
        return Observable.fromCallable(() -> facade.getRecordByLocalId(id));
    }

    @Nullable
    public Observable<DBEntity> getItem(@NonNull Cursor cursor) {
        return Observable.fromCallable(() ->facade.createInstance(cursor));
    }

    public Observable<Long> saveItem(final DBEntity activeItem) {
        return Observable.fromCallable(() -> facade.insert(activeItem));
    }
}
