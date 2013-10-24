package com.game.geodetective;

import com.game.geodetective.graphics.GeoDetectiveSpriteHelper;
import com.game.geodetective.sound.GeoDetectiveMusicHelper;
import com.game.geodetective.utility.GeoDetectiveCommonData;
import com.game.geodetective.utility.GeoDetectiveGameSettings;
import com.game.loblib.LobLibActivity;

import android.os.Bundle;

public class GeoDetectiveActivity extends LobLibActivity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        _spriteHelper = new GeoDetectiveSpriteHelper();
        _musicHelper = new GeoDetectiveMusicHelper();
        _commonData = new GeoDetectiveCommonData();
        _gameSettings = new GeoDetectiveGameSettings();
        
        //TODO: testing
  		//Editor editor = getSharedPreferences("geodetective", Context.MODE_PRIVATE).edit();
  		//editor.putInt("maxLevel", 21);
  		//editor.commit();
        
        super.onCreate(savedInstanceState);
    } 
}
