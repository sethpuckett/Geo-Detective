package com.game.geodetective.behavior;

import com.game.phase.exception.UndefinedVertexException;
import com.game.phase.input.GameMotionEvent;
import com.game.phase.input.ITouchListener;
import com.game.phase.input.MotionType;
import com.game.phase.input.TouchData;
import com.game.phase.messaging.IMessageHandler;
import com.game.phase.messaging.Message;
import com.game.phase.messaging.MessageType;
import com.game.phase.utility.Global;
import com.game.phase.utility.Logger;
import com.game.phase.utility.Manager;
import com.game.phase.utility.area.Area;
import com.game.phase.utility.area.AreaType;
import com.game.phase.utility.area.Rectangle;
import com.game.phase.utility.area.Vertex;

public class ScreenDragBehavior extends Behavior implements ITouchListener, IMessageHandler {
	private boolean _screenTouch = false;
	protected final TouchData _touchData = new TouchData(this, AreaType.Rectangle, false, false, true, MotionType.ACTION_DOWN | MotionType.ACTION_MOVE | MotionType.ACTION_UP);
	protected Vertex _newLocation = new Vertex();
	protected Vertex _previousLocation = new Vertex();
	protected Vertex _startLocation = new Vertex();
	protected float _dragThreshold;
	
	protected boolean _dragging;
	
	public ScreenDragBehavior() {
		_type = BehaviorType.SCREEN_DRAG;
		_dragThreshold = 0f;
	}
	
	public ScreenDragBehavior(float threshold) {
		_type = BehaviorType.SCREEN_DRAG;
		_dragThreshold = threshold;
	}
	
	public float getLastXDragSpeed() {
		return _newLocation.X - _previousLocation.X;
	}
	
	public float getLastYDragSpeed() {
		return _newLocation.Y - _previousLocation.Y;
	}
	
	public float getLastDragSpeed() {
		float speed = 0;
		try {
			speed = Vertex.distance(_previousLocation, _newLocation);
		}
		catch (UndefinedVertexException e) {
			Logger.e(_tag, "Undefined vertex");
		}
		return speed;
	}
	
	public float getXDragDistance() {
		float dist = 0f;
		if (_dragging)
			dist = _newLocation.X - _startLocation.X;
		return dist;
	}
	
	public float getYDragDistance() {
		float dist = 0f;
		if (_dragging)
			dist = _newLocation.Y - _startLocation.Y;
		return dist;
	}
	
	public float getDragDistance() {
		float dist = 0f;
		if (_dragging) {
			try {
				dist = Vertex.distance(_newLocation, _startLocation);
			}
			catch (UndefinedVertexException e) {
				Logger.e(_tag, "Undefined vertex");
			}
		}
		return dist;
	}
	
	public void getStartLocation(Vertex start) {
		Area.sync(start, _startLocation);
	}
	
	public void getCurrentLocation(Vertex prev) {
		Area.sync(prev, _newLocation);
	}
	
	@Override
	protected void onEnable() {
		if (Global.Renderer.isSurfaceCreated())
			_touchData.TouchArea.setSize(Global.Renderer.Width, Global.Renderer.Height);
		
		_dragging = false;
		
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
		_tag.append(": ScreenDragBehavior");
	}

	@Override
	public void onTouchEvent(GameMotionEvent event) {
		switch (event.Type) {
		case MotionType.ACTION_DOWN:
			_screenTouch = true;
			Area.sync(_newLocation, event.ScreenCoords);
			Area.sync(_startLocation, event.ScreenCoords);
			break;
		case MotionType.ACTION_MOVE:
			if (_enabled && _screenTouch) {
				try {
					if (!_dragging && Vertex.distanceSquared(event.ScreenCoords, _startLocation) >= Math.pow(_dragThreshold, 2f)) {
						_dragging = true;
						Manager.Message.sendMessage(MessageType.DRAG_START, _entity);
					}
				}
				catch (UndefinedVertexException e) {
					Logger.e(_tag, "undefined vertex");
				}
				Area.sync(_previousLocation, _newLocation);
				Area.sync(_newLocation, event.ScreenCoords);
			}
			break;
		case MotionType.ACTION_UP:
			if (_dragging) {
				_dragging = false;
				Manager.Message.sendMessage(MessageType.DRAG_STOP, _entity);
			}
			_screenTouch = false;
			break;
		}
	}

	@Override
	public void onTouchAbort(GameMotionEvent event) {
		if (_dragging) {
			_dragging = false;
			Manager.Message.sendMessage(MessageType.DRAG_STOP, _entity);
		}
	}

	@Override
	public void handleMessage(Message message) {
		if (message.Type == MessageType.SCREEN_SIZE_SET) {
			Vertex newSize = message.getData();
			Area.sync(((Rectangle)_touchData.TouchArea).Size, newSize);
		}
	}

}
