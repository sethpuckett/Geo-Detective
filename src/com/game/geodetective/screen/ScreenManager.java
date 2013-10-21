package com.game.geodetective.screen;

import com.game.geodetective.messaging.IMessageHandler;
import com.game.geodetective.messaging.Message;
import com.game.geodetective.messaging.MessageType;
import com.game.geodetective.utility.ButtonControlType;
import com.game.geodetective.utility.Global;
import com.game.geodetective.utility.Logger;
import com.game.geodetective.utility.Manager;
import com.game.geodetective.utility.android.AllocationGuard;

public class ScreenManager implements IMessageHandler {
	protected static final StringBuffer _tag = new StringBuffer("ScreenManager");
	
	protected Screen _active;
	
	public ScreenManager() {
		
	}
	
	public void init() {
		Manager.Message.subscribe(this, MessageType.GAME_INIT);
	}
	
	public void update(float updateRatio) {
		if (_active != null) {
			_active.update(updateRatio);
			ScreenCode code = _active.getCode();
			if (code != ScreenCode.CONTINUE)
				handleCode(code);
		}
	}

	protected void handleCode(ScreenCode code) {
		AllocationGuard.sGuardActive = false;
		Manager.Sound.close();
		Manager.Sound.init();
		switch (_active.getType()) {
			default:
				break;
		}
		Runtime.getRuntime().gc();
		AllocationGuard.sGuardActive = true;
	}

	@Override
	public void handleMessage(Message message) {
		if (message.Type == MessageType.GAME_INIT) {
			if (Global.Renderer.Width > 0) {
				if (_active != null)
					_active.close();
				//_active = _splash;
				_active.init();
			}
			else
				Manager.Message.subscribe(this, MessageType.SCREEN_SIZE_SET);
		}
		else if (message.Type == MessageType.SCREEN_SIZE_SET) {
			Manager.Message.unsubscribe(this, MessageType.SCREEN_SIZE_SET);
			if (_active != null)
				_active.close();
			//_active = _splash;
			_active.init();
		}
	}

	public boolean onBackDown() {
		boolean ret = false;
		
		if (_active != null) {
			int ctl = _active.getBackButtonControl();
			if (ctl == ButtonControlType.OVERRIDE) {
				_active.onBackDown();
				ret = true;
			}
			else if (ctl == ButtonControlType.IGNORE)
				ret = true;
		}
		
		return ret;
	}
	
	public boolean onMenuDown() {
		boolean ret = false;
		
		if (_active != null) {
			int ctl = _active.getMenuButtonControl();
			if (ctl == ButtonControlType.OVERRIDE) {
				_active.onMenuDown();
				ret = true;
			}
			else if (ctl == ButtonControlType.IGNORE)
				ret = true;
		}
		
		return ret;
	}
}