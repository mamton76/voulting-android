package com.example.mamton.testapp.gui.dict.editor;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.InputType;

import com.example.mamton.testapp.model.dbmodel.ColumnMetaInfo;
import com.example.mamton.testapp.model.dbmodel.DBEntity;

public class StringEditor extends AbstractEditor<String> {
    public static StringEditor getInstance(Context context, ColumnMetaInfo meta) {
        return new StringEditor(context, meta);
    }


    private StringEditor(final Context context, final ColumnMetaInfo meta) {
        super(meta, context);
        editValue.setInputType(InputType.TYPE_CLASS_TEXT);
        //todo mamton установить максимальное число знаков и так далее
    }

    @Nullable
    @Override
    public DBEntity.FieldValue<String> getValue() {
        String res = editValue.getText().toString().trim();
        return new DBEntity.FieldValue<String>(meta, (res.length() == 0 ? null : res));
    }
}
