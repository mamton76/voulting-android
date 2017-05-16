package com.example.mamton.testapp.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.example.mamton.testapp.utils.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import timber.log.Timber;


public class DB {

    public static final String TABLE_CLUB = "CLUB";
    public static final String TABLE_HORSE = "HORSE";
    public static final String TABLE_EVENT = "EVENT";
    public static final String TABLE_PERSON = "PERSON";
    public static final String TABLE_TEAM = "TEAM";
    public static final String TABLE_PARTICIPIANT = "PARTICIPIANT";

    public static final String FIELD_COMMON_ID = "ID";
    public static final String FIELD_COMMON_SERVER_ID = "SERVER_ID";
    public static final String FIELD_COMMON_SERVER_VERSION = "SERVER_VERSION";
    public static final String FIELD_COMMON_LOCAL_VERSION = "LOCAL_VERSION";

    public static final String FIELD_CLUB_NAME = "NAME";

    public static final String FIELD_HORSE_NAME = "NAME";
    private static final String FIELD_HORSE_CLUB_ID = "FIELD_HORSE_CLUB_ID";

    public static final String FIELD_EVENT_DATES = "DATES";
    public static final String FIELD_EVENT_NAME = "NAME";

    public static final String FIELD_PERSON_NAME = "NAME";

    public static final String FIELD_PARTICIPIANT_EVENT = "EVENT_ID";
    public static final String FIELD_PARTICIPIANT_HORSE = "HORSE_ID";

    public static final String FIELD_TEAM_PERSON = "PERSON_ID";
    public static final String FIELD_TEAM_PARTICIPIANT = "PARTICIPIANT_ID";

    private static final String DATABASE_PATH = "db_path";  //shared preferences key
    private static final String DATABASE_NAME = "SPORT_VOULTING";
    private static final int MIN_SUPPORTED_DATABASE_VERSION = 0;
    private static final int DATABASE_VERSION = 1;
    private static DB me;
    private SQLiteDatabase db;

    public static synchronized DB instance(Context context) {
        if (me == null) {
            me = new DB(context);
        }
        return me;
    }

    private DB(Context context) {
        String path = handleDb(context);
        db = createDB(path);
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    private String handleDb(Context ctx) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        String savedDbPath = prefs.getString(DATABASE_PATH, null);

        String newDbPath;
        // avoid possible symlinks, . and ..
        try {
            newDbPath = ctx.getDatabasePath(DATABASE_NAME).getCanonicalPath();
        } catch (Exception e) {
            newDbPath = ctx.getDatabasePath(DATABASE_NAME).getPath();
        }

        Timber.i("savedDbPath:%1$s  newDbPath:%2$s", savedDbPath, newDbPath);

        if (savedDbPath != null) {
            if (!savedDbPath.equals(newDbPath)) {
                if (ifDbFileExist(savedDbPath)) {
                    copyDbFile(savedDbPath, newDbPath);
                }
            } else {
                return newDbPath;
            }
        }

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(DATABASE_PATH, newDbPath);
        editor.apply();
        Timber.d("saved new path:%s", newDbPath);

        return newDbPath;
    }

    private boolean ifDbFileExist(String path) {
        File dbFile = new File(path);
        return dbFile.exists();
    }

    private void copyDbFile(String from, String to) {
        boolean result = false;

        File oldFile = null;
        File newFile = null;

        FileChannel src = null;
        FileChannel dst = null;

        try {
            oldFile = new File(from).getCanonicalFile();
            newFile = new File(to).getCanonicalFile();

            newFile.getParentFile().mkdirs();
            newFile.createNewFile();

            src = new FileInputStream(oldFile).getChannel();
            dst = new FileOutputStream(newFile).getChannel();

            long bytesTransferred = dst.transferFrom(src, 0, src.size());

            if (bytesTransferred == src.size()) {
                result = true;
            }

            Timber.i("copiyng DB success!");
        } catch (Exception exception) {
            Timber.e(exception, "error while copying DB file!");
        } finally {
            StreamUtils.close(src);
            StreamUtils.close(dst);
        }

        if (!result && newFile != null) {
            newFile.delete();
        }

        if (oldFile != null) {
            oldFile.delete();
        }
    }

    private SQLiteDatabase createDB(String path) {
        File dbFile = new File(path);

        final boolean createDb;
        if (dbFile.exists()) {
            Timber.i("Opening database at %s", dbFile);
            db = SQLiteDatabase.openOrCreateDatabase(dbFile.toString(), null);
            createDb = db.getVersion() < MIN_SUPPORTED_DATABASE_VERSION;
        } else {
            createDb = true;
        }

        if (createDb) {
            Timber.i("Creating new database at %s", dbFile);
            try {
                dbFile.delete();
                dbFile.getParentFile().mkdirs();
                dbFile.createNewFile();
                db = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
                createTables(db);
                db.setVersion(DATABASE_VERSION);
            } catch (IOException e) {
                Timber.e(e, "error while creating database");
            }
        }

        if (db != null) {
            Timber.i("database version : %s", db.getVersion());
            if (DATABASE_VERSION > db.getVersion()) {
                upgradeDb(db, db.getVersion(), DATABASE_VERSION);
            }
            Timber.i("External databse path=%s", db.getPath());
        }
        return db;
    }

    private void upgradeDb(final SQLiteDatabase db, final int version, final int databaseVersion) {
    }

    private void createTables(final SQLiteDatabase db) {
        createClubTable(db);
        createHorseTable(db);
        createEventTable(db);
        createPersonTable(db);
        createParticipiantTable(db);
        createTeamTable(db);
    }

    private void createTeamTable(final SQLiteDatabase db) {
        final String sb = "CREATE TABLE IF NOT EXISTS " + TABLE_TEAM + " (" +
                getCommonPart() +
                FIELD_TEAM_PARTICIPIANT + " INTEGER, " +
                FIELD_TEAM_PERSON + " INTEGER " +
                ");";

        db.execSQL(sb);
    }

    private void createPersonTable(final SQLiteDatabase db) {
        final String sb = "CREATE TABLE IF NOT EXISTS " + TABLE_PERSON + " (" +
                getCommonPart() +
                FIELD_PERSON_NAME + " TEXT " +
                ");";

        db.execSQL(sb);
    }

    private void createParticipiantTable(final SQLiteDatabase db) {
        final String sb = "CREATE TABLE IF NOT EXISTS " + TABLE_PARTICIPIANT + " (" +
                getCommonPart() +
                FIELD_PARTICIPIANT_EVENT + " INTEGER, " +
                FIELD_PARTICIPIANT_HORSE + " INTEGER " +
                ");";

        db.execSQL(sb);
    }

    private void createEventTable(final SQLiteDatabase db) {
        final String sb = "CREATE TABLE IF NOT EXISTS " + TABLE_EVENT + " (" +
                getCommonPart() +
                FIELD_EVENT_DATES + " TEXT, " +
                FIELD_EVENT_NAME + " TEXT " +
                ");";

        db.execSQL(sb);
    }

    /**
     * Helper method for create/update horse table
     */
    private void createClubTable(final SQLiteDatabase db) {
        final String sb = "CREATE TABLE IF NOT EXISTS " + TABLE_CLUB + " (" +
                getCommonPart() +
                FIELD_CLUB_NAME + " TEXT, " +
                ");";

        db.execSQL(sb);
    }

    /**
     * Helper method for create/update horse table
     */
    private void createHorseTable(final SQLiteDatabase db) {
        final String sb = "CREATE TABLE IF NOT EXISTS " + TABLE_HORSE + " (" +
                getCommonPart() +
                FIELD_HORSE_NAME + " TEXT, " +
                FIELD_HORSE_CLUB_ID + " INTEGER, " +
                ");";

        db.execSQL(sb);
    }

    @NonNull
    private String getCommonPart() {
        return FIELD_COMMON_ID + " INTEGER PRIMARY KEY, " +
                FIELD_COMMON_SERVER_ID + " INTEGER DEFAULT 0, " +
                FIELD_COMMON_SERVER_VERSION + " INTEGER DEFAULT 0, " +
                FIELD_COMMON_LOCAL_VERSION + " INTEGER DEFAULT 0, ";
    }

}
