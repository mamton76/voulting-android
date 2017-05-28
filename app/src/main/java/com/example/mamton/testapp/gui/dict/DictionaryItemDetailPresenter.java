package com.example.mamton.testapp.gui.dict;

import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.arellomobile.mvp.InjectViewState;
import com.example.mamton.testapp.model.AbstractEntity;
import com.example.mamton.testapp.model.dbmodel.DBEntity;
import com.example.mamton.testapp.model.dbmodel.DBMetaInfo;
import com.example.mamton.testapp.model.dbmodel.FieldValueFactory;

import rx.Subscription;

@InjectViewState
public class DictionaryItemDetailPresenter extends AbstractDictionaryPresenter<DictionaryItemView>  {

    @Nullable
    private DBEntity activeItem;

    public DictionaryItemDetailPresenter(
            final DBMetaInfo.Tables metaInfo,
            @NonNull final DictionaryModel dictionaryModel) {
        super(dictionaryModel, metaInfo);
    }

    public void setActiveItem(final long activeItemId) {
        getViewState().startLoading();
        Subscription subscription = model.getItem(activeItemId).subscribe(
                (item) -> {
                    this.activeItem = item;
                    getViewState().showItem(item, metaInfo);
                },
                throwable -> getViewState().showError(throwable)
        );
        subscriptions.add(subscription);
    }

    public void setItemFieldValue(@NonNull Context context, @NonNull DBEntity.FieldValue value) {
        if (activeItem == null) {
            activeItem = new DBEntity(AbstractEntity.NOT_DEFINED,
                    AbstractEntity.NOT_DEFINED,
                    AbstractEntity.NOT_DEFINED,
                    AbstractEntity.NOT_DEFINED,
                    metaInfo.getMetaInfo(), new ContentValues(),
                    FieldValueFactory.getInstance(context));
        }
        activeItem.setFieldValue(value);
    }

    @Nullable
    public DBEntity getActiveItem() {
        return activeItem;
    }

    public void saveActiveItem() {
        //todo mamton validate item
        Subscription subscription = model
                .saveItem(activeItem).subscribe(
                        id -> getViewState().itemSaved(id),
                        throwable -> getViewState().showError(throwable)
                );
        subscriptions.add(subscription);
    }
}
