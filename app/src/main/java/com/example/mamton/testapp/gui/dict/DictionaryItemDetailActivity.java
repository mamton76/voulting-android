package com.example.mamton.testapp.gui.dict;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.mamton.testapp.R;
import com.example.mamton.testapp.model.dbmodel.DBMetaInfo;

/**
 * An activity representing a single DictionaryItem detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link DictionaryItemListActivity}.
 */
public class DictionaryItemDetailActivity extends AbstractDictionaryActivity {
    public static final int REQUEST_CODE_EDIT = 1;
    public static final int REQUEST_CODE_CREATE = 2;

    @NonNull
    public static Intent getIntent(final Context context,
            final @NonNull DBMetaInfo.Tables meta, @Nullable  final Long id) {
        Intent intent = new Intent(context, DictionaryItemDetailActivity.class);
        intent.putExtra(EXTRA_META_INFO, meta);
        intent.putExtra(EXTRA_SELECTED_ID, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionaryitem_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its editorsContainer so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            DictionaryItemDetailFragment fragment = DictionaryItemDetailFragment.newInstance(
                    getSelectedIdFromIntent(), getMetaFromIntent());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.dictionaryitem_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, DictionaryItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
