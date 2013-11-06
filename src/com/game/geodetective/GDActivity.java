package com.game.geodetective;

import android.os.Bundle;

import com.game.geodetective.data.GDDataAccess;
import com.game.geodetective.utility.GDComponentFactory;
import com.game.geodetective.utility.GDGlobal;
import com.game.loblib.LobLibActivity;

public class GDActivity extends LobLibActivity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
		_componentFactory = new GDComponentFactory();
        super.onCreate(savedInstanceState);
        
        GDGlobal.DataAccess = new GDDataAccess();
    } 
	
    @Override
    protected void onPause() {
    	GDGlobal.DataAccess.close();
    	super.onPause();
    }
    
    @Override
    protected void onDestroy() {
    	GDGlobal.DataAccess.close();
    	super.onDestroy();
    } 
}
