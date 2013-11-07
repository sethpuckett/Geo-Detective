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
	
	public CityScreen() {
		_type = GDScreenType.CITY;
	}
	
	@Override
	public void update(float updateRatio) {

	}
	
	@Override
	public void onInit() {
			CaseState state = GDGlobal.DataAccess.getCurrentCaseState();
			City currentCity = GDGlobal.DataAccess.getCity(state.CurrentCityId);
			ClueLocation[] clueLocations = GDGlobal.DataAccess.getRandomClueLocations(3);
			
			GDGlobal.Renderer.setTextProperties(1f, 1f, 1f, 1f);
			
			float clockHeight = LayoutHelper.HeightSubFrac(1f, 24f);
			StringBuffer timeString = new StringBuffer();
			timeString.append("Current: ");
			timeString.append(GDTimeHelper.getTimeString(state.CurrentHour));
			_clock = EntityHelper.text(timeString.toString(), 
					LayoutHelper.WidthFrac(30), 
					clockHeight, 
					false, 
					false);
			_entities.add(_clock);
			
			StringBuffer deadlineString = new StringBuffer();
			deadlineString.append("Deadline: ");
			deadlineString.append(GDTimeHelper.getTimeString(state.DeadlineHour));
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
			
			float cityLabelHeight = borderHeight - LayoutHelper.HeightFrac(20f);
			_cityLabel = EntityHelper.text(CityHelper.getLabel(currentCity), 
					LayoutHelper.WidthFrac(2f), 
					cityLabelHeight, 
					true, 
					false);
			_entities.add(_cityLabel);
			
			float cityImageHeight = cityLabelHeight - LayoutHelper.WidthFrac(4f);
			_cityImage = EntityHelper.graphic(CityHelper.getCityImage(currentCity),
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
			_clueLocation1Button = EntityHelper.button(CityHelper.GetClueLocationImage(clueLocations[0]), 
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
			_clueLocation2Button = EntityHelper.button(CityHelper.GetClueLocationImage(clueLocations[1]), 
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
			_clueLocation3Button = EntityHelper.button(CityHelper.GetClueLocationImage(clueLocations[2]), 
					GDSpriteLayer.UI_HIGH, 
					false, 
					LayoutHelper.WidthSubFrac(1f, 4f), 
					LayoutHelper.WidthSubFrac(8f, 32f),
					true, 
					LayoutHelper.WidthSubFrac(2f, 12f), 
					location3Height, 
					AreaType.Rectangle);
			_entities.add(_clueLocation3Button);
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
