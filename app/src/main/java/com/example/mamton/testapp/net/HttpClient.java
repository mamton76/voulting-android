package com.example.mamton.testapp.net;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.mamton.testapp.model.Horse;

import java.util.List;

public interface HttpClient {
    @NonNull
    List<Horse> getHorses(@Nullable Long id) throws ConnectionException;
}
