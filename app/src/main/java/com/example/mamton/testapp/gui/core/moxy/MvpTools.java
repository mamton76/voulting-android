package com.example.mamton.testapp.gui.core.moxy;


import android.app.Activity;
import android.support.annotation.NonNull;

public class MvpTools {

    //originally was activity.isFinishing()
    public static boolean isFinishing(@NonNull final Activity activity) {
        return !activity.isChangingConfigurations();
    }
}
