package com.example.mamton.testapp.gui.dict;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.mamton.testapp.R;
import com.example.mamton.testapp.db.DictionaryFacadeFactory;
import com.example.mamton.testapp.gui.core.ChangebleCursorRecyclerViewAdapter;
import com.example.mamton.testapp.gui.core.DictionaryCursorAdapter;
import com.example.mamton.testapp.model.dbmodel.DBEntity;
import com.example.mamton.testapp.model.dbmodel.DBMetaInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link DictionaryItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class DictionaryItemListActivity extends AbstractDictionaryActivity implements DictionaryListView {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @InjectPresenter
    DictionaryListPresenter presenter;

    @Nullable
    @BindView(R.id.dictionaryitem_detail_container)
    ViewGroup detailView;

    @BindView(R.id.dictionaryitem_list)
    RecyclerView recyclerView;

    private DictionaryCursorAdapter dictionaryCursorAdapter;

    //todo mamton надо ли хранить ссылку на фрагмент в итоге
    @Nullable
    private DictionaryItemDetailFragment itemDetailFragment;

    @NonNull
    public static Intent getIntent(final Context context, final @NonNull DBMetaInfo.Tables item) {
        Intent intent = new Intent(context, DictionaryItemListActivity.class);
        intent.putExtra(EXTRA_META_INFO, item);
        return intent;
    }

    @ProvidePresenter
    DictionaryListPresenter createPresenter() {
        DBMetaInfo.Tables metaInfo = getMetaFromIntent();

        return new DictionaryListPresenter(metaInfo,
                new DictionaryModel(DictionaryFacadeFactory.getFacade(
                        metaInfo,
                        this.getApplicationContext())));
    }

    @Override
    public void startLoading() {
        //todo mamton implement
        Timber.d("Strat loading stub");
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showError(final Throwable throwable) {
        Timber.w(throwable, "On error ");
    }

    @Override
    public void showItems(final Cursor items) {
        recyclerView.setVisibility(View.VISIBLE);
        dictionaryCursorAdapter.changeCursor(items);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dictionaryitem_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTwoPane && itemDetailFragment != null) {
                    itemDetailFragment.showItem(null, getMetaFromIntent());
                } else {
                    Intent intent = DictionaryItemDetailActivity
                            .getIntent(DictionaryItemListActivity.this, getMetaFromIntent(), null);
                    startActivityForResult(intent, DictionaryItemDetailActivity.REQUEST_CODE_CREATE);
                }
            }
        });

        ButterKnife.bind(this);

        recyclerView.setVisibility(View.GONE);
        dictionaryCursorAdapter = new DictionaryCursorAdapter(this, null, getMetaFromIntent());
        dictionaryCursorAdapter.setOnItemClickListener(new ChangebleCursorRecyclerViewAdapter.OnItemClickListener<DBEntity>() {
            @Override
            public void onItemSelected(final DBEntity item, final int position) {
                if (mTwoPane) {
                    if (itemDetailFragment != null) {
                        itemDetailFragment.setActiveItem(item);
                    } else {
                        Timber.w("detail fragment not defined!");
                    }
                } else {
                    Intent intent = DictionaryItemDetailActivity
                            .getIntent(DictionaryItemListActivity.this, getMetaFromIntent(), item
                                    .getId());
                    startActivityForResult(intent, DictionaryItemDetailActivity.REQUEST_CODE_EDIT);
                }
            }
        });
        recyclerView.setAdapter(dictionaryCursorAdapter);

        if (detailView != null) {
            mTwoPane = true;
            itemDetailFragment = DictionaryItemDetailFragment.newInstance(null, getMetaFromIntent());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.dictionaryitem_detail_container, itemDetailFragment)
                    .commit();

        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode,
            final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.reload();
    }
}
