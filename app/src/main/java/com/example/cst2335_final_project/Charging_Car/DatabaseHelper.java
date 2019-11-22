package com.example.cst2335_final_project.Charging_Car;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "ChargingDB";
    private static final String DB_TABLE = "Favourites";
ArrayList databaseInfo;
    //columns
    private static final String COL_TITTLE = "Station_Tittle";
    private static final String COL_LAT= "Latitude";
    private static final String COL_LONG= "Longitude";
    private static final String COL_CONTACT= "Contact";
    private static final String CHARGE_ID = "MessageID";

    //queries
    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + " (" + CHARGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ""+ COL_TITTLE + " TEXT, " + COL_LAT + " TEXT,  " + COL_LONG + " TEXT, " + COL_CONTACT +", TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        onCreate(db);
    }

    public void onDowngrada() {

    }


    public boolean insertData(String tittle, String Latitude, String Longitude, String Contact ) {

        databaseInfo = new ArrayList();


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Tittle", tittle);
        contentValues.put("Latitude", Latitude);
        contentValues.put("Longitude", Longitude);
        contentValues.put("Contact", Contact);

         long result = db.insert(DB_TABLE, null, contentValues);

        return result != -1; //if result = -1 data doesn't insert
    }

    //view data
    public Cursor viewData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from "+DB_TABLE;
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToNext();

        Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(cursor));
        return cursor;


    }
}