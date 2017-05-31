package com.example.mamton.testapp.model.dbmodel;

import android.support.annotation.NonNull;

public class ColumnFKMetaInfo extends ColumnMetaInfo {
    @NonNull
    private final DBMetaInfo.Tables table;

    ColumnFKMetaInfo(@NonNull final String name,
            final int flags,
            @NonNull final DBMetaInfo.Tables table) {
        super(name, ColumnType.FK, flags);
        this.table = table;
    }

    @NonNull
    public DBMetaInfo.Tables getTable() {
        return table;
    }
}
