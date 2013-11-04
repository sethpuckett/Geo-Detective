package com.game.geodetective.screen;


import com.game.geodetective.entity.EntityHelper;
import com.game.geodetective.graphics.GeoDetectiveImage;
import com.game.geodetective.graphics.GeoDetectiveSpriteLayer;
import com.game.geodetective.sound.GeoDetectiveSound;
import com.game.geodetective.utility.GeoDetectiveGlobal;
import com.game.loblib.entity.GameEntity;
import com.game.loblib.messaging.Message;
import com.game.loblib.messaging.MessageType;
import com.game.loblib.screen.Screen;
import com.game.loblib.utility.ButtonControlType;
import com.game.loblib.utility.Manager;
import com.game.loblib.utility.area.AreaType;

public class TitleScreen extends Screen {

	protected GameEntity _title;
	protected GameEntity _background;
	protected GameEntity _newGameButton;
	protected GameEntity _continueButton;
	protected GameEntity _creditsButton;
	protected GameEntity _optionsButton;
	protected GameEntity _statsButton;
	
	public TitleScreen() {
		_type = GeoDetectiveScreenType.TITLE;
		_screenMusic = GeoDetectiveSound.BADLOOP;
		_backBtnCtl = ButtonControlType.DEFAULT;
	}
	
	@Override
	public void onHandleMessage(Message message) {
		if (message.Type == MessageType.BUTTON_CLICKED) {
			GameEntity entity = message.getData();
			if (entity == _newGameButton) {
				// TODO: check if there is already an open case and provide a warning if one exists
				initializeNewCase();
				_code = GeoDetectiveScreenCode.TRANSITION_CASE_DESCRIPTION;
			}
			else if (entity == _continueButton) {

			}
			else if (entity == _creditsButton) {
				
			}
			else if (entity == _optionsButton) {
				
			}
			else if (entity == _statsButton) {
				
			}
		}
	}

	@Override
	public void onInit() {
		Manager.Message.subscribe(this, MessageType.BUTTON_CLICKED);
		
		_newGameButton = newGameButton();
		_entities.add(_newGameButton);
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
	
	private void initializeNewCase() {
		GeoDetectiveGlobal.DataAccess.createNewCase();
	}
	
	protected GameEntity newGameButton() {
		return titleButton(true, GeoDetectiveImage.NEW_CASE_BUTTON);
	}
	
	protected GameEntity continueButton() {
		return titleButton(true, GeoDetectiveImage.CONTINUE_BUTTON);
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
