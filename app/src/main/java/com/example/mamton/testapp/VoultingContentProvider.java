package com.example.mamton.testapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.FileNotFoundException;

import timber.log.Timber;

public class VoultingContentProvider extends ContentProvider {


    //// UriMatcher
    // общий Uri
    static final int URI_HORSES = 1;
    // Uri с указанным ID
    static final int URI_HORSES_ID = 2;
    private static final String AUTHORITY = "com.example.mamton.VoultingData";
    private static final UriMatcher uriMatcher;
    private static final String HORSES_PATH = "Horses";
    // Общий Uri
    public static final Uri CONTACT_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + HORSES_PATH);
    // Типы данных
    // набор строк
    static final String HORSE_CONTENT_TYPE = "vnd.android.cursor.dir/vnd."
            + AUTHORITY + "." + HORSES_PATH;
    // одна строка
    static final String HORSE_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."
            + AUTHORITY + "." + HORSES_PATH;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, HORSES_PATH, URI_HORSES);
        uriMatcher.addURI(AUTHORITY, HORSES_PATH + "/#", URI_HORSES_ID);
    }

    public VoultingContentProvider() {

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case URI_HORSES:
                throw new UnsupportedOperationException("Not yet implemented");
            case URI_HORSES_ID:
                return 1;
        }
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        Timber.d("getType, %s", uri.toString());
        switch (uriMatcher.match(uri)) {
            case URI_HORSES:
                return HORSE_CONTENT_TYPE;
            case URI_HORSES_ID:
                return HORSE_CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {

        // TODO: Implement this to handle query requests from clients.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
            String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable
    @Override
    public ParcelFileDescriptor openFile(@NonNull final Uri uri, @NonNull final String mode) throws
            FileNotFoundException {
        return super.openFile(uri, mode);
    }
}
