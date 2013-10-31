package com.game.geodetective.screen;

import com.game.loblib.messaging.IMessageHandler;
import com.game.loblib.messaging.Message;
import com.game.loblib.messaging.MessageType;
import com.game.loblib.screen.ScreenManager;
import com.game.loblib.utility.Global;
import com.game.loblib.utility.Logger;
import com.game.loblib.utility.Manager;

public class GeoDetectiveScreenManager extends ScreenManager implements IMessageHandler {

	protected SplashScreen _splash = new SplashScreen();
	protected TitleScreen _title = new TitleScreen();
	protected CreditsScreen _credits = new CreditsScreen();
	
	@Override
	public void init() {
		Manager.Message.subscribe(this, MessageType.GAME_INIT);
	}
	
	@Override
	public void handleMessage(Message message) {
		// when game is initialized set screen to splash screen
		if (message.Type == MessageType.GAME_INIT) {
			if (Global.Renderer.Width > 0) {
				if (_active != null)
					_active.close();
				_active = _splash;
				_active.init();
			}
			else
				Manager.Message.subscribe(this, MessageType.SCREEN_SIZE_SET);
		}
		else if (message.Type == MessageType.SCREEN_SIZE_SET) {
			Manager.Message.unsubscribe(this, MessageType.SCREEN_SIZE_SET);
			if (_active != null)
				_active.close();
			_active = _splash;
			_active.init();
		}
	}
	
	@Override
	protected void onHandleCode(int code) {
		switch (_active.getType()) {
		case GeoDetectiveScreenType.SPLASH:
			_active.close();
			if (Manager.Sprite.allocatedSpriteCount() > 0)
				Logger.e(_tag, "Splash Screen did not free all sprites upon closing");
			if (code == GeoDetectiveScreenCode.TRANSITION_TITLE)
				_active = _title;
			else {
				Logger.e(_tag, "Invalid transition");
				return;
			}
			_active.init();
			break;
		case GeoDetectiveScreenType.TITLE:
			if (code == GeoDetectiveScreenCode.TRANSITION_CREDITS) {
				_active.pause();
				_active = _credits;
			}
			else if (code == GeoDetectiveScreenCode.TRANSITION_OPTIONS) {
				_active.pause();
				_active = _credits;
			}
			else {
				Logger.e(_tag, "Invalid transition");
				return;
			}
			_active.init();	
			break;
		case GeoDetectiveScreenType.CREDITS:
			_active.close();
			if (code == GeoDetectiveScreenCode.TRANSITION_TITLE)
				_active = _title;
			else {
				Logger.e(_tag, "Invalid transition");
				return;
			}
			_active.unpause();
			break;
		}
	}
}
