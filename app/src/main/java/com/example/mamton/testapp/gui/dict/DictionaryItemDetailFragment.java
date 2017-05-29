package com.example.mamton.testapp.gui.dict;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Stream;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.mamton.testapp.R;
import com.example.mamton.testapp.db.DictionaryFacadeFactory;
import com.example.mamton.testapp.gui.core.moxy.MvpFragment;
import com.example.mamton.testapp.gui.dict.editor.AbstractEditor;
import com.example.mamton.testapp.gui.dict.editor.ValueEditor;
import com.example.mamton.testapp.model.dbmodel.ColumnMetaInfo;
import com.example.mamton.testapp.model.dbmodel.DBEntity;
import com.example.mamton.testapp.model.dbmodel.DBMetaInfo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * A fragment representing a single DictionaryItem detail screen.
 * This fragment is either contained in a {@link DictionaryItemListActivity}
 * in two-pane mode (on tablets) or a {@link DictionaryItemDetailActivity}
 * on handsets.
 */
public class DictionaryItemDetailFragment extends MvpFragment implements DictionaryItemView {

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_ITEM_META = "meta";
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.dictionaryitem_detail)
    ViewGroup editorsContainer;
    @InjectPresenter
    DictionaryItemDetailPresenter presenter;

    private Map<ColumnMetaInfo, ValueEditor> editors = new HashMap<>();

    public static DictionaryItemDetailFragment newInstance(@Nullable final Long id,
            final Serializable meta) {
        DictionaryItemDetailFragment res = new DictionaryItemDetailFragment();
        Bundle arguments = new Bundle();
        if (id != null) {
            arguments.putLong(DictionaryItemDetailFragment.ARG_ITEM_ID, id);
        }
        arguments.putSerializable(DictionaryItemDetailFragment.ARG_ITEM_META, meta);
        res.setArguments(arguments);
        return res;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DictionaryItemDetailFragment() {
    }

    @ProvidePresenter
    DictionaryItemDetailPresenter createPresenter() {
        DBMetaInfo.Tables metaInfo = getMetaFromArgs();

        return new DictionaryItemDetailPresenter(metaInfo,
                new DictionaryModel(DictionaryFacadeFactory.getFacade(
                        metaInfo,
                        this.getContext().getApplicationContext())));
    }

    @Override
    public void startLoading() {
        Timber.d("item start loading stub");
    }

    @Override
    public void showError(final Throwable throwable) {
        Timber.w(throwable, "On error ");
    }

    @Override
    public void showItem(@Nullable final DBEntity item, final DBMetaInfo.Tables meta) {
        if (item != null) {
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity
                    .findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(String.valueOf(item.getId()));
            }

            Stream.ofNullable(editors.entrySet())
                    .forEach(columnEntry -> columnEntry.getValue().setValue(
                            item.getValues().get(columnEntry.getKey())));

        }
    }

    @Override
    public void itemSaved(final Long id) {
        //todo mamton понять как-то по другому что мы просто во фрагменте а не в активити
        if (getActivity() instanceof DictionaryItemDetailActivity) {
            getActivity().finish();
        } else {
            fab.setEnabled(false);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.dictionaryitem_detail, container, false);
        ButterKnife.bind(this, rootView);
        List<ColumnMetaInfo> metaFromArgs = getMetaFromArgs().getMetaInfo().getColumns();
        for (int i = 0; i < metaFromArgs.size(); i++) {
            ColumnMetaInfo columnMetaInfo = metaFromArgs.get(i);
            ValueEditor editor = AbstractEditor
                    .createEditor(editorsContainer, columnMetaInfo, i);
            editor.addChangeListener((value) -> {
                        presenter.setItemFieldValue(editorsContainer.getContext(), value);
                        fab.setEnabled(true);
                    });
            editors.put(columnMetaInfo, editor);
            editorsContainer.addView(editor.getView());
        }

        return rootView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null && getArguments().get(ARG_ITEM_ID) != null) {
            presenter.setActiveItem(getArguments().getLong(ARG_ITEM_ID));
        }
    }

    private DBMetaInfo.Tables getMetaFromArgs() {
        return (DBMetaInfo.Tables) getArguments().getSerializable(ARG_ITEM_META);
    }

    @OnClick(R.id.fab)
    void onSaveClick() {
        presenter.saveActiveItem();
    }

    public void setActiveItem(final long activeItem) {
        presenter.setActiveItem(activeItem);
    }

}
