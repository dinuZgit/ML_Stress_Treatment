package com.example.destresstreatment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserExpDao {

    String user_id, experiences, updatedDate;
    private DBHelper dbHelper;

    public UserExpDao(Context context) {
        dbHelper = new DBHelper(context);
    }

    public long insertData(String user_id, String experiences) {
        this.user_id = user_id;
        this.experiences = experiences;

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COL_USER_ID, user_id);
        contentValues.put(DBHelper.COL_EXPERIENCES, experiences);
        contentValues.put(DBHelper.COL_UPDATE_DATE, getDateTime());

        long retID = 4;
        try {
            retID = database.insert(DBHelper.TABLE, null, contentValues);
            System.out.println("Inside insertData function and the retID is " + retID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retID;
    }

    public String getExperienceData(String user_id) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //StringBuffer buffer = new StringBuffer();
        /*String[] columns = {DBHelper.COL_USER_ID, DBHelper.COL_EXPERIENCES, DBHelper.COL_UPDATE_DATE};
        @SuppressLint("Recycle") Cursor cursor = database.query(DBHelper.TABLE, columns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String user_id = cursor.getString(cursor.getColumnIndex(DBHelper.COL_USER_ID));
            String experiences = cursor.getString(cursor.getColumnIndex(DBHelper.COL_EXPERIENCES));
            String updatedDate = cursor.getString(cursor.getColumnIndex(DBHelper.COL_UPDATE_DATE));
            buffer.append(user_id + " " + experiences + " " + updatedDate + "\n");
        }*/
        String query = "SELECT * FROM " + DBHelper.TABLE + " WHERE " + DBHelper.COL_USER_ID + " = '" + user_id + "'";
        Cursor cursor = database.rawQuery(query, null);
        String experiences = "";
        if (cursor.moveToLast()){
            experiences = cursor.getString(cursor.getColumnIndex("experiences"));
            System.out.println("User experiences inside if is " + experiences);
        }
        System.out.println("User experience is "+ experiences);
        cursor.close();
        database.close();

        return experiences;
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
