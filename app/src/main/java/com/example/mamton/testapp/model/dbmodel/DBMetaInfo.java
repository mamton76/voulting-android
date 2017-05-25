package com.example.mamton.testapp.model.dbmodel;

import android.support.annotation.NonNull;

import com.example.mamton.testapp.R;
import com.example.mamton.testapp.db.DB;

import java.util.Arrays;

public class DBMetaInfo {
    public enum Tables {
        CLUB(new TableMetaInfo(TableNames.CLUB,
                Arrays.asList(
                        new ColumnMetaInfo(DB.FIELD_CLUB_NAME, ColumnMetaInfo.ColumnType.STRING,
                                ColumnMetaInfo.FLAG_MAIN | ColumnMetaInfo.FLAG_MODIFIABLE),
                        new ColumnMetaInfo(DB.FIELD_CLUB_PLACE, ColumnMetaInfo.ColumnType.STRING,
                                ColumnMetaInfo.FLAG_MAIN | ColumnMetaInfo.FLAG_MODIFIABLE))),
                R.string.title_dictionary_club),
        HORSE(new TableMetaInfo(TableNames.HORSE,
                Arrays.asList(
                        new ColumnMetaInfo(DB.FIELD_HORSE_NAME, ColumnMetaInfo.ColumnType.STRING,
                                ColumnMetaInfo.FLAG_MAIN | ColumnMetaInfo.FLAG_MODIFIABLE),
                        new ColumnMetaInfo(DB.FIELD_HORSE_CLUB_ID, ColumnMetaInfo.ColumnType.FK,
                                ColumnMetaInfo.FLAG_MAIN | ColumnMetaInfo.FLAG_MODIFIABLE))),
                R.string.title_dictionary_horse);

        @NonNull
        private final TableMetaInfo meta;

        Tables(@NonNull final TableMetaInfo meta, final int tableNameResource) {
            this.meta = meta;
        }

        public TableMetaInfo getMetaInfo() {
            return meta;
        }

        public enum TableNames {
            CLUB(DB.TABLE_CLUB),
            HORSE(DB.TABLE_HORSE);

            private String name;

            TableNames(final String tableName) {
                this.name = tableName;
            }

            public String getName() {
                return name;
            }
        }
    }
}
