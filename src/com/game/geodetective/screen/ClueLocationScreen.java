package com.game.geodetective.screen;

import com.game.geodetective.data.entity.CaseState;
import com.game.geodetective.data.entity.Clue;
import com.game.geodetective.data.entity.ClueLocation;
import com.game.geodetective.data.entity.GenderType;
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
import com.game.loblib.utility.Logger;
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
	private Clue _clue;
	
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
		if (!_state.InBadCity && !_state.InFailCity)
			_clue = GDGlobal.DataAccess.getClueForCurrentState();
		else if (_state.InBadCity && !_state.InFailCity) {
			Clue badClue = new Clue();
			badClue.ClueText = "No, I haven't seen anybody like that.";
			_clue = badClue;
		}
		else if (_state.InFailCity) {
			// TODO: Show fail screen and end game
		}
		
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
				0f,
				1f,
				1f,
				1f,
				1f);
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
				0f,
				1f,
				1f,
				1f,
				1f);
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
		_locationLabel = EntityHelper.text("CALIBRI SMALL",
				_clueLocation.Name, 
				LayoutHelper.WidthFrac(2f), 
				locationLabelHeight, 
				true, 
				false,
				0f,
				1f,
				1f,
				1f,
				1f);
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
		_clueBox = EntityHelper.text("CALIBRI SMALL",
				GetRealClueText(_clue.ClueText), 
				LayoutHelper.WidthFrac(2f), 
				clueHeight, 
				true, 
				false,
				LayoutHelper.WidthSubFrac(1f, 6f),
				1f,
				1f,
				1f,
				1f);
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
				LayoutHelper.WidthFrac(2.5f),
				LayoutHelper.WidthFrac(5f),
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
	
	private String GetRealClueText(String clueText) {
		GenderType gender = GDGlobal.DataAccess.getVillainGenderForCurrentState();
		String realClueText = clueText;
		
		if (gender.Code.equals("MALE"))
			realClueText = clueText.replaceAll("\\[procap\\]", "He").replaceAll("\\[prolow\\]", "he").replaceAll("\\[poscap\\]", "His").replaceAll("\\[poslow\\]", "his");
		else if (gender.Code.equals("FEMALE"))
			realClueText = clueText.replaceAll("\\[procap\\]", "She").replaceAll("\\[prolow\\]", "she").replaceAll("\\[poscap\\]", "Her").replaceAll("\\[poslow\\]", "her");
		else
			Logger.e(_tag, "Invalid Gender Type");
		
		return realClueText;
	}
}
