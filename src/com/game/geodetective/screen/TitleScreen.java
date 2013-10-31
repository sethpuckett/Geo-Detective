package com.game.geodetective.screen;


import com.game.geodetective.behavior.GeoDetectiveBehaviorType;
import com.game.geodetective.behavior.ScrollingTileBehavior;
import com.game.geodetective.behavior.TweenBehavior;
import com.game.geodetective.entity.EntityHelper;
import com.game.geodetective.graphics.GeoDetectiveImage;
import com.game.geodetective.graphics.GeoDetectiveSpriteLayer;
import com.game.geodetective.sound.GeoDetectiveSound;
import com.game.loblib.behavior.BehaviorType;
import com.game.loblib.entity.GameEntity;
import com.game.loblib.messaging.Message;
import com.game.loblib.messaging.MessageType;
import com.game.loblib.screen.Screen;
import com.game.loblib.utility.ButtonControlType;
import com.game.loblib.utility.Direction;
import com.game.loblib.utility.Global;
import com.game.loblib.utility.Manager;
import com.game.loblib.utility.TweenType;
import com.game.loblib.utility.area.AreaType;

public class TitleScreen extends Screen {

	protected GameEntity _title;
	protected GameEntity _scrollingBackground;
	protected GameEntity _newGameButton;
	protected GameEntity _continueButton;
	protected GameEntity _creditsButton;
	protected GameEntity _soundButton;
	
	protected boolean _buttonClicked;
	protected boolean _newGameClicked;
	protected boolean _continueClicked;
	protected boolean _levelSelectClicked;
	protected boolean _creditsClicked;
	
	public TitleScreen() {
		_type = GeoDetectiveScreenType.TITLE;
		_screenMusic = GeoDetectiveSound.BADLOOP;
		_backBtnCtl = ButtonControlType.DEFAULT;
	}
	
	@Override
	public void onHandleMessage(Message message) {
		if (message.Type == MessageType.BUTTON_CLICKED && !_buttonClicked) {
			GameEntity entity = message.getData();
			if (entity == _newGameButton) {
				_buttonClicked = true;
				_newGameClicked = true;
//				((TweenBehavior)_newGameButton.getBehavior(BehaviorType.TWEEN)).setTweenTime(500);
//				Manager.Level.setCurrentLevel(1);
			}
			else if (entity == _continueButton) {
				_buttonClicked = true;
				_continueClicked = true;
				((TweenBehavior)_continueButton.getBehavior(GeoDetectiveBehaviorType.TWEEN)).setTweenTime(500);
			}
			else if (entity == _creditsButton) {
				_buttonClicked = true;
				_creditsClicked = true;
				((TweenBehavior)_creditsButton.getBehavior(GeoDetectiveBehaviorType.TWEEN)).setTweenTime(500);
			}
			else if (entity == _soundButton)
				toggleSound();
			
			if (_buttonClicked) {
				if (_newGameButton != null)
					_newGameButton.enableBehaviors(GeoDetectiveBehaviorType.TWEEN);
				if (_continueButton != null)
					_continueButton.enableBehaviors(GeoDetectiveBehaviorType.TWEEN);
				_creditsButton.enableBehaviors(GeoDetectiveBehaviorType.TWEEN);
				_soundButton.enableBehaviors(GeoDetectiveBehaviorType.TWEEN);
				
//				if (_newGameClicked)
//					_code = ScreenCode.TRANSITION_JOURNAL;
//				else if (_continueClicked)
//					_code = ScreenCode.TRANSITION_LEVEL_SELECT;
//				else if (_creditsClicked)
//					_code = ScreenCode.TRANSITION_CREDITS;
			}
		}
		else if (message.Type == MessageType.ANIMATION_FINISHED) {
//			Sprite sprite = message.getData();
//			if (sprite == _titleBg.Attributes.Sprite) {
//				if (_newGameClicked || _continueClicked)
//					_code = ScreenCode.TRANSITION_JOURNAL;
//				else if (_levelSelectClicked)
//					_code = ScreenCode.TRANSITION_LEVEL_SELECT;
//				else if (_creditsClicked)
//					_code = ScreenCode.TRANSITION_CREDITS;
//			}
		}
	}

	@Override
	public void onInit() {
		_buttonClicked = false;
		_newGameClicked = false;
		_continueClicked = false;
		_levelSelectClicked = false;
		_creditsClicked = false;
	
		//_scrollingBackground = scrollingBackground();
		_entities.add(_scrollingBackground);
		
		_title = EntityHelper.graphic(GeoDetectiveImage.TITLE_LOGO, GeoDetectiveSpriteLayer.UI_LOW, false,
				Global.Renderer.Width, Global.Renderer.Width / 2f, 
				false, 0, Global.Renderer.Height - Global.Renderer.Width / 2f);
		_entities.add(_title);
		
//		if (Global.Settings.getCompletedLevelCount() > 0) {
//			_continueButton = continueButton();
//			_entities.add(_continueButton);
//		}
//		else {
		//	_newGameButton = newGameButton();
		//	_entities.add(_newGameButton);
//		}
		
		_creditsButton = creditsButton();
		_entities.add(_creditsButton);
		//_soundButton = soundButton();
		//_entities.add(_soundButton);
		
		Manager.Message.subscribe(this, MessageType.BUTTON_CLICKED | 
				MessageType.ANIMATION_FINISHED | 
				MessageType.TWEEN_FINISHED);
	}
	
	@Override
	protected void enableBehaviors() {
		_title.enableBehaviors();
		_scrollingBackground.enableBehaviors();
		if (_continueButton != null)
			_continueButton.enableBehaviors(BehaviorType.ALL & ~GeoDetectiveBehaviorType.TWEEN);
		if (_newGameButton != null)
			_newGameButton.enableBehaviors(BehaviorType.ALL & ~GeoDetectiveBehaviorType.TWEEN);
		_creditsButton.enableBehaviors(BehaviorType.ALL & ~GeoDetectiveBehaviorType.TWEEN);
		_soundButton.enableBehaviors(BehaviorType.ALL & ~GeoDetectiveBehaviorType.TWEEN);
	}

	@Override
	public void onPause() {
		Global.Data.ScrollingBackgroundPos = ((ScrollingTileBehavior)_scrollingBackground.getBehavior(GeoDetectiveBehaviorType.SCROLLING_TILE)).getSpritePosition();
		Manager.Message.unsubscribe(this, MessageType.ALL);
	}

	@Override
	public void onUnpause() {
		_buttonClicked = false;
		_newGameClicked = false;
		_continueClicked = false;
		_levelSelectClicked = false;
		_creditsClicked = false;
		
		if (_newGameButton != null) {
			_newGameButton.Attributes.Sprite.Alpha = 1f;
			_newGameButton.disableBehaviors(GeoDetectiveBehaviorType.TWEEN);
		}
		if (_continueButton != null) {
			_continueButton.Attributes.Sprite.Alpha = 1f;
			_continueButton.disableBehaviors(GeoDetectiveBehaviorType.TWEEN);
		}
		
		_creditsButton.Attributes.Sprite.Alpha = 1f;
		_creditsButton.disableBehaviors(GeoDetectiveBehaviorType.TWEEN);
		_soundButton.Attributes.Sprite.Alpha = 1f;
		_soundButton.disableBehaviors(GeoDetectiveBehaviorType.TWEEN);
		
		((ScrollingTileBehavior)_scrollingBackground.getBehavior(GeoDetectiveBehaviorType.SCROLLING_TILE)).setSpritePosition(Global.Data.ScrollingBackgroundPos);
		
		Manager.Message.subscribe(this, MessageType.BUTTON_CLICKED | MessageType.ANIMATION_FINISHED | MessageType.TWEEN_FINISHED);
	}

	@Override
	public void onClose() {
		Global.Data.ScrollingBackgroundPos = ((ScrollingTileBehavior)_scrollingBackground.getBehavior(GeoDetectiveBehaviorType.SCROLLING_TILE)).getSpritePosition();
		Manager.Message.unsubscribe(this, MessageType.ALL);
	}

	@Override
	public void onBackDown() {
		//Global.Activity.finish();
	}
	
	protected void toggleSound() {
		if (Manager.Sound.isEnabled()) {
			Manager.Sound.disableSound();
			_soundButton.Attributes.Sprite.setFrame(1);
		}
		else {
			Manager.Sound.enableSound();
			_soundButton.Attributes.Sprite.setFrame(0);
		}
	}

	/*********************************
	 * Entities
	 ********************************/
	protected GameEntity creditsButton() {
		GameEntity button = EntityHelper.button(GeoDetectiveImage.CREDITS_BUTTON, 
				GeoDetectiveSpriteLayer.UI_LOW, false, Global.Renderer.Width / 6f, Global.Renderer.Width / 6f, 
				false, Global.Renderer.Width / 60f, Global.Renderer.Width / 60f, AreaType.Circle);
		button.addBehavior(new TweenBehavior(TweenType.ALPHA, -1f, 250));
		return button;
	}
	
	//protected GameEntity soundButton() {
		//GameEntity button = EntityHelper.button(GeoDetectiveImage.SOUND_BUTTON,
		//		GeoDetectiveSpriteLayer.UI_LOW, false, Global.Renderer.Width / 6f, Global.Renderer.Width / 6f, false, 
		//		Global.Renderer.Width - (11f * Global.Renderer.Width / 60f), Global.Renderer.Width / 60f, 
		//		AreaType.Circle);
		//button.addBehavior(new TweenBehavior(TweenType.ALPHA, -1f, 250));
		
		//if (Manager.Sound.isEnabled())
		//	button.Attributes.Sprite.setFrame(0);
		//else
		//	button.Attributes.Sprite.setFrame(1);
		
		//return button;
	//}
	
//	protected GameEntity scrollingBackground() {
//		return EntityHelper.scrollingGraphic(GeoDetectiveImage.SCROLLING_STONE_WALL, GeoDetectiveSpriteLayer.BACKGROUND1, Direction.LEFT, .5f, Global.Data.ScrollingBackgroundPos);
//	}
//	
//	protected GameEntity newGameButton() {
//		return titleButton(true, GeoDetectiveImage.NEW_GAME_BUTTON);
//	}
	
	protected GameEntity continueButton() {
		return titleButton(true, GeoDetectiveImage.CONTINUE_BUTTON);
	}
	
	protected GameEntity titleButton(boolean top, int image) {
		float centerX = Global.Renderer.Width / 2f;
		float centerY = 0;
		
		if  (top)
			centerY = Global.Renderer.Height / 2f;
		else
			centerY = Global.Renderer.Height / 2f - Global.Renderer.Width / 4f;
		
		GameEntity entity = EntityHelper.button(image, 
				GeoDetectiveSpriteLayer.UI_LOW, 
				false, 
				Global.Renderer.Width / 2.5f, 
				Global.Renderer.Width / 5f,
				true,
				centerX,
				centerY,
				AreaType.Rectangle);
		entity.addBehavior(new TweenBehavior(TweenType.ALPHA, -1f, 250));
		
		return entity;
	}
}
