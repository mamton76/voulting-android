package com.example.mamton.testapp.model;

public class Person extends AbstractEntity {

    Person(final long id, final long serverId, final long serverVersion,
            final long localVersion) {
        super(id, serverId, serverVersion, localVersion);
    }
}
