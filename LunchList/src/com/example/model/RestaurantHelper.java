package com.example.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class RestaurantHelper extends SQLiteOpenHelper{
	private static final String DATABASE_NAME="lunchlist.db";
	private static final int SCHEMA_VERSION=1;
	private static final String onCreateCommand=
	"CREATE TABLE restaurants (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, address TEXT, type TEXT, note TEXT);";
	private static final String getAllCommand=
	"SELECT _id, name, address, type, note FROM restaurants ORDER BY name";
	public RestaurantHelper(Context context) {
		super(context, DATABASE_NAME, null, SCHEMA_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(onCreateCommand);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	public void insert (String name, String address, String type, String note){
		ContentValues cv= new ContentValues();
		cv.put("name", name);
		cv.put("address", address);
		cv.put("type", type);
		cv.put("note", note);
		getWritableDatabase().insert("restaurants", "name", cv);
	}
	public Cursor getAll(){
		return (getReadableDatabase().rawQuery(getAllCommand, null));
	}
	//get value in name column
	public String getName(Cursor c){
		return c.getString(1);
	}
	//get value in address column
	public String getAddress(Cursor c){
		return c.getString(2);
	}
	//get value in type column
	public String getType(Cursor c){
		return c.getString(3);
	}
	//get value in note column
	public String getNote(Cursor c){
		return c.getString(4);
	}
}
