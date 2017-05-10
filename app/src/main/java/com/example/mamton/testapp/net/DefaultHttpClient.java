package com.example.mamton.testapp.net;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.mamton.testapp.model.Horse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class DefaultHttpClient implements HttpClient {

    private OkHttpClient client = new OkHttpClient();

    @NonNull
    @Override
    public List<Horse> getHorses(@Nullable final Long id) throws ConnectionException,
            ServiceException {
        Call call = client.newCall(
                new Request.Builder()
                        .url("http://mamton.myjino.ru/get_horses.php")
                        .build());
        try {
            okhttp3.Response response = call.execute();
            int code = response.code();
            if (code < 500) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                HorsesResponse horsesResponse = gson.fromJson(new InputStreamReader(response.body()
                        .byteStream()), HorsesResponse.class);
                return horsesResponse.getContent();
            } else {
                throw new ServiceException();
            }
        } catch (IOException e) {
            throw new ConnectionException();
        }

    }

    public static class HorsesResponse extends ServiceResponse<List<Horse>> {

        @SerializedName("horses")
        private List<Horse> horses;

        @Override
        List<Horse> getContent() {
            return (horses == null) ? Collections.<Horse>emptyList() : horses;
        }
    }
}
