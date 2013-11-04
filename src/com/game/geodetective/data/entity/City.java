package com.game.geodetective.data.entity;

public class City {
	public int _id;
	public String CityName;
	public String CountryName;
	public int DifficultyId;
	
	@Override
	public int hashCode() {
		return _id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof City))
			return false;
		
		City city = (City)obj;
		return _id == city._id;
	}
}
