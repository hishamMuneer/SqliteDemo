package com.hisham.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hisham on 9/1/2015.
 */
public class MyDBHelper extends SQLiteOpenHelper {

    private static final String DBNAME = "mydb.db";
    private static final int VERSION = 1;

    public static final String TABLE_NAME = "employees";
    public static final String ID = "_id";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String ADDRESS = "address";
    public static final String SALARY = "salary";


    private SQLiteDatabase myDB;

    public MyDBHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String queryTable = "CREATE TABLE " + TABLE_NAME +
                " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FIRST_NAME + " TEXT NOT NULL, " +
                LAST_NAME + " TEXT NOT NULL, " +
                ADDRESS + " TEXT NOT NULL, " +
                SALARY + " REAL NOT NULL " +
                ")";

        db.execSQL(queryTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDB(){
        myDB = getWritableDatabase();
    }

    public void closeDB(){
        if(myDB != null && myDB.isOpen()) {
            myDB.close();
        }
    }

    public long insert(int id, String fName, String lName, String address, Double salary){
        ContentValues values = new ContentValues();
        if(id != -1)
            values.put(ID, id);
        values.put(FIRST_NAME, fName);
        values.put(LAST_NAME, lName);
        values.put(ADDRESS, address);
        values.put(SALARY, salary);

        return myDB.insert(TABLE_NAME, null, values);
    }

    public long update(int id, String fName, String lName, String address, Double salary){
        ContentValues values = new ContentValues();
        values.put(FIRST_NAME, fName);
        values.put(LAST_NAME, lName);
        values.put(ADDRESS, address);
        values.put(SALARY, salary);

        String where = ID + " = " + id;

        return myDB.update(TABLE_NAME, values, where, null);
    }

    public long delete(int id){
        String where = ID + " = " + id;

        return myDB.delete(TABLE_NAME, where, null);
    }

    public Cursor getAllRecords(){

       // myDB.query(TABLE_NAME, null, null, null, null, null, null);

        String query = "SELECT * FROM " + TABLE_NAME;
        return myDB.rawQuery(query, null);

    }

    public Cursor getDataBasedOnQuery(String query){
        return myDB.rawQuery(query, null);
    }


}
