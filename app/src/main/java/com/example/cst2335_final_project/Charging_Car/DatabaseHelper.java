package com.example.cst2335_final_project.Charging_Car;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
//    ArrayList databaseInfo;
    private static final String DB_NAME = "ChargingStation.Db";
    private static final String DB_TABLE = "Charging_Station";
    //columns
    public static final int DATABASE_VERSION = 1;
    private static final String COL_TITTLE = "Tittle";
    private static final String COL_LAT = "Latitude";
    private static final String COL_LONG = "Longitude";
    private static final String COL_CONTACT = "Contact";
    private static final String CHARGE_ID = "ID";


    //queries
    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + "(" + CHARGE_ID + " INTEGER PRIMARY KEY ," + COL_TITTLE + " TEXT," + COL_LAT + " TEXT," + COL_LONG + " TEXT," + COL_CONTACT + " ,TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);

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

    public void deleteAll(){
        SQLiteDatabase db =this.getWritableDatabase();
        db.execSQL("delete from "+ DB_TABLE);

    }
    public boolean insertData(String tittle, String Latitude, String Longitude, String Contact) {

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
    public ArrayList<Charging> viewData() {
        ArrayList<Charging> chargeArray = new ArrayList<Charging>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from " + DB_TABLE ;
        Cursor cursor = db.rawQuery(query, null);

        String values=" ";

       while (cursor.moveToNext()){
//             values = "Tittle :" + cursor.getString(1)+ "\n "+  "Latitude :" + cursor.getString(2) + "\n " +"Longitude :" + cursor.getString(3);

//           int id = cursor.getInt(0);
           String tittle = cursor.getString(1);
           double lat = cursor.getDouble(2);
           double longg = cursor.getDouble(3);
           String phone = cursor.getString(4);

           chargeArray.add(new Charging(tittle,lat,longg,phone));

        }
        return chargeArray;
        }



    }

