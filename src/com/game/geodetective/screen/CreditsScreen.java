package com.game.geodetective.screen;

import com.game.geodetective.behavior.GeoDetectiveBehaviorType;
import com.game.geodetective.behavior.ScrollingTileBehavior;
import com.game.geodetective.entity.EntityHelper;
import com.game.geodetective.graphics.GeoDetectiveImage;
import com.game.geodetective.graphics.GeoDetectiveSpriteLayer;
import com.game.loblib.entity.GameEntity;
import com.game.loblib.messaging.Message;
import com.game.loblib.messaging.MessageType;
import com.game.loblib.screen.Screen;
import com.game.loblib.sound.Sound;
import com.game.loblib.utility.ButtonControlType;
import com.game.loblib.utility.Direction;
import com.game.loblib.utility.Global;
import com.game.loblib.utility.Manager;
import com.game.loblib.utility.area.AreaType;

public class CreditsScreen extends Screen {

	protected GameEntity _background;
	protected GameEntity _backButton;
	
	public CreditsScreen() {
		_type = GeoDetectiveScreenType.CREDITS;
		_screenMusic = Sound.CONTINUE_MUSIC;
		_backBtnCtl = ButtonControlType.OVERRIDE;
	}

	@Override
	public void onBackDown() {
		_code = GeoDetectiveScreenCode.TRANSITION_TITLE;
	}
	
	@Override
	public void onHandleMessage(Message message) {
		if (message.Type == MessageType.BUTTON_CLICKED) {
			GameEntity entity = message.getData();
			if (entity == _backButton) {
				_code = GeoDetectiveScreenCode.TRANSITION_TITLE;
			}
		}
	}

	@Override
	public void onInit() {
		_background = EntityHelper.scrollingGraphic(GeoDetectiveImage.SCROLLING_STONE_WALL, GeoDetectiveSpriteLayer.BACKGROUND1, Direction.LEFT, .5f, Global.Data.ScrollingBackgroundPos);
		_entities.add(_background);
		
		_entities.add(EntityHelper.graphic(GeoDetectiveImage.CREDITS,
				GeoDetectiveSpriteLayer.FOREGROUND, false, Global.Renderer.Width, Global.Renderer.Width, false, 0, Global.Renderer.Height - Global.Renderer.Width - (Global.Renderer.Width / 10f)));
		
		_backButton = EntityHelper.button(GeoDetectiveImage.BACK_BUTTON,
				GeoDetectiveSpriteLayer.UI_LOW, false, Global.Renderer.Width / 3f, Global.Renderer.Width / 6f, false, Global.Renderer.Width / 60f, Global.Renderer.Width / 60f, AreaType.Rectangle);
		_entities.add(_backButton);
		Manager.Message.subscribe(this, MessageType.BUTTON_CLICKED);
	}

	@Override
	public void onPause() {
		Manager.Message.unsubscribe(this, MessageType.BUTTON_CLICKED);
	}

	@Override
	public void onUnpause() {
		Manager.Message.subscribe(this, MessageType.BUTTON_CLICKED);
	}

	@Override
	public void onClose() {
		Global.Data.ScrollingBackgroundPos = ((ScrollingTileBehavior)_background.getBehavior(GeoDetectiveBehaviorType.SCROLLING_TILE)).getSpritePosition();
		Manager.Message.unsubscribe(this, MessageType.BUTTON_CLICKED);
	}
}
