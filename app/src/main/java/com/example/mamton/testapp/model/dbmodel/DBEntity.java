package com.example.mamton.testapp.model.dbmodel;

import com.example.mamton.testapp.model.AbstractEntity;


public class DBEntity extends AbstractEntity {


    DBEntity(final long id, final long serverId, final long serverVersion,
            final long localVersion) {
        super(id, serverId, serverVersion, localVersion);
    }
}
