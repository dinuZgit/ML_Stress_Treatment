package com.example.destresstreatment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "DeStress1";
    public static final String TABLE = "RecExperiencesData";
    public static final String COL_ID = "id";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_EXPERIENCES = "experiences";
    public static final String COL_UPDATE_DATE = "updatedDate";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /*String CREATE_TABLE = "CREATE TABLE " + TABLE + " ("
                + COL_USER_ID + " VARCHAR(255), "
                + COL_EXPERIENCES + " VARCHAR(255), "
                + COL_UPDATE_DATE + " VARCHAR(255));";*/
        String CREATE_TABLE = "CREATE TABLE " + TABLE + "("
                + COL_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_USER_ID + " VARCHAR(255),"
                + COL_EXPERIENCES + " VARCHAR(255),"
                + COL_UPDATE_DATE + " VARCHAR(255));";
        sqLiteDatabase.execSQL(CREATE_TABLE);
        System.out.println("Table created " + TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(sqLiteDatabase);
    }
}
