package com.example.mamton.testapp.gui.dict.editor;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.InputType;

import com.example.mamton.testapp.model.AbstractEntity;
import com.example.mamton.testapp.model.dbmodel.ColumnMetaInfo;
import com.example.mamton.testapp.model.dbmodel.DBEntity;

public class NumberEditor extends AbstractEditor<Long> {
    public static NumberEditor getInstance(Context context, ColumnMetaInfo meta, final int id) {
        return new NumberEditor(context, meta, id);
    }


    private NumberEditor(final Context context, final ColumnMetaInfo meta, final int id) {
        super(meta, context, id);
        editValue.setInputType(InputType.TYPE_CLASS_NUMBER);
        //todo mamton установить максимальное число знаков и так далее
    }

    @Nullable
    @Override
    public DBEntity.FieldValue<Long> getValue() {
        String res = editValue.getText().toString().trim();
        Long aLong = res.length() == 0 ? AbstractEntity.NOT_DEFINED : Long.parseLong(res);
        if (aLong == AbstractEntity.NOT_DEFINED) {
            aLong = null;
        }
        return new DBEntity.FieldValue<Long>(meta, aLong);
    }
}
