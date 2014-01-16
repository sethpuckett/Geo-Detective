package com.game.geodetective.screen;

import com.game.geodetective.behavior.GDBehaviorType;
import com.game.geodetective.behavior.TextRenderBehavior;
import com.game.geodetective.data.entity.CaseState;
import com.game.geodetective.data.entity.City;
import com.game.geodetective.data.entity.DifficultyType;
import com.game.geodetective.entity.EntityHelper;
import com.game.geodetective.graphics.GDImage;
import com.game.geodetective.graphics.GDSpriteLayer;
import com.game.geodetective.utility.CityHelper;
import com.game.geodetective.utility.GDCommonData;
import com.game.geodetective.utility.GDGlobal;
import com.game.geodetective.utility.GDTimeHelper;
import com.game.loblib.entity.GameEntity;
import com.game.loblib.messaging.Message;
import com.game.loblib.messaging.MessageType;
import com.game.loblib.screen.Screen;
import com.game.loblib.utility.LayoutHelper;
import com.game.loblib.utility.Manager;
import com.game.loblib.utility.area.AreaType;


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
	
	private int _destinationSelected;
	
	public TravelScreen() {
		_type = GDScreenType.TRAVEL;
	}
	
	@Override
	public void onHandleMessage(Message message) {
		if (message.Type == MessageType.BUTTON_CLICKED) {
			GameEntity entity = message.getData();
			boolean destinationChanged = false;
			
			if (entity == _backButton) {
				_code = GDScreenCode.TRANSITION_CITY;
			}
			else if (entity == _destination1Button) {
				destinationChanged = true;
				_destinationSelected = 1;
			}
			else if (entity == _destination2Button) {
				destinationChanged = true;
				_destinationSelected = 2;
			}
			else if (entity == _destination3Button) {
				destinationChanged = true;
				_destinationSelected = 3;
			}
			else if (entity == _destination4Button) {
				destinationChanged = true;
				_destinationSelected = 4;
			}
			else if (entity == _destination5Button) {
				destinationChanged = true;
				_destinationSelected = 5;
			}
			else if (entity == _destination6Button) {
				destinationChanged = true;
				_destinationSelected = 6;
			}
			else if (entity == _travelButton) {
				if (_destinationSelected == 1 && !_state.InBadCity) {
					((GDCommonData)GDGlobal.Data).PopupText = "You just came from there!";
					_code = GDScreenCode.TRANSITION_POPUP;
				}
				else {
					// update previous city
					_state.PreviousCityId = _state.CurrentCityId;
					// update current city
					City currentCity = null;
					boolean returning = false;
					if (_destinationSelected == 1) {
						currentCity = _previousCity;
						_state.PreviousCityId = 0;
						returning = true;
					}
					else
						currentCity = _availableCities[_destinationSelected - 2];
					_state.CurrentCityId = currentCity._id;
					
					// Handles case where user was in good city and is traveling to next good city
					if (!_state.InBadCity && _state.CurrentCityId == _state.GoalCityId) {
						// update visited count
						_state.CurrentCityVisitCount++;
						// update visited city
						GDGlobal.DataAccess.setVisitedCity(_state, currentCity);
						// update available cities
						DifficultyType difficulty = GDGlobal.DataAccess.getCurrentDifficulty();
						City[] visitableCities =  GDGlobal.DataAccess.setVisitableCities(_state, 4, difficulty);
						
						GDGlobal.DataAccess.UpdateCaseState(_state);
						
						// set goal city
						 GDGlobal.DataAccess.setRandomGoalCity(_state, visitableCities);
						// get clue locations for this city
						 GDGlobal.DataAccess.setRandomClueLocationsForCurrentCase(3);
					}
					// Handles case where user was in good city and is traveling to bad city
					else if (!_state.InBadCity && _state.CurrentCityId != _state.GoalCityId) {
						_state.InBadCity = true;
						
						GDGlobal.DataAccess.UpdateCaseState(_state);
						
						// set bad clue locations
						GDGlobal.DataAccess.setRandomBadClueLocationsForCurrentCase(3);
					}
					// Handles case where user was in bad city and is returning to good city
					else if (_state.InBadCity && returning) {
						_state.InBadCity = false;
						GDGlobal.DataAccess.UpdateCaseState(_state);
					}
					// Handles case where user was in bad city and is traveling to another bad city (loss)
					else if (_state.InBadCity && !returning) {
						_state.InFailCity = true;
						GDGlobal.DataAccess.UpdateCaseState(_state);
					}
					
					_code = GDScreenCode.TRANSITION_TRANSIT_LOAD;
				}
			}
			
			if (destinationChanged) {
				updateTravelButtonColors(_destinationSelected);
				_travelButton.Attributes.Sprite.setFrame(0);
				_travelButton.enableBehaviors(GDBehaviorType.BUTTON);
			}
			
		}
	}
	
	@Override
	public void onInit() {
		Manager.Message.subscribe(this, MessageType.BUTTON_CLICKED);
		
		_state = GDGlobal.DataAccess.getCurrentCaseState();
		_currentCity = GDGlobal.DataAccess.getCity(_state.CurrentCityId);
		
		// set previous city if player is coming from somewhere else and not returning to past destiantion
		if (_state.PreviousCityId > 0)
			_previousCity = GDGlobal.DataAccess.getCity(_state.PreviousCityId);
		else
			_previousCity = null;
		
		// set random travel destinations if player is in wrong city
		if (_state.InBadCity) {
			DifficultyType difficulty = GDGlobal.DataAccess.getCurrentDifficulty();
			_availableCities = GDGlobal.DataAccess.getUnvisitedCities(4, difficulty);
		}
		else
			_availableCities = GDGlobal.DataAccess.getAvailableCitiesForCurrentCase();
		
		float clockHeight = LayoutHelper.HeightSubFrac(1f, 24f);
		StringBuffer timeString = new StringBuffer();
		timeString.append("Current: ");
		timeString.append(GDTimeHelper.getTimeString(_state.CurrentHour));
		_clock = EntityHelper.text("CALIBRI SMALL",
				timeString.toString(), 
				LayoutHelper.WidthFrac(30), 
				clockHeight, 
				false, 
				false,
				0f, 1f, 1f, 1f, 1f);
		_entities.add(_clock);
		
		StringBuffer deadlineString = new StringBuffer();
		deadlineString.append("Deadline: ");
		deadlineString.append(GDTimeHelper.getTimeString(_state.DeadlineHour));
		_deadline = EntityHelper.text("CALIBRI SMALL",
				deadlineString.toString(), 
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
		_cityLabel = EntityHelper.text("CALIBRI SMALL",
				CityHelper.getLabel(_currentCity), 
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
		
		float location1Height = cityImageHeight - LayoutHelper.WidthSubFrac(2f, 4f) - LayoutHelper.HeightFrac(20f);
		if (_previousCity != null) {
			_destination1Button = EntityHelper.textButton("CALIBRI BIG",
					CityHelper.getLabel(_previousCity), 
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
		_destination2Button = EntityHelper.textButton("CALIBRI BIG",
				CityHelper.getLabel(_availableCities[0]), 
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
		_destination3Button = EntityHelper.textButton("CALIBRI BIG",
				CityHelper.getLabel(_availableCities[1]), 
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
		_destination4Button = EntityHelper.textButton("CALIBRI BIG",
				CityHelper.getLabel(_availableCities[2]), 
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
			_destination5Button = EntityHelper.textButton("CALIBRI BIG",
					CityHelper.getLabel(_availableCities[3]), 
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
			_destination6Button = EntityHelper.textButton("CALIBRI BIG",
					CityHelper.getLabel(_availableCities[4]), 
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
		
		float backButtonHeight = LayoutHelper.HeightFrac(12f);
		_backButton = EntityHelper.button(GDImage.TRAVEL_BACK_BUTTON,
				GDSpriteLayer.UI_HIGH, 
				false, 
				LayoutHelper.WidthFrac(2.5f),
				LayoutHelper.WidthFrac(5f),
				true,
				LayoutHelper.WidthFrac(2f),
				backButtonHeight,
				AreaType.Rectangle);
		_entities.add(_backButton);
		
		float travelButtonHeight = backButtonHeight + LayoutHelper.WidthFrac(5f) + LayoutHelper.HeightFrac(48f);
		_travelButton = EntityHelper.button(GDImage.TRAVEL_FLY_BUTTON,
				GDSpriteLayer.UI_HIGH, 
				false, 
				LayoutHelper.WidthFrac(2.5f),
				LayoutHelper.WidthFrac(5f),
				true,
				LayoutHelper.WidthFrac(2f),
				travelButtonHeight,
				AreaType.Rectangle);
		_travelButton.Attributes.Sprite.setFrame(1);
		_entities.add(_travelButton);
	}
	
	@Override
	protected void enableBehaviors() {
		super.enableBehaviors();
		
		_travelButton.disableBehaviors(GDBehaviorType.BUTTON);
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

	private void updateTravelButtonColors(int selected) {
		if (_destination1Button != null)
			((TextRenderBehavior)_destination1Button.getBehavior(GDBehaviorType.TEXT_RENDER)).setProperties(1f, 1f, 1f, 1f);
		((TextRenderBehavior)_destination2Button.getBehavior(GDBehaviorType.TEXT_RENDER)).setProperties(1f, 1f, 1f, 1f);
		((TextRenderBehavior)_destination3Button.getBehavior(GDBehaviorType.TEXT_RENDER)).setProperties(1f, 1f, 1f, 1f);
		((TextRenderBehavior)_destination4Button.getBehavior(GDBehaviorType.TEXT_RENDER)).setProperties(1f, 1f, 1f, 1f);
		if (_destination5Button != null)
			((TextRenderBehavior)_destination5Button.getBehavior(GDBehaviorType.TEXT_RENDER)).setProperties(1f, 1f, 1f, 1f);
		if (_destination6Button != null)
			((TextRenderBehavior)_destination6Button.getBehavior(GDBehaviorType.TEXT_RENDER)).setProperties(1f, 1f, 1f, 1f);
		
		switch (selected) {
		case 1:
			((TextRenderBehavior)_destination1Button.getBehavior(GDBehaviorType.TEXT_RENDER)).setProperties(1f, 0f, 1f, 1f);
			break;
		case 2:
			((TextRenderBehavior)_destination2Button.getBehavior(GDBehaviorType.TEXT_RENDER)).setProperties(1f, 0f, 1f, 1f);
			break;
		case 3:
			((TextRenderBehavior)_destination3Button.getBehavior(GDBehaviorType.TEXT_RENDER)).setProperties(1f, 0f, 1f, 1f);
			break;
		case 4:
			((TextRenderBehavior)_destination4Button.getBehavior(GDBehaviorType.TEXT_RENDER)).setProperties(1f, 0f, 1f, 1f);
			break;
		case 5:
			((TextRenderBehavior)_destination5Button.getBehavior(GDBehaviorType.TEXT_RENDER)).setProperties(1f, 0f, 1f, 1f);
			break;
		case 6:
			((TextRenderBehavior)_destination6Button.getBehavior(GDBehaviorType.TEXT_RENDER)).setProperties(1f, 0f, 1f, 1f);
			break;
		}
	}
}
