package com.game.geodetective.behavior;

import com.game.loblib.behavior.Behavior;
import com.game.loblib.utility.Logger;
import com.game.loblib.utility.area.Vertex;

public class CircleMovementBehavior extends Behavior {
	
	protected float _initAnchorX;
	protected float _initAnchorY;
	protected Vertex _anchor = new Vertex();
	protected float _radius;
	protected float _timeToSpin;
	protected float _angleOffset;
	protected boolean _clockwise;
	protected boolean _anchorReference;
	
	protected float _currentAngle;

	public CircleMovementBehavior(float anchorX, float anchorY, float radius, float timeToSpin, boolean clockwise) {
		_type = GeoDetectiveBehaviorType.CIRCLE_MOVE;
		_initAnchorX = anchorX;
		_initAnchorY = anchorY;
		_radius = radius;
		_timeToSpin = timeToSpin;
		_clockwise = clockwise;
		_angleOffset = 0f;
		_anchorReference = false;
	}
	
	public CircleMovementBehavior(float anchorX, float anchorY, float radius, float timeToSpin, boolean clockwise, float angleOffset) {
		_type = GeoDetectiveBehaviorType.CIRCLE_MOVE;
		_initAnchorX = anchorX;
		_initAnchorY = anchorY;
		_radius = radius;
		_timeToSpin = timeToSpin;
		_clockwise = clockwise;
		_angleOffset = angleOffset;
		_anchorReference = false;
	}
	
	public CircleMovementBehavior(Vertex anchor, float radius, float timeToSpin, boolean clockwise, float angleOffset) {
		_type = GeoDetectiveBehaviorType.CIRCLE_MOVE;
		_initAnchorX = 0f;
		_initAnchorY = 0f;
		_anchor = anchor;
		_radius = radius;
		_timeToSpin = timeToSpin;
		_clockwise = clockwise;
		_angleOffset = angleOffset;
		_anchorReference = true;
	}
	
	
	public void setAnchor(float x, float y) {
		_anchor.X = x;
		_anchor.Y = y;
		
		if (_anchorReference)
			Logger.w(_tag, "Anchor is a reference. It's value should not be changed here");
	}
	
	public Vertex getAnchor(){
		return _anchor;
	}
	
	@Override
	public void init() {
		_currentAngle = _angleOffset;
		if (!_anchorReference)
			setAnchor(_initAnchorX, _initAnchorY);
	}
	
	@Override
	protected void onUpdate(float updateRatio) {
		float elapsedTime = updateRatio * 50f / 3f;
		if (_clockwise) {
			_currentAngle -= (elapsedTime / _timeToSpin) * Math.PI * 2f;
			if (_currentAngle < 0)
				_currentAngle += (Math.PI * 2f);
		}
		else {
			_currentAngle += (elapsedTime / _timeToSpin) * Math.PI * 2f;
			if (_currentAngle > Math.PI * 2f)
				_currentAngle -= (Math.PI * 2f);
		}
		_entity.Attributes.Area.setCenter(_anchor.X + _radius * (float)Math.cos(_currentAngle),
				_anchor.Y + _radius * (float)Math.sin(_currentAngle));
	}
	
	@Override
	protected void setTag() {
		_tag.setLength(0);
		_tag.append(_entity.getTag());
		_tag.append(": CircleMovementBehavior");
	}

}
