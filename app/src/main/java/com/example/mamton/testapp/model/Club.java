package com.example.mamton.testapp.model;

import com.google.gson.annotations.SerializedName;

public class Club extends AbstractEntity {
    @SerializedName("name")
    private String name;

    @SerializedName("place")
    private String place;

    public Club(final long id, final long serverId, final long serverVersion,
            final long localVersion,
            final String name, final String place) {
        super(id, serverId, serverVersion, localVersion);
        this.name = name;
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }
}
