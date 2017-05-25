package com.example.mamton.testapp.gui.dict.editor;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.mamton.testapp.model.dbmodel.DBEntity;

public interface ValueEditor<T extends DBEntity.FieldValue> {
    void setValue(@Nullable T value);
    @Nullable
    T getValue();
    @NonNull
    View getView();

    void addChangeListener(OnValueChangeListener o);

    interface OnValueChangeListener<M extends DBEntity.FieldValue> {
        void valueWasChanged(M newValue);
    }
}
