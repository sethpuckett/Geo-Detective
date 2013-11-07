package com.game.geodetective.data;

import java.util.Arrays;
import java.util.Random;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.game.geodetective.data.entity.CaseState;
import com.game.geodetective.data.entity.City;
import com.game.geodetective.data.entity.ClueLocation;
import com.game.geodetective.data.entity.Crime;
import com.game.geodetective.data.entity.DifficultyType;
import com.game.geodetective.data.entity.Villain;
import com.game.geodetective.utility.Debug;
import com.game.geodetective.utility.GDGlobal;
import com.game.geodetective.utility.GDTimeHelper;

public class GDDataAccess {

	private SQLiteDatabase _db;
	private GDDataHelper _dbHelper;

	public GDDataAccess() {
		_dbHelper = new GDDataHelper();
		
		if (Debug.AlwaysResetDatabase)
			_dbHelper.delete();
	}

	public void close() {
		_dbHelper.close();
	}

	// Returns a random villain
	public Villain getRandomVillain() {  
		  Cursor cursor = getDB().query("Villain", new String[] {"*"}, null, null, null, null, "RANDOM()", "1");
		  cursor.moveToFirst();
		  Villain villain = cursorToVillain(cursor);
		  cursor.close();
		  return villain;		  
	  }
	
	// Returns a random crime from a city with a difficulty less than or equal to the provided difficulty
	public Crime getRandomCrime(DifficultyType difficulty) {  
		  Cursor cursor = getDB().rawQuery("SELECT * FROM Crime INNER JOIN City ON Crime.CityId = City._id WHERE City.DifficultyId <= ? ORDER BY RANDOM() LIMIT 1",
					new String[] {Integer.toString(difficulty._id)});
		  cursor.moveToFirst();
		  Crime crime = cursorToCrime(cursor);
		  cursor.close();
		  return crime;		  
	  }

	// Returns first city associated with provided crime
	public City getCityByCrime(Crime crime) {
		  Cursor cursor = getDB().rawQuery("SELECT * FROM City INNER JOIN Crime ON Crime.CityId = City._id WHERE Crime._id = ? LIMIT 1",
					new String[] {Integer.toString(crime._id)});
		  cursor.moveToFirst();
		  City city = cursorToCity(cursor);
		  cursor.close();
		  return city;	
	}

	public DifficultyType getCurrentDifficulty() {
		  Cursor cursor = getDB().rawQuery("SELECT * FROM DifficultyType INNER JOIN GamePreferences ON DifficultyType._id = GamePreferences.DifficultyId LIMIT 1", null);
		  cursor.moveToFirst();
		  DifficultyType difficulty = cursorToDifficultyType(cursor);
		  cursor.close();
		  return difficulty;	
	}
	
	// Wipes out any existing state information (i.e. stops current case) by truncating state tables
	public void resetState() {
		getDB().rawQuery("DELETE FROM CaseState", null);
		getDB().rawQuery("DELETE FROM CaseStateCityAvailable", null);
		getDB().rawQuery("DELETE FROM CaseStateCityVisited", null);
		getDB().rawQuery("DELETE FROM CaseStateClueLocation", null);
	}

	// Returns true if there is an active, ongoing case
	public boolean openCaseExists() {
		int count = 0;
		Cursor cursor = getDB().rawQuery("SELECT COUNT(*) FROM CaseState", null);
		if (cursor.moveToFirst())
			count = cursor.getInt(0);
		return count > 0;
	}
	
	// Initialize a new case
	public void createNewCase() {
		// Clear any existing state information
		resetState();
		
		// get difficulty
		DifficultyType difficulty =  GDGlobal.DataAccess.getCurrentDifficulty();
		
		// get villain
		Villain villain = GDGlobal.DataAccess.getRandomVillain();
		
		// get crime and starting city
		Crime crime = GDGlobal.DataAccess.getRandomCrime(difficulty);
		City startCity = GDGlobal.DataAccess.getCityByCrime(crime);
		
		// get start time/deadline
		int startHour = GDTimeHelper.getRandomHour();
		int deadlineHour = GDTimeHelper.getDeadlineFromStart(startHour);
		
		// get goal city count (number of cities to travel to be before villain is caught)
		// TODO: make this configurable in DB and based on difficulty
		int goalCityCount = 10;
		
		String cityString = Integer.toString(startCity._id);
		String currentHourString = Integer.toString(startHour);
		String deadlineHourString = Integer.toString(deadlineHour);
		String difficultyString = Integer.toString(difficulty._id);
		String villainString = Integer.toString(villain._id);
		String goalCityVisitCountString = Integer.toString(goalCityCount);
		
		getDB().execSQL("INSERT INTO CaseState (CurrentCityId, CurrentHour, DeadlineHour, GoalCityVisitCount, DifficultyTypeId, CrimeCityId, VillainId) VALUES (?, ?, ?, ?, ?, ?, ?)", 
			new String[] {cityString, currentHourString, deadlineHourString, goalCityVisitCountString, difficultyString, cityString, villainString});
		Cursor stateCursor = getDB().rawQuery("SELECT * FROM CaseState LIMIT 1", null);
		stateCursor.moveToFirst();
		CaseState state = cursorToCaseState(stateCursor);
		
		//set start city as visited
		setVisitedCity(state, startCity);
		
		// get visitable cities
		// TODO: make this configurable in DB
		int visitableCityCount = 4;
		City[] visitableCities = GDGlobal.DataAccess.getUnvisitedCities(visitableCityCount, difficulty);
		
		for (int i = 0; i <  visitableCities.length; i ++) {
			getDB().rawQuery("INSERT INTO CaseStateCityAvailable (CaseStateId, CityId) VALUES (?, ?)",
				new String[] {Integer.toString(state._id), Integer.toString( visitableCities[i]._id) });
		}
		
		// set goal city
		setRandomGoalCity(state, visitableCities);
		
		// get clue locations for this city
		ClueLocation[] clueLocations = GDGlobal.DataAccess.getRandomClueLocations(3);
		
		for (int i = 0; i < clueLocations.length; i ++) {
			getDB().rawQuery("INSERT INTO CaseStateClueLocation (CaseStateId, ClueLocationId) VALUES (?, ?)",
				new String[] {Integer.toString(state._id), Integer.toString(clueLocations[i]._id) });
		}
	}
	
	// Returns a random unvisited city with a difficulty less than or equal to the provided difficulty
	public City[] getUnvisitedCities(int cityCount, DifficultyType difficulty) {
		City[] cities = new City[cityCount];
		
		int count = 0;
		while (count < cityCount) {
		  Cursor cursor = getDB().rawQuery("SELECT * FROM City WHERE City._id NOT IN (SELECT CityId FROM CaseStateCityAvailable INNER JOIN CaseState ON CaseStateCityAvailable.CaseStateId = CaseState._id) AND City.DifficultyId <= ? ORDER BY RANDOM() LIMIT 1",
					new String[] {Integer.toString(difficulty._id)});
		  cursor.moveToFirst();
		  City city = cursorToCity(cursor);
		  cursor.close();
		  
		  // add this city to array, if it hasn't been added yet
		  if (!Arrays.asList(cities).contains(city)) {
			  cities[count] = city;
			  count++;
		  }
		}
		
		return cities;
	}
	
	public void setVisitedCity(CaseState state, City city) {
		getDB().rawQuery("INSERT INTO CaseStateCityVisited (CaseStateId, CityId) VALUES (?, ?)", new String[] { Integer.toString(state._id), Integer.toString(city._id) });
	}
	
	public void setRandomGoalCity(CaseState state, City[] visitableCities) {
		// get goal city
		Random rand = new Random();
		City goalCity = visitableCities[rand.nextInt(visitableCities.length)];
		
		getDB().rawQuery("UPDATE CaseState SET GoalCityId = ? WHERE _id = ?", new String[] {Integer.toString(state._id), Integer.toString(goalCity._id)});
	}
	
	public void clearAvailableTravelCities() {
		getDB().rawQuery("DELETE FROM CaseStateCityAvailable", null);
	}
	
	public void setAvailableTravelCities(City[] cities) {
		clearAvailableTravelCities();
		CaseState state = getCurrentCaseState();
		for (int i = 0; i < cities.length; i++) {
			getDB().rawQuery("INSERT INTO CaseStateCityAvailable (CaseStateId, CityId) VALUES (?, ?)", new String[] { Integer.toString(state._id), Integer.toString(cities[i]._id) });
		}
	}
	
	public ClueLocation[] getRandomClueLocations(int locationCount) {
		ClueLocation[] locations = new ClueLocation[locationCount];
		
		int count = 0;
		while (count < locationCount) {
			// TODO: need to be specify if trait clue locations are included
			Cursor cursor = getDB().rawQuery("SELECT * FROM ClueLocation ORDER BY RANDOM() LIMIT 1", null);
			cursor.moveToFirst();
			ClueLocation location = cursorToClueLocation(cursor);
			cursor.close();
  
			// add this location to array, if it hasn't been added yet
			if (!Arrays.asList(locations).contains(location)) {
				locations[count] = location;
				count++;
			}
		}
		
		return locations;
	}
	
	public CaseState getCurrentCaseState() {
		Cursor cursor = getDB().rawQuery("SELECT * FROM CaseState LIMIT 1", null);
		cursor.moveToFirst();
		CaseState state = cursorToCaseState(cursor);
		cursor.close();
		return state;	
	}
	
	public City getCity(int cityId) {
		Cursor cursor = getDB().rawQuery("SELECT * FROM City WHERE _id = ? LIMIT 1", new String[] { Integer.toString(cityId) });
		cursor.moveToFirst();
		City city = cursorToCity(cursor);
		cursor.close();
		return city;	
	}
	
	public Crime getCrimeForCurrentCase() {
		CaseState state = getCurrentCaseState();
		Cursor cursor = getDB().rawQuery("SELECT * FROM Crime WHERE Crime.CityId = ?", new String[] { Integer.toString(state.CrimeCityId)});
		cursor.moveToFirst();
		Crime crime = cursorToCrime(cursor);
		cursor.close();
		return crime;	
	}
	
	private SQLiteDatabase getDB() {
		if (_db == null)
			_db = _dbHelper.open();
		return _db;
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
	
	private CaseState cursorToCaseState(Cursor cursor) {
		CaseState state = new CaseState();
		state._id = cursor.getInt(0);
		state.CurrentCityId = cursor.getInt(1);
		state.CurrentHour = cursor.getInt(2);
		state.DeadlineHour = cursor.getInt(3);
		state.PreviousCityId = cursor.getInt(4);
		state.GoalCityId = cursor.getInt(5);
		state.CurrentCityVisitCount = cursor.getInt(6);
		state.GoalCityVisitCount = cursor.getInt(7);
		state.DifficultyTypeId = cursor.getInt(8);
		state.SleptToday = cursor.getInt(9) > 0;
		state.CrimeCityId = cursor.getInt(10);
		state.VillainId = cursor.getInt(11);
		state.CurrentClueLocationId = cursor.getInt(12);
		state.WarrantEyeId = cursor.getInt(13);
		state.WarrantFeatureId = cursor.getInt(14);
		state.WarrantFoodId = cursor.getInt(15);
		state.WarrantGenderId = cursor.getInt(16);
		state.WarrantHairId = cursor.getInt(17);
		state.WarrantHobbyId = cursor.getInt(18);
		state.WarrantVehicleId = cursor.getInt(19);
		state.HasWarrant = cursor.getInt(20) > 0;
		return state;
	}
	
	private ClueLocation cursorToClueLocation(Cursor cursor) {
		ClueLocation loc = new ClueLocation();
		loc._id = cursor.getInt(0);
		loc.Name = cursor.getString(1);
		loc.ClueType1Id = cursor.getInt(2);
		loc.ClueType2Id = cursor.getInt(3);
		return loc;
	}
	
	private DifficultyType cursorToDifficultyType(Cursor cursor) {
		DifficultyType difficulty = new DifficultyType();
		difficulty._id = cursor.getInt(0);
		difficulty.Code = cursor.getString(1);
		difficulty.Name = cursor.getString(2);
		return difficulty;
	}
}
