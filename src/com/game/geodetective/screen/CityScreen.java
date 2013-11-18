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

public class CityScreen extends Screen {
	
	private GameEntity _backgroundImage;
	private GameEntity _clock;
	private GameEntity _deadline;
	private GameEntity _clockBorder;
	private GameEntity _cityLabel;
	private GameEntity _cityImage;
	private GameEntity _cityInfoBox;
	private GameEntity _cityInfoScrollUp;
	private GameEntity _cityInfoScrollDown;
	private GameEntity _clueLocation1Button;
	private GameEntity _clueLocation2Button;
	private GameEntity _clueLocation3Button;
	private GameEntity _crimeLabButton;
	private GameEntity _footstepAnimation;
	private GameEntity _travelButton;
	
	private CaseState _state;
	private City _currentCity;
	private ClueLocation[] _clueLocations;
	
	public CityScreen() {
		_type = GDScreenType.CITY;
	}
	
	@Override
	public void onHandleMessage(Message message) {
		if (message.Type == MessageType.BUTTON_CLICKED) {
			GameEntity entity = message.getData();
			int clueNumber = 0;
			
			if (entity == _clueLocation1Button)
				clueNumber = 1;
			else if (entity == _clueLocation2Button)
				clueNumber = 2;
			else if (entity == _clueLocation3Button)
				clueNumber = 3;
			else if (entity == _crimeLabButton) {
				
			}
			else if (entity == _travelButton) {
				_code = GDScreenCode.TRANSITION_TRAVEL;
			}
			
			if (clueNumber > 0)
				clueSelect(clueNumber);
		}
	}
	
	@Override
	public void update(float updateRatio) {

	}
	
	@Override
	public void onInit() {
		Manager.Message.subscribe(this, MessageType.BUTTON_CLICKED);
		
		_state = GDGlobal.DataAccess.getCurrentCaseState();
		_currentCity = GDGlobal.DataAccess.getCity(_state.CurrentCityId);
		_clueLocations = GDGlobal.DataAccess.getClueLocationsOrCreateForCurrentCase(3);
		
		float clockHeight = LayoutHelper.HeightSubFrac(1f, 24f);
		StringBuffer timeString = new StringBuffer();
		timeString.append("Current: ");
		timeString.append(GDTimeHelper.getTimeString(_state.CurrentHour));
		_clock = EntityHelper.text(timeString.toString(), 
				LayoutHelper.WidthFrac(30), 
				clockHeight, 
				false, 
				false,
				0f, 1f, 1f, 1f, 1f);
		_entities.add(_clock);
		
		StringBuffer deadlineString = new StringBuffer();
		deadlineString.append("Deadline: ");
		deadlineString.append(GDTimeHelper.getTimeString(_state.DeadlineHour));
		_deadline = EntityHelper.text(deadlineString.toString(), 
				LayoutHelper.WidthAddFrac(2f, 15f), 
				clockHeight, 
				false, 
				false,
				0f, 1f, 1f, 1f, 1f);
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
		
		float cityLabelHeight = borderHeight - LayoutHelper.HeightFrac(20f);
		_cityLabel = EntityHelper.text(CityHelper.getLabel(_currentCity), 
				LayoutHelper.WidthFrac(2f), 
				cityLabelHeight, 
				true, 
				false,
				0f,
				1f,
				1f,
				1f,
				1f);
		_entities.add(_cityLabel);
		
		float cityImageHeight = cityLabelHeight - LayoutHelper.WidthFrac(4f);
		_cityImage = EntityHelper.graphic(CityHelper.getCityImage(_currentCity),
				GDSpriteLayer.UI_HIGH, 
				false, 
				LayoutHelper.WidthSubFrac(1f, 6f),
				LayoutHelper.WidthFrac(2f),
				true, 
				LayoutHelper.WidthFrac(2f),
				cityImageHeight);
		_entities.add(_cityImage);
		
		// TODO: scroll images need to change if text can be scrolled
		float scrollUpHeight = cityImageHeight - LayoutHelper.WidthSubFrac(2f, 4f) - LayoutHelper.HeightFrac(40f);
		_cityInfoScrollUp = EntityHelper.graphic(GDImage.CITY_SCROLL_UP_CLOSED,
				GDSpriteLayer.UI_HIGH, 
				false, 
				LayoutHelper.WidthFrac(16f),
				LayoutHelper.WidthFrac(16f),
				true, 
				LayoutHelper.WidthFrac(2f),
				scrollUpHeight);
		_entities.add(_cityInfoScrollUp);
		
		// TODO: scroll images need to change if text can be scrolled
		float infoBoxHeight = scrollUpHeight - LayoutHelper.HeightFrac(30f) - LayoutHelper.WidthFrac(12f);
		_cityInfoBox = EntityHelper.graphic(GDImage.CITY_INFO_TEXT_BOX,
				GDSpriteLayer.UI_HIGH, 
				false, 
				LayoutHelper.WidthSubFrac(1f, 6f),
				LayoutHelper.HeightFrac(5f),
				true, 
				LayoutHelper.WidthFrac(2f),
				infoBoxHeight);
		_entities.add(_cityInfoBox);
		
		float scrollDownHeight = infoBoxHeight - LayoutHelper.WidthFrac(12f) - LayoutHelper.HeightFrac(30f);
		_cityInfoScrollDown = EntityHelper.graphic(GDImage.CITY_SCROLL_DOWN_OPEN,
				GDSpriteLayer.UI_HIGH, 
				false, 
				LayoutHelper.WidthFrac(16f),
				LayoutHelper.WidthFrac(16f),
				true, 
				LayoutHelper.WidthFrac(2f),
				scrollDownHeight);
		_entities.add(_cityInfoScrollDown);
		
		float location1Height = scrollDownHeight - LayoutHelper.HeightFrac(30f) - LayoutHelper.HeightFrac(15f);
		_clueLocation1Button = EntityHelper.button(CityHelper.GetClueLocationLabel(_clueLocations[0]), 
				GDSpriteLayer.UI_HIGH, 
				false, 
				LayoutHelper.WidthSubFrac(1f, 4f), 
				LayoutHelper.WidthSubFrac(8f, 32f), 
				true, 
				LayoutHelper.WidthSubFrac(2f, 12f), 
				location1Height, 
				AreaType.Rectangle);
		_entities.add(_clueLocation1Button);
		
		float location2Height = location1Height - LayoutHelper.HeightFrac(20f) - LayoutHelper.HeightFrac(20f);
		_clueLocation2Button = EntityHelper.button(CityHelper.GetClueLocationLabel(_clueLocations[1]), 
				GDSpriteLayer.UI_HIGH, 
				false, 
				LayoutHelper.WidthSubFrac(1f, 4f), 
				LayoutHelper.WidthSubFrac(8f, 32f),
				true, 
				LayoutHelper.WidthSubFrac(2f, 12f), 
				location2Height, 
				AreaType.Rectangle);
		_entities.add(_clueLocation2Button);
		
		float location3Height = location2Height - LayoutHelper.HeightFrac(20f) - LayoutHelper.HeightFrac(20f);
		_clueLocation3Button = EntityHelper.button(CityHelper.GetClueLocationLabel(_clueLocations[2]), 
				GDSpriteLayer.UI_HIGH, 
				false, 
				LayoutHelper.WidthSubFrac(1f, 4f), 
				LayoutHelper.WidthSubFrac(8f, 32f),
				true, 
				LayoutHelper.WidthSubFrac(2f, 12f), 
				location3Height, 
				AreaType.Rectangle);
		_entities.add(_clueLocation3Button);
		
		_travelButton = EntityHelper.button(GDImage.CITY_TRAVEL_CENTER_BUTTON, 
				GDSpriteLayer.UI_HIGH, 
				false, 
				LayoutHelper.WidthFrac(4f), 
				LayoutHelper.WidthFrac(4f),
				true, 
				LayoutHelper.WidthSubFrac(1f, 8f), 
				location1Height, 
				AreaType.Rectangle);
		_entities.add(_travelButton);
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

	private void clueSelect(int clueNumber) {
		// TODO: show time passing animation
		
		_state.CurrentClueLocationId = _clueLocations[clueNumber - 1]._id;
		GDGlobal.DataAccess.setCurrentClueLocation(_state);
		_code = GDScreenCode.TRANSITION_CLUE_LOCATION;
	}
}
