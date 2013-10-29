package com.game.geodetective;

import android.os.Bundle;

import com.game.geodetective.utility.GeoDetectiveComponentFactory;
import com.game.loblib.LobLibActivity;

public class GeoDetectiveActivity extends LobLibActivity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
		_componentFactory = new GeoDetectiveComponentFactory();
        super.onCreate(savedInstanceState);
    } 
}
