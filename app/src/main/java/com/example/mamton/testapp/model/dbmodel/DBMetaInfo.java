package com.example.mamton.testapp.model.dbmodel;

import android.support.annotation.NonNull;

import com.example.mamton.testapp.db.DB;

import java.util.List;

public class DBMetaInfo {
    enum Tables {
        CLUB(DB.TABLE_CLUB),
        HORSE(DB.TABLE_HORSE);

        private String name;

        Tables(final String tableName) {
            this.name = tableName;
        }

        public String getName() {
            return name;
        }
    }

    public static class TableMetaInfo {
        @NonNull
        private Tables table;
        @NonNull
        private List<ColumnMetaInfo> columns;

        public TableMetaInfo(@NonNull final Tables table,
                @NonNull final List<ColumnMetaInfo> columns) {
            this.table = table;
            this.columns = columns;
        }

        @NonNull
        public Tables getTable() {
            return table;
        }

        @NonNull
        public List<ColumnMetaInfo> getColumns() {
            return columns;
        }
    }

    public static class ColumnMetaInfo {
        public static final int FLAG_MAIN = 0x1;

        @NonNull
        private final String name;
        @NonNull
        private final ColumnType type;
        @NonNull
        private final int flags;

        public ColumnMetaInfo(@NonNull final String name,
                @NonNull final ColumnType type, @NonNull final int flags) {
            this.name = name;
            this.type = type;
            this.flags = flags;
        }
    }

    public static class ColumnFKMetaInfo extends ColumnMetaInfo {
        @NonNull
        private final Tables table;

        ColumnFKMetaInfo(@NonNull final String name,
                @NonNull final ColumnType type, @NonNull final int flags,
                @NonNull final Tables table) {
            super(name, type, flags);
            this.table = table;
        }

        @NonNull
        public Tables getTable() {
            return table;
        }
    }

    enum ColumnType {
        STRING,
        NUMBER,
        FK
    }

}
