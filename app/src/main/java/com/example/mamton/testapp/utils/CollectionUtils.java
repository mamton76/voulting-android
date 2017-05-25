package com.example.mamton.testapp.utils;

import com.example.mamton.testapp.model.AbstractEntity;

import java.util.Collection;

public class CollectionUtils {

    public static <T extends AbstractEntity> boolean isEmpty(final Collection<T> collection) {
        return (collection == null || collection.size() == 0);
    }
}
