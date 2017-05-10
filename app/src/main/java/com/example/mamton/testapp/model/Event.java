package com.example.mamton.testapp.model;

public class Event extends AbstractEntity{
    private String dates;

    Event(final long id, final long serverId, final long serverVersion,
            final long localVersion) {
        super(id, serverId, serverVersion, localVersion);
    }
}
