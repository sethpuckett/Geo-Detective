package com.game.geodetective.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.game.loblib.utility.Global;

public class GeoDetectiveDataHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "geodetective.s3db";
    private static final int DB_VERSION = 1;
    
    private SQLiteDatabase _db; 
 
    public GeoDetectiveDataHelper() {
    	super(Global.Context, DB_NAME, null, DB_VERSION);
    }	
    
    public SQLiteDatabase open() throws SQLException{
    	// if _db hasn't been set yet create the DB if hasn't been created yet
    	if (_db == null) {
	    	boolean dbExists = checkDataBase();
	    	if (!dbExists) {
	        	this.getReadableDatabase();
	        	try {
	    			copyDataBase();
	    		} catch (IOException e) {
	        		//throw new Error("Error copying database");
	        	}
	    	}
    	}
    	
    	//Open the database
        String myPath = Global.Context.getDatabasePath(DB_NAME) + DB_NAME;
    	_db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    	return _db;
    }
   
    // Deletes app database
    public void delete() {
    	if (_db != null) {
    		_db.close();
    		_db = null;
    	}
    	
    	Global.Context.deleteDatabase(DB_NAME);
    }
    
    @Override
	public synchronized void close() {
	    if(_db != null) {
		    _db.close();
		    _db = null;
	    }
	    
	    super.close();
	}
    
    // Check if the database already exist to avoid re-copying the file each time you open the application.
    private boolean checkDataBase(){
    	File dbFile = Global.Context.getDatabasePath(DB_NAME);
    	return dbFile.exists();
    }
 
    // Copies clean database from assets directory app database directory
    private void copyDataBase() throws IOException{
    	//Open your local db as the input stream
    	InputStream myInput = Global.Context.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = Global.Context.getDatabasePath(DB_NAME) + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
	
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}
}