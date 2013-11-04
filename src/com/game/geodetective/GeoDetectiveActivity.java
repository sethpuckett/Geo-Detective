package com.game.geodetective;

import android.os.Bundle;

import com.game.geodetective.data.GeoDetectiveDataAccess;
import com.game.geodetective.utility.GeoDetectiveComponentFactory;
import com.game.geodetective.utility.GeoDetectiveGlobal;
import com.game.loblib.LobLibActivity;

public class GeoDetectiveActivity extends LobLibActivity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
		_componentFactory = new GeoDetectiveComponentFactory();
        super.onCreate(savedInstanceState);
        
        GeoDetectiveGlobal.DataAccess = new GeoDetectiveDataAccess();
    } 
	
    @Override
    protected void onPause() {
    	GeoDetectiveGlobal.DataAccess.close();
    	super.onPause();
    }
    
    @Override
    protected void onDestroy() {
    	GeoDetectiveGlobal.DataAccess.close();
    	super.onDestroy();
    } 
}
