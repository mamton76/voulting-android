package com.example.mamton.testapp.gui.core.moxy;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewParent;

import com.arellomobile.mvp.MvpDelegate;
import com.example.mamton.testapp.R;

public class MoxyUtils {

    @IdRes
    private static final int DELEGATE = R.id.moxy_delegate;

    public static void setDelegateToView(@NonNull final View view,
            @Nullable final MvpDelegate delegate) {
        view.setTag(DELEGATE, delegate);
    }

    @NonNull
    public static MvpDelegate getParentDelegate(@NonNull final View view) {
        MvpDelegate delegate = (MvpDelegate) view.getTag(MoxyUtils.DELEGATE);
        if (delegate != null) {
            return delegate;
        } else {
            ViewParent parent = view.getParent();
            if (parent != null && parent instanceof View) {
                return getParentDelegate((View) view.getParent());
            } else {
                throw new RuntimeException("Mvp view couldn't find parent delegate!");
            }
        }
    }
}
