package com.example.mamton.testapp.net;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public abstract class ServiceResponse<T> {
    @SerializedName("errors")
    private List<ServiceError> errors;

    private class ServiceError {
        @SerializedName("code")
        private int code;

    }

    abstract T getContent();
}
