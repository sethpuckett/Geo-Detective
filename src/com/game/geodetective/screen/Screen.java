package com.game.geodetective.screen;

import com.game.geodetective.entity.GameEntity;
import com.game.geodetective.messaging.IMessageHandler;
import com.game.geodetective.messaging.Message;
import com.game.geodetective.messaging.MessageType;
import com.game.geodetective.sound.Sound;
import com.game.geodetective.utility.ButtonControlType;
import com.game.geodetective.utility.Manager;
import com.game.geodetective.utility.android.FixedSizeArray;

// Base class that all game screens should inherit from
public abstract class Screen implements IMessageHandler {
	protected final static StringBuffer _tag = new StringBuffer("Screen");
	
	protected FixedSizeArray<GameEntity> _entities = new FixedSizeArray<GameEntity>(2048);
	protected ScreenType _type = ScreenType.UNKNOWN;
	protected ScreenCode _code = ScreenCode.UNKNOWN;
	// Music that plays on this screen
	protected int _screenMusic = Sound.UNKNOWN;
	// How to handle back button presses on this screen
	protected int _backBtnCtl = ButtonControlType.IGNORE;
	// How to handle menu button presses on this screen
	protected int _menuBtnCtl = ButtonControlType.IGNORE;
	
	public ScreenType getType() {
		return _type;
	}
	
	public ScreenCode getCode() {
		return _code;
	}
	
	public void update(float updateRatio) {
		// no default behavior
	}
	
	public void onBackDown() {
		// no default behavior
	}
	
	public void onMenuDown() {
		// no default behavior
	}
	
	public int getBackButtonControl() {
		return _backBtnCtl;
	}
	
	public int getMenuButtonControl() {
		return _menuBtnCtl;
	}
	
	@Override
	public final void handleMessage(Message message) {
		if (message.Type == MessageType.SOUND_ENABLED) {
			if (_screenMusic != Sound.UNKNOWN)
				Manager.Sound.playMusic(_screenMusic);
		}
		
		onHandleMessage(message);
	}
	
	// subscribes to messages, adds entities to entity manager, and enables all behaviors
	public final void init() {
		_code = ScreenCode.CONTINUE;
		Manager.Message.subscribe(this, MessageType.SOUND_ENABLED);
		if (_screenMusic != Sound.UNKNOWN)
			Manager.Sound.playMusic(_screenMusic);
		else
			Manager.Sound.stopMusic();
		onInit();
		int count = _entities.getCount();
		for (int i = 0; i < count; i++)
			Manager.Entity.addEntity(_entities.get(i));
		enableBehaviors();
	}
	
	public final void pause() {
		onPause();
		Manager.Message.unsubscribe(this, MessageType.SOUND_ENABLED);
		int count = _entities.getCount();
		for (int i = 0; i < count; i++)
			_entities.get(i).pause();
	}
	
	public final void unpause() {
		_code = ScreenCode.CONTINUE;
		Manager.Message.subscribe(this, MessageType.SOUND_ENABLED);
		if (_screenMusic != Sound.UNKNOWN)
			Manager.Sound.playMusic(_screenMusic);
		int count = _entities.getCount();
		for (int i = 0; i < count; i++)
			_entities.get(i).unpause();
		onUnpause();
	}
	
	public final void close() {
		onClose();
		Manager.Message.unsubscribe(this, MessageType.SOUND_ENABLED);
		int count = _entities.getCount();
		for (int i = 0; i < count; i++)
			Manager.Entity.freeEntity(_entities.get(i));
		_entities.clear();
	}
	
	// enables all entity behaviors
	protected void enableBehaviors() {
		int count = _entities.getCount();
		for (int i = 0; i < count; i++)
			_entities.get(i).enableBehaviors();
	}
	
	protected abstract void onInit();
	
	protected abstract void onPause();
	
	protected abstract void onUnpause();
	
	protected abstract void onClose();
	
	protected void onHandleMessage(Message message) {
		
	}
}
