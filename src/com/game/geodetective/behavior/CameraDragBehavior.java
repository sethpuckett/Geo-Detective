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

public class CameraDragBehavior extends Behavior implements ITouchListener, IMessageHandler {
	protected final TouchData _touchData = new TouchData(this, AreaType.Rectangle, false, false, true, MotionType.ACTION_DOWN | MotionType.ACTION_MOVE | MotionType.ACTION_UP);
	protected Vertex _previousLocation = new Vertex();
	
	public CameraDragBehavior() {
		_type = GeoDetectiveBehaviorType.CAMERA_DRAG;
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
		_tag.append(": DragMoveBehavior");
	}

	@Override
	public void onTouchEvent(GameMotionEvent event) {		
		switch (event.Type) {
		case MotionType.ACTION_DOWN:
			Area.sync(_previousLocation, event.ScreenCoords);
		case MotionType.ACTION_MOVE:
			// set dest to touch loc; remember loc if disabled
			if (_enabled) {
				float xChange = event.ScreenCoords.X - _previousLocation.X;
				float yChange = event.ScreenCoords.Y - _previousLocation.Y;
				_entity.Attributes.Area.changePosition(-xChange, -yChange);
				Area.sync(_previousLocation, event.ScreenCoords);
			}
			break;
		case MotionType.ACTION_UP:
			break;
		}
	}
	
	@Override
	public void onTouchAbort(GameMotionEvent event) {
		// Nothing to do
	}
	
	@Override
	public void handleMessage(Message message) {
		if (message.Type == MessageType.SCREEN_SIZE_SET) {
			Vertex newSize = message.getData();
			Area.sync(((Rectangle)_touchData.TouchArea).Size, newSize);
		}
	}
}
