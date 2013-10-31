package com.game.geodetective.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.game.geodetective.data.entity.City;
import com.game.geodetective.data.entity.Crime;
import com.game.geodetective.data.entity.Villain;

public class GeoDetectiveDataAccess {

	private SQLiteDatabase _db;
	private GeoDetectiveDataHelper _dbHelper;

	public GeoDetectiveDataAccess(Context context) {
		_dbHelper = new GeoDetectiveDataHelper(1);
	}

	public void open() throws SQLException {
		_db = _dbHelper.getWritableDatabase();
	}

	public void close() {
		_dbHelper.close();
	}

	// Returns a random villain
	public Villain getRandomVillain() {  
		  Cursor cursor = _db.query("Villain", new String[] {"*"}, null, null, null, null, "RANDOM()", "1");
		  cursor.moveToFirst();
		  Villain villain = cursorToVillain(cursor);
		  cursor.close();
		  return villain;		  
	  }
	
	// Returns a random crime from a city with a difficulty less than or equal to the provided difficulty
	public Crime getRandomCrime(int DifficultyId) {  
		  Cursor cursor = _db.rawQuery("SELECT * FROM Crime INNER JOIN City ON Crime.CityId = City._id WHERE City.DifficultyId <= ? ORDER BY RANDOM() LIMIT 1",
					new String[] {Integer.toString(DifficultyId)});
		  cursor.moveToFirst();
		  Crime crime = cursorToCrime(cursor);
		  cursor.close();
		  return crime;		  
	  }

	// Returns first city associated with provided crime
	public City getCityByCrime(int crimeId) {
		  Cursor cursor = _db.rawQuery("SELECT * FROM City INNER JOIN Crime ON Crime.CityId = City._id WHERE Crime._id = ? LIMIT 1",
					new String[] {Integer.toString(crimeId)});
		  cursor.moveToFirst();
		  City city = cursorToCity(cursor);
		  cursor.close();
		  return city;	
	}

	private Villain cursorToVillain(Cursor cursor) {
		Villain villain = new Villain();
		villain._id = cursor.getInt(0);
		villain.Name = cursor.getString(1);
		villain.EyeTypeId = cursor.getInt(2);
		villain.FeatureTypeId = cursor.getInt(3);
		villain.FoodTypeId = cursor.getInt(4);
		villain.GenderTypeId = cursor.getInt(5);
		villain.HairTypeId = cursor.getInt(6);
		villain.HobbyTypeId = cursor.getInt(7);
		villain.VehicleTypeId = cursor.getInt(8);
		return villain;
	}
	
	private Crime cursorToCrime(Cursor cursor) {
		Crime crime = new Crime();
		crime._id = cursor.getInt(0);
		crime.CityId = cursor.getInt(1);
		crime.CrimeText = cursor.getString(2);
		return crime;
	}
	
	private City cursorToCity(Cursor cursor) {
		City city = new City();
		city._id = cursor.getInt(0);
		city.CityName = cursor.getString(1);
		city.CountryName = cursor.getString(2);
		city.DifficultyId = cursor.getInt(3);
		return city;
	}
}
