package com.game.geodetective.screen;

import com.game.geodetective.entity.EntityHelper;
import com.game.geodetective.graphics.GDImage;
import com.game.geodetective.graphics.GDSpriteLayer;
import com.game.geodetective.utility.GDCommonData;
import com.game.geodetective.utility.GDGlobal;
import com.game.loblib.entity.GameEntity;
import com.game.loblib.messaging.Message;
import com.game.loblib.messaging.MessageType;
import com.game.loblib.screen.Screen;
import com.game.loblib.utility.LayoutHelper;
import com.game.loblib.utility.Manager;
import com.game.loblib.utility.area.AreaType;

public class PopupScreen extends Screen {
	
	private GameEntity _background;
	private GameEntity _button;
	private GameEntity _text;
	
	public PopupScreen() {
		_type = GDScreenType.POPUP;
	}
	
	@Override
	public void onHandleMessage(Message message) {
		if (message.Type == MessageType.BUTTON_CLICKED) {
			GameEntity entity = message.getData();
			if (entity == _button) {
				_code = GDScreenCode.POP;
			}
		}
	}
	
	@Override
	protected void onInit() {
		Manager.Message.subscribe(this, MessageType.BUTTON_CLICKED);
		
		_background = EntityHelper.graphic(GDImage.POPUP_BACKGROUND, GDSpriteLayer.UI_LOW, false, LayoutHelper.WidthMul(.8f), LayoutHelper.WidthMul(.8f), true, LayoutHelper.WidthFrac(2f), LayoutHelper.HeightFrac(2f));
		_entities.add(_background);
		
		_button = EntityHelper.button(GDImage.CONTINUE_BUTTON, GDSpriteLayer.UI_HIGH, false, LayoutHelper.WidthFrac(2.5f), LayoutHelper.WidthFrac(5f), true, LayoutHelper.WidthFrac(2f), LayoutHelper.HeightSubFrac(2f, 8f), AreaType.Rectangle);
		_entities.add(_button);
		
		_text = EntityHelper.text("CALIBRI BIG", ((GDCommonData)GDGlobal.Data).PopupText, LayoutHelper.WidthMul(.1f), LayoutHelper.HeightAddFrac(2f, 4f), false, false, LayoutHelper.WidthMul(.7f), 1f, 1f, 1f, 1f);
		_entities.add(_text);
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
