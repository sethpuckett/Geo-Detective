package com.game.geodetective;

import com.game.geodetective.GeoDetectiveGame;
import com.game.geodetective.GeoDetectiveView;
import com.game.geodetective.graphics.Camera;
import com.game.geodetective.graphics.GeoDetectiveRenderer;
import com.game.geodetective.utility.CommonData;
import com.game.geodetective.utility.GameSettings;
import com.game.geodetective.utility.Global;
import com.game.geodetective.utility.Logger;
import com.game.geodetective.utility.android.AllocationGuard;

import android.media.AudioManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.WindowManager;

public class GeoDetectiveActivity extends Activity {

	protected static StringBuffer _tag = new StringBuffer("GeoDetectiveActivity");
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getWindow().setFlags(
        		WindowManager.LayoutParams.FLAG_FULLSCREEN,
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(
        		  WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
        		  WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        
        Logger.init();
        
        AllocationGuard.sGuardActive = false;
        
        //TODO: testing
		//Editor editor = getSharedPreferences("geodetective", Context.MODE_PRIVATE).edit();
		//editor.putInt("maxLevel", 21);
		//editor.commit();
        
        GeoDetectiveView view = new GeoDetectiveView(this);
        setContentView(view);
        GeoDetectiveGame game = new GeoDetectiveGame();
        GeoDetectiveRenderer renderer = new GeoDetectiveRenderer();
        view.setRenderer(renderer);
        
        Global.Context = this;
        Global.View = view;
        Global.Game = game;
        Global.Renderer = renderer;
        Global.Camera = new Camera();
        Global.Settings = new GameSettings();
        Global.Data = new CommonData();
        
        game.init();
        Global.Camera.init();
        Runtime.getRuntime().gc();
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
    	boolean returnSuper = true;
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (Global.Game.onBackDown())
            	returnSuper = false;
        }
        else if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0) {
            if (Global.Game.onMenuDown())
            	returnSuper = false;
        }
        
        if (returnSuper)
        	return super.onKeyDown(keyCode, event);
        else
        	return true;
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	Global.Game.onPause();
    	Global.Renderer.onPause();
    	Global.View.onPause();
		Runtime r = Runtime.getRuntime();
		r.gc();
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	Global.View.onResume();
    	Global.Renderer.onResume();
    	Global.Game.onResume();	
    }
    
    @Override
    protected void onDestroy() {
    	Global.Game.onStop();
    	super.onDestroy();
    }
    
}
