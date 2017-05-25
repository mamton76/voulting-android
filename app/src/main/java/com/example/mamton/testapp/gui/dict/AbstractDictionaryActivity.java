package com.example.mamton.testapp.gui.dict;

import android.support.annotation.NonNull;

import com.example.mamton.testapp.gui.core.moxy.MvpActivity;
import com.example.mamton.testapp.model.dbmodel.DBMetaInfo;

abstract class AbstractDictionaryActivity extends MvpActivity {

    public static final String EXTRA_META_INFO = "metaInfo";

    public static final String EXTRA_SELECTED_ID = "selectedId";

    @NonNull
    protected DBMetaInfo.Tables getMetaFromIntent() {
        DBMetaInfo.Tables res = (DBMetaInfo.Tables) getIntent()
                .getSerializableExtra(EXTRA_META_INFO);
        if (res == null) {
            throw new IllegalStateException("No table metainfo in intent");
        }
        return res;
    }

    @NonNull
    protected Long getSelectedIdFromIntent() {
        return  getIntent().getLongExtra(EXTRA_SELECTED_ID, -1);
    }

}
