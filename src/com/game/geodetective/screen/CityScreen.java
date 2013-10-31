package com.game.geodetective.screen;

import com.game.geodetective.entity.EntityHelper;
import com.game.geodetective.graphics.GeoDetectiveImage;
import com.game.geodetective.graphics.GeoDetectiveSpriteLayer;
import com.game.loblib.entity.GameEntity;
import com.game.loblib.messaging.MessageType;
import com.game.loblib.screen.Screen;
import com.game.loblib.utility.Global;
import com.game.loblib.utility.Manager;

public class CityScreen extends Screen {
	
	private GameEntity _backgroundImage;
	private GameEntity _clock;
	private GameEntity _deadline;
	private GameEntity _cityImage;
	private GameEntity _cityInfoBox;
	private GameEntity _cityInfoScrollUp;
	private GameEntity _cityInfoScrollDown;
	private GameEntity _clueLocation1Button;
	private GameEntity _clueLocation2Button;
	private GameEntity _clueLocation3Button;
	private GameEntity _crimeLabButton;
	
	public CityScreen() {
		_type = GeoDetectiveScreenType.CITY;
	}
	
	@Override
	public void update(float updateRatio) {

	}
	
	@Override
	public void onInit() {

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
