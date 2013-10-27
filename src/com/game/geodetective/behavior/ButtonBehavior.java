package com.game.geodetective.behavior;

import com.game.loblib.behavior.Behavior;
import com.game.loblib.input.GameMotionEvent;
import com.game.loblib.input.ITouchListener;
import com.game.loblib.input.MotionType;
import com.game.loblib.input.TouchData;
import com.game.loblib.messaging.MessageType;
import com.game.loblib.utility.Manager;
import com.game.loblib.utility.area.Area;
import com.game.loblib.utility.area.AreaType;

public class ButtonBehavior extends Behavior implements ITouchListener {

	protected final TouchData _touchArea;
	protected boolean _useCamera;
	protected boolean _clickOnRelase;
	protected boolean _buttonDown;
	protected int _buttonDownPointerId;
	
	public ButtonBehavior(AreaType type, boolean useCamera) {
		_type = GeoDetectiveBehaviorType.BUTTON;
		_useCamera = useCamera;
		_clickOnRelase = false;
		_touchArea = new TouchData(this, type, _useCamera, true, false, MotionType.ACTION_DOWN | MotionType.ACTION_POINTER_DOWN);
	}
	
	public ButtonBehavior(AreaType type, boolean useCamera, boolean clickOnRelease) {
		_type = GeoDetectiveBehaviorType.BUTTON;
		_useCamera = useCamera;
		_clickOnRelase = clickOnRelease;
		int motionTypes = MotionType.ACTION_DOWN | MotionType.ACTION_POINTER_DOWN;
		if (clickOnRelease)
			motionTypes = motionTypes | MotionType.ACTION_UP | MotionType.ACTION_POINTER_UP | MotionType.ACTION_CANCEL;
		_touchArea = new TouchData(this, type, _useCamera, true, false, motionTypes);
	}
	
	@Override
	protected void onUpdate(float updateRatio) {
		syncArea();
	}

	@Override
	protected void onEnable() {
		syncArea();
		Manager.Input.subscribe(_touchArea);
	}

	@Override
	protected void onDisable() {
		Manager.Input.unsubscribe(_touchArea);
	}
	
	@Override
	protected void onDestroy() {
		if (_enabled)
			Manager.Input.unsubscribe(_touchArea);
	}

	@Override
	protected void setTag() {
		_tag.setLength(0);
		_tag.append(_entity.getTag());
		_tag.append(": ButtonBehavior");
	}

	@Override
	public void onTouchEvent(GameMotionEvent event) {
		if (!_clickOnRelase)
			Manager.Message.sendMessage(MessageType.BUTTON_CLICKED, _entity);
		else {
			
			if (event.Type == MotionType.ACTION_DOWN || event.Type == MotionType.ACTION_POINTER_DOWN) {
				_buttonDown = true;
				_buttonDownPointerId = event.PointerId;
				Manager.Message.sendMessage(MessageType.BUTTON_DOWN, _entity);
			}
			else if (_buttonDown && _buttonDownPointerId == event.PointerId) {
				_buttonDown = false;
				Manager.Message.sendMessage(MessageType.BUTTON_CLICKED, _entity);
			}
		}
	}
	
	@Override
	public void onTouchAbort(GameMotionEvent event) {
		if (_buttonDown && _buttonDownPointerId == event.PointerId) {
			_buttonDown = false;
			Manager.Message.sendMessage(MessageType.BUTTON_UP, _entity);
		}
	}

	protected void syncArea() {
		Area.sync(_touchArea.TouchArea, _entity.Attributes.Area);
	}
}
