package com.example.mamton.testapp.model.dbmodel;

import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

public class FieldValueFactory {
    private static volatile FieldValueFactory instance;

    @NonNull
    private final Context context;

    private FieldValueFactory(@NonNull Context context) {
        this.context = context;
    }

    @NonNull
    public static FieldValueFactory getInstance(@NonNull Context context) {
        if (instance == null) {
            synchronized (FieldValueFactory.class) {
                if (instance == null) {
                    instance = new FieldValueFactory(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    @NonNull
    public DBEntity.FieldValue createFieldValue(final ContentValues otherValues,
            final ColumnMetaInfo meta) {
        DBEntity.FieldValue value = null;
        switch (meta.getType()) {
            case NUMBER:
                value = new DBEntity.FieldValue<Long>(meta, otherValues.getAsLong(meta.getName()));
                break;
            case FK:
                value = new DBEntity.ForeignFieldValue((ColumnFKMetaInfo) meta, otherValues.getAsLong(meta.getName()), context);
                break;
            case STRING:
            default:
                value = new DBEntity.FieldValue<String>(meta, otherValues.getAsString(meta.getName()));
                break;
        }
        return value;
    }
}
