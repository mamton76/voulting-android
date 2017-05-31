package com.example.mamton.testapp.model.dbmodel;

import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.annimon.stream.Stream;
import com.example.mamton.testapp.db.DictionaryFacade;
import com.example.mamton.testapp.db.DictionaryFacadeFactory;
import com.example.mamton.testapp.model.AbstractEntity;
import com.example.mamton.testapp.utils.StreamApi;

import java.util.HashMap;
import java.util.Map;


public class DBEntity extends AbstractEntity {
    @NonNull
    private final Map<ColumnMetaInfo, FieldValue> values = new HashMap<>();

    public DBEntity(final long id, final long serverId, final long serverVersion,
            final long localVersion, TableMetaInfo tableMeta,
            ContentValues otherValues, FieldValueFactory valueFactory) {
        super(id, serverId, serverVersion, localVersion);
        StreamApi.safeOf(tableMeta.getColumns())
                .forEach(meta -> {
                    FieldValue value = valueFactory.createFieldValue(otherValues, meta);
                    values.put(meta, value);
                });
    }

    @NonNull
    public Map<ColumnMetaInfo, FieldValue> getValues() {
        return values;
    }

    public void setFieldValue(@NonNull final FieldValue fieldValue) {
        FieldValue fieldValuePrev = values.get(fieldValue.getMeta());
        if (isValueEmpty(fieldValue) && !isValueEmpty(fieldValuePrev)
            || !isValueEmpty(fieldValue) && (isValueEmpty(fieldValuePrev) ||
                !fieldValue.getValue().equals(fieldValuePrev.getValue()))) {
            values.put(fieldValue.getMeta(), fieldValue);
            localVersion ++;
        }
    }

    public static boolean isValueEmpty(final @Nullable FieldValue fieldValue) {
        return (fieldValue == null) || fieldValue.getValue() == null;
    }

    public static class ForeignFieldValue extends FieldValue<Long> {

        private final String shownValue;

        public ForeignFieldValue(@NonNull final ColumnFKMetaInfo meta,
                @NonNull final Long value, @NonNull final Context context) {
            super(meta, value);
            DBMetaInfo.Tables table = meta.getTable();
            DictionaryFacade facade = DictionaryFacadeFactory.getFacade(table, context);
            DBEntity foreignEntity = facade.getRecordByLocalId(value);
            if (foreignEntity != null) {
                StringBuilder res = new StringBuilder();
                Stream.ofNullable(table.getMetaInfo().getColumns())
                        .filter(columnMeta ->
                                ((columnMeta.getFlags() & ColumnMetaInfo.FLAG_MAIN) == ColumnMetaInfo.FLAG_MAIN)
                                        && foreignEntity.getValues().get(columnMeta) != null)
                        .forEach(columnMeta -> res.append(foreignEntity.getValues().get(columnMeta).getShownValue() + ' '));
                this.shownValue = res.toString();
            } else {
                this.shownValue = "";
            }

        }

        @NonNull
        @Override
        public String getShownValue() {
            return (shownValue == null) ? "" : shownValue;
        }
    }

    public static class FieldValue<T> {
        @NonNull
        private final ColumnMetaInfo meta;
        @Nullable
        private final T value;

        public FieldValue(@NonNull final ColumnMetaInfo meta, final T value) {
            this.meta = meta;
            this.value = value;
        }

        @Nullable
        public T getValue() {
            return value;
        }

        @NonNull
        public String getShownValue() {
            return String.valueOf(value);
        }

        @NonNull
        public ColumnMetaInfo getMeta() {
            return meta;
        }
    }


}
