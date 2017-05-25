package com.example.mamton.testapp.gui.core.moxy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Optional;
import com.arellomobile.mvp.MvpDelegate;

public class MvpActivity extends AppCompatActivity {

    private MvpDelegate<? extends MvpActivity> mMvpDelegate;

    @Override
    public void setContentView(final int layoutResID) {
        super.setContentView(layoutResID);
        MoxyUtils.setDelegateToView(findViewById(android.R.id.content), getMvpDelegate());
    }

    @Override
    public void setContentView(final View view, final ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        MoxyUtils.setDelegateToView(findViewById(android.R.id.content), getMvpDelegate());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getMvpDelegate().onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        getMvpDelegate().onDetach();

        //originally was isFinishing()
        if (MvpTools.isFinishing(this)) {
            getMvpDelegate().onDestroy();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        getMvpDelegate().onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();

        getMvpDelegate().onAttach();
    }

    /**
     * @return The {@link MvpDelegate} being used by this Activity.
     */
    public MvpDelegate getMvpDelegate() {
        if (mMvpDelegate == null) {
            mMvpDelegate = new MvpDelegate<>(this);
        }
        return mMvpDelegate;
    }

    @NonNull
    protected final Optional<ActionBar> getActionBarOptional() {
        return Optional.ofNullable(getSupportActionBar());
    }
}

