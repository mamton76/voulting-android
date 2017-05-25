package com.example.mamton.testapp.model;

import android.support.annotation.NonNull;

public class Event extends AbstractEntity{

    public static final Event SAMPLE_EVENT = new Event(1, 1, 1, 1, "today", "event");
    @NonNull
    private String dates;
    @NonNull
    private String name;

    public Event(final long id, final long serverId, final long serverVersion,
            final long localVersion,
            @NonNull final String dates, @NonNull final String name) {
        super(id, serverId, serverVersion, localVersion);
        this.dates = dates;
        this.name = name;
    }

    @NonNull
    public String getDates() {
        return dates;
    }

    @NonNull
    public String getName() {
        return name;
    }
}
