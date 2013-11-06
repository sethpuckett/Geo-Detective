package com.game.geodetective.screen;

import com.game.loblib.screen.ScreenType;

public class GDScreenType extends ScreenType {
	public final static int SPLASH =			1 << 0;
	public final static int TITLE =				1 << 1;
	public final static int OPTIONS=			1 << 2;
	public final static int STATS =				1 << 3;
	public final static int CREDITS =			1 << 4;
	public final static int CASE_DESCRIPTION =	1 << 5;
	public final static int CITY =				1 << 6;
	public final static int CLUE_LOCATION =		1 << 8;
	public final static int CRIME_NET =			1 << 9;
	public final static int TRAVEL =			1 << 10;
	public final static int TRANSIT_LOAD =		1 << 11;
	public final static int WIN =				1 << 12;
	public final static int LOSS =				1 << 13;
}
