package com.elasticbeanstalk.laciecool.first;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by lacie on 1/13/16.
 */
public class SurveySQL extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "surveyDb.db";
    private static final int DATABASE_VERSION = 2;

    public interface TABLES {
        String GROUP = "GROUP_QNS";
        String OPTIONS = "OPTIONS_TABLE";
        String QNS = "SURVEY_QNS_TABLE";
    }

    public SurveySQL(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public Cursor getGroupNames() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        qb.setTables(TABLES.GROUP);

        Cursor c = qb.query(db, null, null, null, null, null, null);
        c.moveToFirst();

        return c;
    }

    public Cursor getSurveyQnsInOrder(int grp) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        qb.setTables(TABLES.QNS);

        Cursor c = qb.query(db, null, "grp="+grp, null, null, null, "ord ASC");
        c.moveToFirst();
        return c;
    }

    public Cursor getSurveyQnsFromQnsId(int qId) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        qb.setTables(TABLES.QNS);

        Cursor c = qb.query(db, null, "id=" + qId, null, null, null, "ord ASC");
        c.moveToFirst();

        return c;
    }

    public Cursor getOptionsFromQnsId(int qnsId) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        qb.setTables(TABLES.OPTIONS);

        Cursor c = qb.query(db, null, "qnsId=" + qnsId, null, null, null, "id ASC");

        return c;
    }

    public static void forceDatabaseReload(Context context){
        SurveySQL dbHelper = new SurveySQL(context);
        dbHelper.setForcedUpgrade(DATABASE_VERSION);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.setVersion(-1);
        db.close();
        db = dbHelper.getWritableDatabase();
    }

//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//    }

//    public Cursor getSurveyQns() {
//        SQLiteDatabase db = getReadableDatabase();
//        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
//
//        //qb.setTables("");
//
//    }
}
