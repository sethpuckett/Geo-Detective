package com.game.geodetective.screen;

import com.game.geodetective.behavior.TextRenderBehavior;
import com.game.geodetective.entity.EntityHelper;
import com.game.geodetective.graphics.GeoDetectiveImage;
import com.game.geodetective.graphics.GeoDetectiveSpriteLayer;
import com.game.geodetective.utility.GeoDetectiveGlobal;
import com.game.loblib.entity.GameEntity;
import com.game.loblib.messaging.Message;
import com.game.loblib.messaging.MessageType;
import com.game.loblib.screen.Screen;
import com.game.loblib.sound.Sound;
import com.game.loblib.utility.ButtonControlType;
import com.game.loblib.utility.Manager;
import com.game.loblib.utility.area.AreaType;

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
		Manager.Message.subscribe(this, MessageType.BUTTON_CLICKED);
		
		_goToCaseButton = continueButton();
		_entities.add(_goToCaseButton);
		
		GameEntity textTest = new GameEntity();
		textTest.Attributes.Area.Position.X = 50;
		textTest.Attributes.Area.Position.Y = 50;
		textTest.addBehavior(new TextRenderBehavior("This is a test."));
		_entities.add(textTest);	
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
	
	protected GameEntity continueButton() {
		return titleButton(false, GeoDetectiveImage.CONTINUE_BUTTON);
	}
	
	protected GameEntity titleButton(boolean top, int image) {
		float centerX = GeoDetectiveGlobal.Renderer.Width / 2f;
		float centerY = 0;
		
		if  (top)
			centerY = GeoDetectiveGlobal.Renderer.Height / 2f;
		else
			centerY = GeoDetectiveGlobal.Renderer.Height / 2f - GeoDetectiveGlobal.Renderer.Width / 4f;
		
		GameEntity entity = EntityHelper.button(image, 
				GeoDetectiveSpriteLayer.UI_LOW, 
				false, 
				GeoDetectiveGlobal.Renderer.Width / 2.5f, 
				GeoDetectiveGlobal.Renderer.Width / 5f,
				true,
				centerX,
				centerY,
				AreaType.Rectangle);
		
		return entity;
	}
}
