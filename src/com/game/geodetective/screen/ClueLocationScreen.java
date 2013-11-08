package com.game.geodetective.screen;

import com.game.geodetective.data.entity.CaseState;
import com.game.geodetective.data.entity.City;
import com.game.geodetective.data.entity.ClueLocation;
import com.game.geodetective.entity.EntityHelper;
import com.game.geodetective.graphics.GDImage;
import com.game.geodetective.graphics.GDSpriteLayer;
import com.game.geodetective.utility.CityHelper;
import com.game.geodetective.utility.GDGlobal;
import com.game.geodetective.utility.GDTimeHelper;
import com.game.loblib.entity.GameEntity;
import com.game.loblib.messaging.Message;
import com.game.loblib.messaging.MessageType;
import com.game.loblib.screen.Screen;
import com.game.loblib.utility.LayoutHelper;
import com.game.loblib.utility.Manager;
import com.game.loblib.utility.area.AreaType;

public class ClueLocationScreen extends Screen {
	
	private GameEntity _backgroundImage;
	private GameEntity _clock;
	private GameEntity _deadline;
	private GameEntity _clockBorder;
	private GameEntity _locationLabel;
	private GameEntity _locationImage;
	private GameEntity _clueBox;
	private GameEntity _clueScrollUp;
	private GameEntity _clueScrollDown;
	private GameEntity _returnToCityButton;
	
	private CaseState _state;
	private ClueLocation _clueLocation;
	
	public ClueLocationScreen() {
		_type = GDScreenType.CLUE_LOCATION;
	}
	
	@Override
	public void onHandleMessage(Message message) {
		if (message.Type == MessageType.BUTTON_CLICKED) {
			GameEntity entity = message.getData();
			if (entity == _returnToCityButton)
				_code = GDScreenCode.TRANSITION_CITY;
		}
	}
	
	@Override
	public void update(float updateRatio) {

	}
	
	@Override
	public void onInit() {
		Manager.Message.subscribe(this, MessageType.BUTTON_CLICKED);
		
		_state = GDGlobal.DataAccess.getCurrentCaseState();
		_clueLocation = GDGlobal.DataAccess.getClueLocation(_state.CurrentClueLocationId);
		
		float clockHeight = LayoutHelper.HeightSubFrac(1f, 24f);
		StringBuffer timeString = new StringBuffer();
		timeString.append("Current: ");
		timeString.append(GDTimeHelper.getTimeString(_state.CurrentHour));
		_clock = EntityHelper.text(timeString.toString(), 
				LayoutHelper.WidthFrac(30), 
				clockHeight, 
				false, 
				false);
		_entities.add(_clock);
		
		StringBuffer deadlineString = new StringBuffer();
		deadlineString.append("Deadline: ");
		deadlineString.append(GDTimeHelper.getTimeString(_state.DeadlineHour));
		_deadline = EntityHelper.text(deadlineString.toString(), 
				LayoutHelper.WidthAddFrac(2f, 15f), 
				clockHeight, 
				false, 
				false);
		_entities.add(_deadline);
		
		float borderHeight = clockHeight - LayoutHelper.HeightFrac(30f);
		_clockBorder = EntityHelper.graphic(GDImage.CITY_CLOCK_BORDER,
				GDSpriteLayer.UI_HIGH, 
				false, 
				LayoutHelper.Width(),
				LayoutHelper.WidthFrac(24f),
				true, 
				LayoutHelper.WidthFrac(2f),
				borderHeight);
		_entities.add(_clockBorder);
		
		float locationLabelHeight = borderHeight - LayoutHelper.HeightFrac(20f);
		_locationLabel = EntityHelper.text(_clueLocation.Name, 
				LayoutHelper.WidthFrac(2f), 
				locationLabelHeight, 
				true, 
				false);
		_entities.add(_locationLabel);
		
		float locationImageHeight = locationLabelHeight - LayoutHelper.WidthFrac(4f);
		_locationImage = EntityHelper.graphic(CityHelper.GetClueLocationImage(_clueLocation),
				GDSpriteLayer.UI_HIGH, 
				false, 
				LayoutHelper.WidthSubFrac(1f, 6f),
				LayoutHelper.WidthFrac(2f),
				true, 
				LayoutHelper.WidthFrac(2f),
				locationImageHeight);
		_entities.add(_locationImage);
		
		// TODO: scroll images need to change if text can be scrolled
		float scrollUpHeight = locationImageHeight - LayoutHelper.WidthSubFrac(2f, 4f) - LayoutHelper.HeightFrac(40f);
		_clueScrollUp = EntityHelper.graphic(GDImage.CITY_SCROLL_UP_CLOSED,
				GDSpriteLayer.UI_HIGH, 
				false, 
				LayoutHelper.WidthFrac(16f),
				LayoutHelper.WidthFrac(16f),
				true, 
				LayoutHelper.WidthFrac(2f),
				scrollUpHeight);
		_entities.add(_clueScrollUp);
		
		// TODO: scroll images need to change if text can be scrolled
		float clueHeight = scrollUpHeight - LayoutHelper.HeightFrac(30f) - LayoutHelper.WidthFrac(12f);
		_clueBox = EntityHelper.graphic(GDImage.CLUE_LOCATION_CLUE_TEXT_BOX,
				GDSpriteLayer.UI_HIGH, 
				false, 
				LayoutHelper.WidthSubFrac(1f, 6f),
				LayoutHelper.HeightFrac(5f),
				true, 
				LayoutHelper.WidthFrac(2f),
				clueHeight);
		_entities.add(_clueBox);
		
		float scrollDownHeight = clueHeight - LayoutHelper.WidthFrac(12f) - LayoutHelper.HeightFrac(30f);
		_clueScrollDown = EntityHelper.graphic(GDImage.CITY_SCROLL_DOWN_OPEN,
				GDSpriteLayer.UI_HIGH, 
				false, 
				LayoutHelper.WidthFrac(16f),
				LayoutHelper.WidthFrac(16f),
				true, 
				LayoutHelper.WidthFrac(2f),
				scrollDownHeight);
		_entities.add(_clueScrollDown);
		
		float backButtonHeight = scrollDownHeight - LayoutHelper.HeightFrac(30f) - LayoutHelper.HeightFrac(30f);
		_returnToCityButton = EntityHelper.button(GDImage.CLUE_LOCATION_BACK_BUTTON,
				GDSpriteLayer.UI_HIGH, 
				false, 
				GDGlobal.Renderer.Width / 2.5f, 
				GDGlobal.Renderer.Width / 5f,
				true,
				LayoutHelper.WidthFrac(2f),
				backButtonHeight,
				AreaType.Rectangle);
		_entities.add(_returnToCityButton);
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
