package com.elasticbeanstalk.laciecool.first;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Environment;

import java.io.File;

/**
 * Created by lacie on 1/24/16.
 */
public class CollectedResults extends SQLiteOpenHelper {
    private static String DB_PATH = Environment.getExternalStorageDirectory().getPath();
    private static String DB_NAME = "collectedResults.db";
    private static CollectedResults sInstance;
    private SQLiteDatabase collectedResults;
    private final Context c;

    public static synchronized CollectedResults getInstance(Context context) {
        if(sInstance == null) {
            sInstance = new CollectedResults(context);
        }

        return sInstance;
    }

    private CollectedResults(Context con) {
        super(con, Environment.getExternalStorageDirectory() + File.separator + DB_NAME, null, 1);
        this.c = con;
    }

    public Cursor getAllResults() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        qb.setTables("RESULTS");

        Cursor c = qb.query(db, null, null, null, null, null, "pid ASC");
        c.moveToFirst();

        return c;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table RESULTS (pid VARCHAR(255) PRIMARY KEY, date INTEGER, startTime INTEGER, introAns VARCHAR(255), name VARCHAR(255), surveyAns VARCHAR(255), randNum INTEGER, phoneRandNum INTEGER, endTime INTEGER)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
