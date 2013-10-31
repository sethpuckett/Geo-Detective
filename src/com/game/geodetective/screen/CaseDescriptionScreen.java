package com.game.geodetective.screen;

import com.game.loblib.entity.GameEntity;
import com.game.loblib.messaging.Message;
import com.game.loblib.messaging.MessageType;
import com.game.loblib.screen.Screen;
import com.game.loblib.sound.Sound;
import com.game.loblib.utility.ButtonControlType;
import com.game.loblib.utility.Manager;

public class CaseDescriptionScreen extends Screen {
	
	private GameEntity _backgroundImage;
	private GameEntity _clock;
	private GameEntity _breakingImage;
	private GameEntity _caseInfoBox;
	private GameEntity _caseInfoScrollUp;
	private GameEntity _caseInfoScrollDown;
	private GameEntity _goToCaseButton;
	
	public CaseDescriptionScreen() {
		_type = GeoDetectiveScreenType.CASE_DESCRIPTION;
		_screenMusic = Sound.CONTINUE_MUSIC;
		_menuBtnCtl = ButtonControlType.IGNORE;
		_backBtnCtl = ButtonControlType.OVERRIDE;
	}
	
	@Override
	public void onBackDown() {
		_code = GeoDetectiveScreenCode.TRANSITION_CITY;
	}
	
	@Override
	public void onHandleMessage(Message message) {
		if (message.Type == MessageType.BUTTON_CLICKED) {
			GameEntity entity = message.getData();
			if (entity == _goToCaseButton) {
				_code = GeoDetectiveScreenCode.TRANSITION_CITY;
			}
		}
	}
	
	@Override
	public void update(float updateRatio) {
		// Nothing to do here
	}
	
	@Override
	public void onInit() {
		// TODO: set game setting variable indicating city
		
		//_backgroundImage = EntityHelper.graphic(GeoDetectiveImage.city, layer, useCamera, sizeX, sizeY, center, positionX, positionY)
	}

	@Override
	public void onPause() {
		Manager.Message.unsubscribe(this, MessageType.ALL);
	}

	@Override
	public void onUnpause() {
		Manager.Message.subscribe(this, MessageType.BUTTON_CLICKED);
	}

	@Override
	public void onClose() {
		Manager.Message.unsubscribe(this, MessageType.ALL);
	}
}
