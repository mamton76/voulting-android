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
            DictionaryFacade value = new DictionaryFacade(context.getApplicationContext(),
                    table.getMetaInfo());
            facade = facades.putIfAbsent(table, value);
            if (facade == null) {
                facade = value;
            }
        }
        return facade;
    }

}
