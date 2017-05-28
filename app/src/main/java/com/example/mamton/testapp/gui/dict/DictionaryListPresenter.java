package com.example.mamton.testapp.gui.dict;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.annimon.stream.Stream;
import com.arellomobile.mvp.InjectViewState;
import com.example.mamton.testapp.model.dbmodel.DBMetaInfo;
import com.example.mamton.testapp.model.dbmodel.filter.Filter;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

@InjectViewState
public class DictionaryListPresenter extends AbstractDictionaryPresenter<DictionaryListView> {

    @Nullable
    private List<Filter> filters;

    public DictionaryListPresenter(@NonNull final DBMetaInfo.Tables metaInfo,
            @NonNull final DictionaryModel model) {
        super(model, metaInfo);
    }

    public void applyFilters(@Nullable List<Filter> filters) {
        this.filters = filters;
        reload();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        reload();
    }

    public void reload() {
        getViewState().startLoading();
        StringBuilder where = new StringBuilder();
        List<String> args = new ArrayList<>();
        Stream.ofNullable(filters).forEach(filter -> {
            where.append(filter.getWhereArguments());
            args.addAll(filter.getWhereArguments());
        });
        Subscription subscription =
                model.getItems(where.toString(), args.toArray(new String[args.size()]))
                .subscribe(
                        items -> getViewState().showItems(items),
                        throwable -> getViewState().showError(throwable)
                );
        subscriptions.add(subscription);
    }


}
