package com.game.geodetective.data;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.game.loblib.utility.Global;

public class GeoDetectiveDataHelper extends SQLiteOpenHelper {
	
    private static final String DB_NAME = "geodetective";
    private SQLiteDatabase _db; 

	public GeoDetectiveDataHelper(int version) {
    	super(Global.Context, DB_NAME, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		boolean dbExist = checkDataBase();
		 
    	if(dbExist){
    		//do nothing - database already exist
    	}else{
 
    		//By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase();
 
        	try {
 
    			copyDataBase();
 
    		} catch (IOException e) {
 
        		throw new Error("Error copying database");
 
        	}
    	}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
    @Override
	public synchronized void close() {
    	    if(_db != null)
    	    	_db.close();
    	    super.close();
	}
	
    public void openDataBase() throws SQLException{
    	//Open the database
        String myPath = getDatabasePath() + DB_NAME;
        _db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }
    
	/**
     * Check if the database already exists to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {
    	SQLiteDatabase checkDB = null;
    	try {
    		String myPath = getDatabasePath() + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    	} catch(SQLiteException e) { /*database does't exist yet.*/ }
 
    	if(checkDB != null)
    		checkDB.close();
 
    	return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transferring bytestream.
     * */
    private void copyDataBase() throws IOException{
    	//Open your local db as the input stream
    	InputStream myInput = Global.Context.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = getDatabasePath() + DB_NAME;
 
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

    private String getDatabasePath() {
    	return Global.Context.getFilesDir().getPath() + "/databases/";
    }
}