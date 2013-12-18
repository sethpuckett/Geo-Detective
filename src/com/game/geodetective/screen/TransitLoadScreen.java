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
import com.game.geodetective.utility.GDGlobal;
import com.game.geodetective.utility.GDTimeHelper;
import com.game.loblib.entity.GameEntity;
import com.game.loblib.messaging.Message;
import com.game.loblib.messaging.MessageType;
import com.game.loblib.screen.Screen;
import com.game.loblib.utility.LayoutHelper;
import com.game.loblib.utility.Manager;
import com.game.loblib.utility.area.AreaType;


public class TransitLoadScreen extends Screen {
	private GameEntity _backgroundImage;
	private GameEntity _clock;
	private GameEntity _deadline;
	private GameEntity _clockBorder;
	
	private CaseState _state;
	
	public TransitLoadScreen() {
		_type = GDScreenType.TRANSIT_LOAD;
	}

	@Override
	public void onInit() {
		Manager.Message.subscribe(this, MessageType.BUTTON_CLICKED);
		
		_state = GDGlobal.DataAccess.getCurrentCaseState();
		
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
		
		// TODO: temporary
		_code = GDScreenCode.TRANSITION_CITY;
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
