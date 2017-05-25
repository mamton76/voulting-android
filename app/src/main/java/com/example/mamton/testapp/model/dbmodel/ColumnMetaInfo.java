package com.example.mamton.testapp.model.dbmodel;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class ColumnMetaInfo implements Serializable {

    public static final int FLAG_MAIN = 0x1;
    public static final int FLAG_MODIFIABLE = 0x2;
    public static final int FLAG_REQUIRED = 0x4;
    public static final int FLAG_SYSTEM = 0x8;

    @NonNull
    private final String name;
    @NonNull
    private final ColumnType type;
    @NonNull
    private final int flags;

    public ColumnMetaInfo(@NonNull final String name,
            @NonNull final ColumnType type,
             final int flags) {
        this.name = name;
        this.type = type;
        this.flags = flags;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public ColumnType getType() {
        return type;
    }

    public int getFlags() {
        return flags;
    }

    public enum ColumnType {
        STRING,
        NUMBER,
        FK
    }
}
