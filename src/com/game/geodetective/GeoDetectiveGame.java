package com.game.geodetective;

import android.view.MotionEvent;

import com.game.geodetective.GeoDetectiveGameThread;
import com.game.geodetective.messaging.MessageType;
import com.game.geodetective.utility.Global;
import com.game.geodetective.utility.Manager;

public class GeoDetectiveGame {
	protected static StringBuffer _tag = new StringBuffer("GeoDetectiveGame");
	protected boolean _running;
	protected Thread _thread;
	protected GeoDetectiveGameThread _gameThread;

	public GeoDetectiveGame() {
		_gameThread = new GeoDetectiveGameThread();
	}
	
	public void init() {
		Manager.init();
		Global.View.init();
		
		Manager.Message.sendMessage(MessageType.GAME_INIT);
	}
	
	public void start() {
		if (!_running) {
			Runtime r = Runtime.getRuntime();
			r.gc();
			_thread = new Thread(_gameThread);
			_thread.setName("GeoDetectiveGame");
			_thread.start();
			_running = true;
		}
	}

	public void onPause() {
		if (_running) {
			_gameThread.pauseGame();
		}
	}
	
	public void onResume() {
		if (_running) {
			Global.Renderer.requestCallback();
		}
		else
			start();
	}
	
	public void onStop() {
		if(_running) {
			_gameThread.stopGame();
		}
		try {
			_thread.join();
		} catch (InterruptedException e) {
			_thread.interrupt();
		}
		_thread = null;
		_running = false;
	}
	
	public void onSurfaceReady() {
		if (_gameThread.isPaused() && _running)
			_gameThread.resumeGame();	
	}

	public boolean onTouchEvent(MotionEvent event) {
		if (_running)
			return _gameThread.onTouchEvent(event);
		else
			return true;
	}

    public boolean onBackDown()  {
    	if (_running)
    		return _gameThread.onBackDown();
    	else
    		return false;
    }
    
    public boolean onMenuDown() {
    	if (_running)
    		return _gameThread.onMenuDown();
    	else
    		return false;
    }
}
