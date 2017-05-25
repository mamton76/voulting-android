package com.example.mamton.testapp.model.dbmodel;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

//todo mamton Parcelable
public class TableMetaInfo implements Serializable {
    @NonNull
    private DBMetaInfo.Tables.TableNames table;
    @NonNull
    private List<ColumnMetaInfo> columns;

    public TableMetaInfo(@NonNull final DBMetaInfo.Tables.TableNames table,
            @NonNull final List<ColumnMetaInfo> columns) {
        this.table = table;
        this.columns = columns;
    }

    @NonNull
    public DBMetaInfo.Tables.TableNames getTable() {
        return table;
    }

    @NonNull
    public List<ColumnMetaInfo> getColumns() {
        return columns;
    }
}
