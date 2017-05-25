package com.example.mamton.testapp.gui.dict.editor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.example.mamton.testapp.R;
import com.example.mamton.testapp.model.dbmodel.ColumnMetaInfo;
import com.example.mamton.testapp.model.dbmodel.DBEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class AbstractEditor<T> implements ValueEditor<DBEntity.FieldValue<T>> {

    protected final View view;
    protected final ColumnMetaInfo meta;
    @BindView(R.id.name)
    TextView title;
    @BindView(R.id.value)
    EditText editValue;

    //todo mamton weak?
    private List<OnValueChangeListener> listeners = new ArrayList<>();

    @NonNull
    public static ValueEditor createEditor(final ViewGroup container,
            final ColumnMetaInfo columnMetaInfo) {
        ValueEditor editor;
        switch (columnMetaInfo.getType()) {
            case FK:
                //todo mamton implement
            case NUMBER:
                editor = NumberEditor
                        .getInstance(container.getContext(), columnMetaInfo);
                break;
            default:
                editor = StringEditor
                        .getInstance(container.getContext(), columnMetaInfo);
        }
        return editor;
    }

    AbstractEditor(final ColumnMetaInfo meta, final Context context) {
        this.meta = meta;
        view = LayoutInflater.from(context).inflate(R.layout.field_editor, null);
        ButterKnife.bind(this, view);
        editValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence s, final int start, final int count,
                    final int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, final int start, final int before,
                    final int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {
                Stream.ofNullable(listeners)
                        .forEach((listener) -> listener.valueWasChanged(getValue()));
            }
        });
        title.setText(meta.getName());

    }

    @NonNull
    @Override
    public final View getView() {
        return view;
    }

    @Override
    public void setValue(@Nullable final DBEntity.FieldValue<T> value) {
        editValue.setText((value == null) ? "" : String.valueOf(value));
    }

    @Override
    public void addChangeListener(final OnValueChangeListener listener) {
        listeners.add(listener);
    }
}
