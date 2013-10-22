package com.game.geodetective.utility;

import android.content.Context;

import com.game.geodetective.GeoDetectiveGame;
import com.game.geodetective.GeoDetectiveView;
import com.game.geodetective.graphics.Camera;
import com.game.geodetective.graphics.GeoDetectiveRenderer;
import com.game.geodetective.utility.GameSettings;
import com.game.geodetective.utility.CommonData;

// making these globally accessible so I don't have to pass them around everywhere
public class Global {
	public static Context Context;
	public static GeoDetectiveView View;
	public static GeoDetectiveRenderer Renderer;
	public static GeoDetectiveGame Game;
	public static Camera Camera;
	public static GameSettings Settings;
	public static CommonData Data;
}
