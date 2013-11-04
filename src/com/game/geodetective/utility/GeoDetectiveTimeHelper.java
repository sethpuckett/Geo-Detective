package com.game.geodetective.utility;

import java.util.Random;

public class GeoDetectiveTimeHelper {
	
	// number of hours in a week. 0 = MON 12 AM
	private static final int MAX_HOUR = 24 * 7;
	
	public static int getRandomHour() {
		Random rand = new Random();
		return rand.nextInt(MAX_HOUR);
	}
	
	public static int getDeadlineFromStart(int startHour) {
		if (startHour > 0)
			return startHour - 1;
		else
			return MAX_HOUR;
	}
	
	public static String getTimeString(int hour) {
		StringBuffer timeString = new StringBuffer();
		
		int dayInt = hour / 24;
		int hourInt = hour % 24;
		
		switch (dayInt) {
		case 0:
			timeString.append("MON ");
			break;
		case 1:
			timeString.append("TUE ");
			break;
		case 2:
			timeString.append("WED ");
			break;
		case 3:
			timeString.append("THU ");
			break;
		case 4:
			timeString.append("FRI ");
			break;
		case 5:
			timeString.append("SAT ");
			break;
		case 6:
			timeString.append("SUN ");
			break;
		}
		
		switch (hourInt) {
		case 0:
			timeString.append("12 AM");
			break;
		case 1:
			timeString.append("1 AM");
			break;
		case 2:
			timeString.append("2 AM");
			break;
		case 3:
			timeString.append("3 AM");
			break;
		case 4:
			timeString.append("4 AM");
			break;
		case 5:
			timeString.append("5 AM");
			break;
		case 6:
			timeString.append("6 AM");
			break;
		case 7:
			timeString.append("7 AM");
			break;
		case 8:
			timeString.append("8 AM");
			break;
		case 9:
			timeString.append("9 AM");
			break;
		case 10:
			timeString.append("10 AM");
			break;
		case 11:
			timeString.append("11 AM");
			break;
		case 12:
			timeString.append("12 PM");
			break;
		case 13:
			timeString.append("1 PM");
			break;
		case 14:
			timeString.append("2 PM");
			break;
		case 15:
			timeString.append("3 PM");
			break;
		case 16:
			timeString.append("4 PM");
			break;
		case 17:
			timeString.append("5 PM");
			break;
		case 18:
			timeString.append("6 PM");
			break;
		case 19:
			timeString.append("7 PM");
			break;
		case 20:
			timeString.append("8 PM");
			break;
		case 21:
			timeString.append("9 PM");
			break;
		case 22:
			timeString.append("10 PM");
			break;
		case 23:
			timeString.append("11 PM");
			break;
		}
		
		return timeString.toString();
	}
}
