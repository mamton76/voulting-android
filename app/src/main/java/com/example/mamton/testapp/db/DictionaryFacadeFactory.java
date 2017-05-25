package com.example.mamton.testapp.db;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.mamton.testapp.model.dbmodel.DBMetaInfo;

import java.util.concurrent.ConcurrentHashMap;

public class DictionaryFacadeFactory {
    private static ConcurrentHashMap<DBMetaInfo.Tables, DictionaryFacade> facades = new ConcurrentHashMap<>();

    public static DictionaryFacade getFacade(@NonNull DBMetaInfo.Tables table, @NonNull Context context) {
        DictionaryFacade facade = facades.get(table);
        if (facade == null) {
            facade = facades.putIfAbsent(table,
                    new DictionaryFacade(context.getApplicationContext(), table.getMetaInfo()));
        }
        return facade;
    }

}
