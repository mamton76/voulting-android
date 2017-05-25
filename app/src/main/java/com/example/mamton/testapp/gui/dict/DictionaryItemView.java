package com.example.mamton.testapp.gui.dict;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.mamton.testapp.gui.core.CommonView;
import com.example.mamton.testapp.model.dbmodel.DBEntity;
import com.example.mamton.testapp.model.dbmodel.DBMetaInfo;


public interface DictionaryItemView extends CommonView {

    void showItem(@Nullable DBEntity item, @NonNull DBMetaInfo.Tables meta);

    void itemSaved(Long id);
}
