package com.game.geodetective.behavior;

import com.game.loblib.behavior.Behavior;
import com.game.loblib.input.GameMotionEvent;
import com.game.loblib.input.ITouchListener;
import com.game.loblib.input.MotionType;
import com.game.loblib.input.TouchData;
import com.game.loblib.messaging.IMessageHandler;
import com.game.loblib.messaging.Message;
import com.game.loblib.messaging.MessageType;
import com.game.loblib.utility.Global;
import com.game.loblib.utility.Manager;
import com.game.loblib.utility.area.Area;
import com.game.loblib.utility.area.AreaType;
import com.game.loblib.utility.area.Rectangle;
import com.game.loblib.utility.area.Vertex;


public class TouchDestinationBehavior extends Behavior implements ITouchListener, IMessageHandler {
	protected boolean _screenTouch = false;
	protected boolean _destinationReset = false;
	protected final TouchData _touchData = new TouchData(this, AreaType.Rectangle, false, false, true, MotionType.ACTION_DOWN | MotionType.ACTION_UP | MotionType.ACTION_MOVE | MotionType.ACTION_POINTER_UP);
	protected final Vertex _center = new Vertex();
	protected final Vertex _cameraLocation = new Vertex();
	
	public TouchDestinationBehavior() {
		_type = GDBehaviorType.TOUCH_DESTINATION;
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
			_screenTouch = true;
		case MotionType.ACTION_MOVE:
			if (_screenTouch) {
				_entity.Attributes.Destination.Undefined = false;
				_entity.Attributes.Destination.X = event.CameraCoords.X;
				_entity.Attributes.Destination.Y = event.CameraCoords.Y;
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
