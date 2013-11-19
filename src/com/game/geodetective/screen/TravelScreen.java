package com.game.geodetective.screen;

import com.game.geodetective.data.entity.CaseState;
import com.game.geodetective.data.entity.City;
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


public class TravelScreen extends Screen {
	
	private GameEntity _backgroundImage;
	private GameEntity _clock;
	private GameEntity _deadline;
	private GameEntity _clockBorder;
	private GameEntity _cityLabel;
	private GameEntity _worldImage;
	private GameEntity _destination1Button;
	private GameEntity _destination2Button;
	private GameEntity _destination3Button;
	private GameEntity _destination4Button;
	private GameEntity _destination5Button;
	private GameEntity _destination6Button;
	private GameEntity _backButton;
	private GameEntity _travelButton;
	private GameEntity _cityIndicator;
	private GameEntity _travelTimeLabel;
	
	private CaseState _state;
	private City _currentCity;
	private City _previousCity;
	private City[] _availableCities;
	
	public TravelScreen() {
		_type = GDScreenType.TRAVEL;
	}
	
	@Override
	public void onHandleMessage(Message message) {
		if (message.Type == MessageType.BUTTON_CLICKED) {
			GameEntity entity = message.getData();
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
		if (_state.PreviousCityId > 0)
			_previousCity = GDGlobal.DataAccess.getCity(_state.PreviousCityId);
		_availableCities = GDGlobal.DataAccess.getAvailableCitiesForCurrentCase();
		
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
		_worldImage = EntityHelper.graphic(GDImage.TRAVEL_WORLD,
				GDSpriteLayer.UI_HIGH, 
				false, 
				LayoutHelper.WidthSubFrac(1f, 6f),
				LayoutHelper.WidthFrac(2f),
				true, 
				LayoutHelper.WidthFrac(2f),
				cityImageHeight);
		_entities.add(_worldImage);
		
		float location1Height = cityImageHeight - LayoutHelper.WidthSubFrac(2f, 4f) - LayoutHelper.HeightFrac(30f);
		if (_previousCity != null) {
			_destination1Button = EntityHelper.textButton(CityHelper.getLabel(_previousCity), 
					false,
					false, 
					false, 
					LayoutHelper.WidthFrac(12f),
					location1Height, 
					1f, 
					1f, 
					1f, 
					1f);
			_entities.add(_destination1Button);
		}
		
		float location2Height = location1Height - LayoutHelper.HeightFrac(40f) - LayoutHelper.HeightFrac(30f);
		_destination2Button = EntityHelper.textButton(CityHelper.getLabel(_availableCities[0]), 
				false,
				false, 
				false, 
				LayoutHelper.WidthFrac(12f),
				location2Height, 
				1f, 
				1f, 
				1f, 
				1f);
		_entities.add(_destination2Button);
		
		float location3Height = location2Height - LayoutHelper.HeightFrac(40f) - LayoutHelper.HeightFrac(30f);
		_destination3Button = EntityHelper.textButton(CityHelper.getLabel(_availableCities[1]), 
				false,
				false, 
				false, 
				LayoutHelper.WidthFrac(12f),
				location3Height, 
				1f, 
				1f, 
				1f, 
				1f);
		_entities.add(_destination3Button);
		
		float location4Height = location3Height - LayoutHelper.HeightFrac(40f) - LayoutHelper.HeightFrac(30f);
		_destination4Button = EntityHelper.textButton(CityHelper.getLabel(_availableCities[2]), 
				false,
				false, 
				false, 
				LayoutHelper.WidthFrac(12f),
				location4Height, 
				1f, 
				1f, 
				1f, 
				1f);
		_entities.add(_destination4Button);
		
		float location5Height = location4Height - LayoutHelper.HeightFrac(40f) - LayoutHelper.HeightFrac(30f);
		if (_availableCities.length > 3) {
			_destination5Button = EntityHelper.textButton(CityHelper.getLabel(_availableCities[3]), 
					false,
					false, 
					false, 
					LayoutHelper.WidthFrac(12f),
					location5Height, 
					1f, 
					1f, 
					1f, 
					1f);
			_entities.add(_destination5Button);
		}
		
		float location6Height = location5Height - LayoutHelper.HeightFrac(40f) - LayoutHelper.HeightFrac(30f);
		if (_availableCities.length > 4) {
			_destination6Button = EntityHelper.textButton(CityHelper.getLabel(_availableCities[4]), 
					false,
					false, 
					false, 
					LayoutHelper.WidthFrac(12f),
					location6Height, 
					1f, 
					1f, 
					1f, 
					1f);
			_entities.add(_destination6Button);
		}
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
