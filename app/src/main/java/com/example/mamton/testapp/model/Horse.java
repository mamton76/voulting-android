package com.example.mamton.testapp.model;

import com.google.gson.annotations.SerializedName;

public class Horse extends AbstractEntity {
    @SerializedName("name")
    private String name;

    public Horse(final long id,
            final long serverId,
            final long serverVersion,
            final long localVersion,
            final String name) {
        super(id, serverId, serverVersion, localVersion);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
