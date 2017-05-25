package com.example.mamton.testapp.gui.dict;

import android.database.Cursor;

import com.example.mamton.testapp.gui.core.CommonView;


public interface DictionaryListView extends CommonView {

    void showItems(Cursor items);
}
