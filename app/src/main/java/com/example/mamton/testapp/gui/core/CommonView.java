package com.example.mamton.testapp.gui.core;

import com.arellomobile.mvp.MvpView;

public interface CommonView extends MvpView {

    void startLoading();

    void showError(Throwable throwable);
}
