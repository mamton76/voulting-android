package com.example.mamton.testapp.test;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtils {

    @NonNull
    public static List<Byte> toList(final byte[] s2b) {
        List<Byte> lbytes = new ArrayList<>(s2b.length);
        for (int j = 0; j < s2b.length; j++) {
            lbytes.add(s2b[j]);
        }
        return lbytes;
    }
}
