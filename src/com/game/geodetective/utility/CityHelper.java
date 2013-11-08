package com.game.geodetective.utility;

import com.game.geodetective.data.entity.City;
import com.game.geodetective.data.entity.ClueLocation;
import com.game.geodetective.graphics.GDImage;
import com.game.loblib.graphics.Image;
import com.game.loblib.utility.Logger;

public class CityHelper {
	protected static StringBuffer _tag = new StringBuffer("CityHelper");

	public static int getCityImage(City city) {
		int image = Image.UNKNOWN;

		if (city.CityName.equals("Kabul"))
			image = GDImage.CITY_IMAGE_KABUL;
		else if (city.CityName.equals("Buenos Aires"))
			image = GDImage.CITY_IMAGE_BUENOS_AIRES;
		else if (city.CityName.equals("Sydney"))
			image = GDImage.CITY_IMAGE_SYDNEY;
		else if (city.CityName.equals("Vienna"))
			image = GDImage.CITY_IMAGE_VIENNA;
		else if (city.CityName.equals("Nassau"))
			image = GDImage.CITY_IMAGE_NASSAU;
		else if (city.CityName.equals("Manama"))
			image = GDImage.CITY_IMAGE_MANAMA;
		else if (city.CityName.equals("Dhaka"))
			image = GDImage.CITY_IMAGE_DHAKA;
		else
			Logger.e(_tag, "Unknown city name");

		return image;
	}

	public static int GetClueLocationLabel(ClueLocation location) {
		int image = Image.UNKNOWN;

		if (location.Name.equals("Museum"))
			image = GDImage.CLUE_LOCATION_MUSEUM_LABEL;
		else if (location.Name.equals("Historical Society"))
			image = GDImage.CLUE_LOCATION_HISTORICAL_SOCIETY_LABEL;
		else if (location.Name.equals("Bar"))
			image = GDImage.CLUE_LOCATION_TAVERN_LABEL;
		else if (location.Name.equals("Stadium"))
			image = GDImage.CLUE_LOCATION_STADIUM_LABEL;
		else if (location.Name.equals("Library"))
			image = GDImage.CLUE_LOCATION_LIBRARY_LABEL;
		else if (location.Name.equals("Travel Center"))
			image = GDImage.CLUE_LOCATION_TRAVEL_CENTER_LABEL;
		else if (location.Name.equals("Courthouse"))
			image = GDImage.CLUE_LOCATION_COURTHOUSE_LABEL;
		else if (location.Name.equals("Bank"))
			image = GDImage.CLUE_LOCATION_BANK_LABEL;
		
		return image;
	}
	
	public static int GetClueLocationImage(ClueLocation location) {
		int image = Image.UNKNOWN;

		if (location.Name.equals("Museum"))
			image = GDImage.CLUE_LOCATION_MUSEUM;
		else if (location.Name.equals("Historical Society"))
			image = GDImage.CLUE_LOCATION_HISTORICAL_SOCIETY;
		else if (location.Name.equals("Bar"))
			image = GDImage.CLUE_LOCATION_TAVERN;
		else if (location.Name.equals("Stadium"))
			image = GDImage.CLUE_LOCATION_STADIUM;
		else if (location.Name.equals("Library"))
			image = GDImage.CLUE_LOCATION_LIBRARY;
		else if (location.Name.equals("Travel Center"))
			image = GDImage.CLUE_LOCATION_TRAVEL_CENTER;
		else if (location.Name.equals("Courthouse"))
			image = GDImage.CLUE_LOCATION_COURTHOUSE;
		else if (location.Name.equals("Bank"))
			image = GDImage.CLUE_LOCATION_BANK;
		
		return image;
	}
	
	public static String getLabel(City city) {
		StringBuffer label = new StringBuffer();
		label.append(city.CityName);
		label.append(", ");
		label.append(city.CountryName);
		return label.toString();
	}
}