package com.example.mamton.testapp.gui.dict;

import android.database.Cursor;

import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.mamton.testapp.gui.core.CommonView;


@StateStrategyType(SingleStateStrategy.class)
public interface DictionaryListView extends CommonView {

    @StateStrategyType(SingleStateStrategy.class)
    void showItems(Cursor items);
}
