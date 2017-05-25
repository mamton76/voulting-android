package com.example.mamton.testapp.gui.dict;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpPresenter;
import com.example.mamton.testapp.gui.core.CommonView;
import com.example.mamton.testapp.model.dbmodel.DBMetaInfo;

import rx.subscriptions.CompositeSubscription;

class AbstractDictionaryPresenter<T extends CommonView> extends MvpPresenter<T> {

    @NonNull
    protected final DBMetaInfo.Tables metaInfo;
    @NonNull
    protected final DictionaryModel model;
    @NonNull
    protected CompositeSubscription subscriptions = new CompositeSubscription();

    AbstractDictionaryPresenter(
            @NonNull final DictionaryModel model, @NonNull final DBMetaInfo.Tables metaInfo) {
        this.model = model;
        this.metaInfo = metaInfo;
    }
}
