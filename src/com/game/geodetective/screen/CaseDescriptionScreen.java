package com.game.geodetective.screen;

import com.game.geodetective.data.entity.Crime;
import com.game.geodetective.entity.EntityHelper;
import com.game.geodetective.graphics.GDImage;
import com.game.geodetective.graphics.GDSpriteLayer;
import com.game.geodetective.utility.GDGlobal;
import com.game.loblib.entity.GameEntity;
import com.game.loblib.messaging.Message;
import com.game.loblib.messaging.MessageType;
import com.game.loblib.screen.Screen;
import com.game.loblib.sound.Sound;
import com.game.loblib.utility.ButtonControlType;
import com.game.loblib.utility.LayoutHelper;
import com.game.loblib.utility.Manager;
import com.game.loblib.utility.area.AreaType;

public class CaseDescriptionScreen extends Screen {
	
	private GameEntity _backgroundImage; // detectives office, top 2/3 of screen
	private GameEntity _newspaperImage; // covers background, shows crime
	private GameEntity _borderImage; // separates background from control area
	private GameEntity _instructions; // tells player they have 1 week to solve case
	private GameEntity _goToCaseButton; // button to start case
	
	public CaseDescriptionScreen() {
		_type = GDScreenType.CASE_DESCRIPTION;
		_screenMusic = Sound.CONTINUE_MUSIC;
		_menuBtnCtl = ButtonControlType.IGNORE;
		_backBtnCtl = ButtonControlType.OVERRIDE;
	}
	
	@Override
	public void onBackDown() {
		_code = GDScreenCode.TRANSITION_CITY;
	}
	
	@Override
	public void onHandleMessage(Message message) {
		if (message.Type == MessageType.BUTTON_CLICKED) {
			GameEntity entity = message.getData();
			if (entity == _goToCaseButton) {
				_code = GDScreenCode.TRANSITION_CITY;
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
		
		GDGlobal.Renderer.setTextProperties(0f, 0f, 0f, 1f);
		
		_backgroundImage = EntityHelper.graphic(GDImage.CASE_DESCRIPTION_BACKGROUND,
				GDSpriteLayer.UI_LOW, 
				false,
				LayoutHelper.Width(),
				LayoutHelper.WidthMul(2f),
				false, 
				0,
				GDGlobal.Renderer.Height / 3f);
		_entities.add(_backgroundImage);
		
		_newspaperImage = EntityHelper.graphic(GDImage.CASE_DESCRIPTION_NEWSPAPER,
				GDSpriteLayer.UI_HIGH, 
				false, 
				LayoutHelper.WidthSubFrac(1f, 16f),
				LayoutHelper.WidthSubFrac(1f, 16f),
				true, 
				LayoutHelper.WidthSubFrac(1f, 2f),
				LayoutHelper.HeightSubFrac(1f, 3f));
		_entities.add(_newspaperImage);
		
		_borderImage = EntityHelper.graphic(GDImage.CASE_DESCRIPTION_BORDER,
				GDSpriteLayer.UI_HIGH, 
				false, 
				LayoutHelper.Width(),
				LayoutHelper.WidthFrac(24f),
				true, 
				LayoutHelper.WidthFrac(2f),
				LayoutHelper.HeightFrac(3f));
		_entities.add(_borderImage);
		
		_instructions = EntityHelper.graphic(GDImage.CASE_DESCRIPTION_INSTRUCTIONS,
				GDSpriteLayer.UI_HIGH, 
				false, 
				LayoutHelper.Width(),
				LayoutHelper.WidthFrac(8f),
				true, 
				LayoutHelper.WidthFrac(2f),
				LayoutHelper.HeightSubFrac(3f, 24f));
		_entities.add(_instructions);
		
		_goToCaseButton = EntityHelper.button(GDImage.CONTINUE_BUTTON, 
				GDSpriteLayer.UI_LOW, 
				false, 
				LayoutHelper.WidthFrac(2.5f),
				LayoutHelper.WidthFrac(5f),
				true,
				LayoutHelper.WidthFrac(2f),
				LayoutHelper.HeightFrac(6f),
				AreaType.Rectangle);
		_entities.add(_goToCaseButton);

		Crime crime = GDGlobal.DataAccess.getCrimeForCurrentCase();

		float height1 = LayoutHelper.HeightAddFrac(1f,  3f);
		float height2 = height1 - LayoutHelper.HeightFrac(24f);
		GameEntity setupText = EntityHelper.text("An unknown assailant has stolen", LayoutHelper.WidthAddFrac(2f, 24f), height1, true, false);
		GameEntity crimeText = EntityHelper.text(crime.CrimeText,LayoutHelper.WidthAddFrac(2f, 24f), height2, true, false);
		_entities.add(setupText);	
		_entities.add(crimeText);
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
