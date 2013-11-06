package com.game.geodetective.behavior;

import com.game.loblib.input.GameMotionEvent;
import com.game.loblib.input.MotionType;
import com.game.loblib.utility.Global;
import com.game.loblib.utility.area.Area;
import com.game.loblib.utility.area.Vertex;


public class ReverseTouchDestinationBehavior extends TouchDestinationBehavior{

	protected Vertex _focus;
	
	public ReverseTouchDestinationBehavior(Vertex focus) {
		_type = GDBehaviorType.REVERSE_TOUCH_DESTINATION;
		
		_focus = focus;
	}

	@Override
	protected void setTag() {
		_tag.setLength(0);
		_tag.append(_entity.getTag());
		_tag.append(": TouchDestinationBehavior");
	}
	
	@Override
	public void onTouchEvent(GameMotionEvent event) {
		switch (event.Type) {
		case MotionType.ACTION_DOWN:
			_screenTouch = true;
		case MotionType.ACTION_MOVE:
			if (_screenTouch) {
				_entity.Attributes.Destination.Undefined = false;
				_entity.Attributes.Destination.X = _focus.X + (_focus.X - event.CameraCoords.X);
				_entity.Attributes.Destination.Y = _focus.Y + (_focus.Y - event.CameraCoords.Y);
				Area.sync(_cameraLocation, Global.Camera.CameraArea.Position);
			}
			break;
		case MotionType.ACTION_UP:
		case MotionType.ACTION_POINTER_UP:
			_entity.Attributes.Destination.Undefined = true;
			_screenTouch = false;
			break;
		}
	}
}
