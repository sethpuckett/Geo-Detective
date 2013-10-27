package com.game.geodetective.behavior;

import com.game.phase.input.GameMotionEvent;
import com.game.phase.input.ITouchListener;
import com.game.phase.input.MotionType;
import com.game.phase.input.TouchData;
import com.game.phase.messaging.IMessageHandler;
import com.game.phase.messaging.Message;
import com.game.phase.messaging.MessageType;
import com.game.phase.utility.Global;
import com.game.phase.utility.Manager;
import com.game.phase.utility.area.Area;
import com.game.phase.utility.area.AreaType;
import com.game.phase.utility.area.Rectangle;
import com.game.phase.utility.area.Vertex;


public class TouchDestinationBehavior extends Behavior implements ITouchListener, IMessageHandler {
	protected boolean _screenTouch = false;
	protected boolean _destinationReset = false;
	protected final TouchData _touchData = new TouchData(this, AreaType.Rectangle, false, false, true, MotionType.ACTION_DOWN | MotionType.ACTION_UP | MotionType.ACTION_MOVE | MotionType.ACTION_POINTER_UP);
	protected final Vertex _center = new Vertex();
	protected final Vertex _cameraLocation = new Vertex();
	
	public TouchDestinationBehavior() {
		_type = BehaviorType.TOUCH_DESTINATION;
	}
	
	@Override
	public void onUpdate(float updateRatio) {
		if (_screenTouch && _cameraLocation != Global.Camera.CameraArea.Position) {
			_entity.Attributes.Destination.Undefined = false;
			_entity.Attributes.Destination.X += Global.Camera.CameraArea.Position.X - _cameraLocation.X;
			_entity.Attributes.Destination.Y += Global.Camera.CameraArea.Position.Y - _cameraLocation.Y;
			Area.sync(_cameraLocation, Global.Camera.CameraArea.Position);
		}
	}

	@Override
	protected void onEnable() {
		if (Global.Renderer.isSurfaceCreated())
			_touchData.TouchArea.setSize(Global.Renderer.Width, Global.Renderer.Height);
		
		Manager.Input.subscribe(_touchData);
		Manager.Message.subscribe(this, MessageType.SCREEN_SIZE_SET);
	}

	@Override
	protected void onDisable() {
		Manager.Message.unsubscribe(this, MessageType.ALL);
		Manager.Input.unsubscribe(_touchData);
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
			if (event.ScreenCoords.Y > Manager.Level.getControlBarHeight())
				_screenTouch = true;
			else
				break;
		case MotionType.ACTION_MOVE:
			if (_screenTouch || event.ScreenCoords.Y > Manager.Level.getControlBarHeight()) {
				_screenTouch = true;
				_entity.Attributes.Destination.Undefined = false;
				_entity.Attributes.Destination.X = event.CameraCoords.X;
				_entity.Attributes.Destination.Y = event.CameraCoords.Y - Manager.Level.getControlBarHeight();
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
	
	@Override
	public void onTouchAbort(GameMotionEvent event) {
		// Nothing to do
	}
	
	@Override
	public void handleMessage(Message message) {
		if (message.Type == MessageType.SCREEN_SIZE_SET)
			Area.sync(((Rectangle)_touchData.TouchArea).Size, (Vertex)message.getData());
	}
}
