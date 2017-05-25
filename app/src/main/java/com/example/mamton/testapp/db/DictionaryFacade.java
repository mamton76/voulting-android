package com.example.mamton.testapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.annimon.stream.Stream;
import com.example.mamton.testapp.model.dbmodel.DBEntity;
import com.example.mamton.testapp.model.dbmodel.FieldValueFactory;
import com.example.mamton.testapp.model.dbmodel.TableMetaInfo;

import java.util.List;

public class DictionaryFacade extends AbstractFacade<DBEntity> {

    private final TableMetaInfo tableMetaInfo;

    public DictionaryFacade(@NonNull final Context context,
            @NonNull TableMetaInfo tableMetaInfo) {
        super(context);
        this.tableMetaInfo = tableMetaInfo;
    }

    @NonNull
    @Override
    protected String getTableName() {
        return tableMetaInfo.getTable().getName();
    }

    @NonNull
    @Override
    protected List<String> getColumns() {
        return Stream.ofNullable(tableMetaInfo.getColumns())
                .map(meta -> meta.getName())
                .toList();
    }

    @NonNull
    @Override
    //Для ключей подсасывает зависимые зачения - поэтому может занять какоре-то время
    //todo mamton - подсасывать отображаемые значения для клюучей во время запроса к таблице
    public DBEntity createInstance(@NonNull final Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndexOrThrow(DB.FIELD_COMMON_ID));
        long serverId = cursor.getLong(cursor.getColumnIndexOrThrow(DB.FIELD_COMMON_SERVER_ID));
        long serverVersion = cursor
                .getLong(cursor.getColumnIndexOrThrow(DB.FIELD_COMMON_SERVER_VERSION));
        long localVersion = cursor
                .getLong(cursor.getColumnIndexOrThrow(DB.FIELD_COMMON_LOCAL_VERSION));
        ContentValues values = new ContentValues();
        Stream.ofNullable(tableMetaInfo.getColumns())
                .forEach(meta -> {
                    switch (meta.getType()) {
                        case NUMBER:
                        case FK:
                            values.put(meta.getName(), cursor
                                    .getLong(cursor.getColumnIndexOrThrow(meta.getName())));
                            break;
                        case STRING:
                        default:
                            values.put(meta.getName(), cursor
                                    .getString(cursor.getColumnIndexOrThrow(meta.getName())));
                    }
                });
        return new DBEntity(id, serverId, serverVersion, localVersion, tableMetaInfo, values,
                FieldValueFactory.getInstance(getContext()));
    }

    @NonNull
    @Override
    protected ContentValues appendContentValues(@NonNull final DBEntity object,
            @NonNull final ContentValues values) {
        Stream.ofNullable(object.getValues().entrySet())
                .filter(entry -> (entry.getValue() == null))
                .forEach(entry -> {
                    switch (entry.getKey().getType()) {
                        case NUMBER :
                        case FK:
                            values.put(entry.getKey().getName(),(Long) (entry.getValue().getValue()));
                            break;
                        default:
                            values.put(entry.getKey().getName(),(String) (entry.getValue().getValue()));
                    }
                });
        return values;
    }
}
