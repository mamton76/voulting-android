package com.example.mamton.testapp.model;

import com.google.gson.annotations.SerializedName;

public abstract class AbstractEntity {

    private static final int NOT_DEFINED = -1;

    @SerializedName("localId")
    private long id = NOT_DEFINED;
    @SerializedName("id")
    private long serverId = NOT_DEFINED;
    @SerializedName("version")
    private long serverVersion = NOT_DEFINED;
    @SerializedName("localVersion")
    private long localVersion = NOT_DEFINED;

    protected AbstractEntity(final long id, final long serverId, final long serverVersion,
            final long localVersion) {
        this.id = id;
        this.serverId = serverId;
        this.serverVersion = serverVersion;
        this.localVersion = localVersion;
    }


    public long getId() {
        return id;
    }

    public long getServerId() {
        return serverId;
    }

    public long getServerVersion() {
        return serverVersion;
    }

    public long getLocalVersion() {
        return localVersion;
    }
}
