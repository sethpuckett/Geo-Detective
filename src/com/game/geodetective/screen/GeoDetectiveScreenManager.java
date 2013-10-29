package com.game.geodetective.screen;

import com.game.loblib.messaging.IMessageHandler;
import com.game.loblib.messaging.Message;
import com.game.loblib.messaging.MessageType;
import com.game.loblib.screen.ScreenManager;
import com.game.loblib.utility.Global;
import com.game.loblib.utility.Manager;
import com.game.geodetective.screen.SplashScreen;

public class GeoDetectiveScreenManager extends ScreenManager implements IMessageHandler {

	protected SplashScreen _splash = new SplashScreen();
	
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
}
