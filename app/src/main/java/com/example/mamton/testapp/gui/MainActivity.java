package com.example.mamton.testapp.gui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.mamton.testapp.R;
import com.example.mamton.testapp.gui.dict.DictionaryItemListActivity;
import com.example.mamton.testapp.model.Event;
import com.example.mamton.testapp.model.dbmodel.DBMetaInfo;
import com.facebook.stetho.Stetho;
import com.idescout.sql.SqlScoutServer;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

//todo решить куда засунуть этих листенеров (кажется их место все же в самих фрагментах)
public class MainActivity extends AppCompatActivity implements
        DictFragment.OnListFragmentInteractionListener,
        EventsFragment.OnListFragmentInteractionListener {

    @BindView(R.id.message)
    @NonNull
    TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            boolean res = false;
            Fragment fr = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    fr = EventsFragment.newInstance(1);
                    res = true;
                    break;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    fr = DictFragment.newInstance(1);
                    res = true;
                    break;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    res = true;
                    break;
            }

            if (res && fr != null) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragmentPlace, fr)
                        .commit();
                res = fragmentManager.executePendingTransactions();
            }

            return res;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SqlScoutServer.create(this, getPackageName());
        Stetho.initializeWithDefaults(this.getApplicationContext());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onListFragmentInteraction(final Event item) {
        Timber.d("item: %s", item);
    }

    @Override
    public void onListFragmentInteraction(final @NonNull DBMetaInfo.Tables item) {
        //todo mamton ? а надо
        //startActivityForResult(DictionaryItemListActivity.getIntent(item), DictionaryItemListActivity.);
        Intent intent = DictionaryItemListActivity.getIntent(this, item);
        startActivity(intent);
    }

}
