package com.game.geodetective.behavior;

import com.game.geodetective.messaging.GeoDetectiveMessageType;
import com.game.loblib.behavior.Behavior;
import com.game.loblib.utility.Logger;
import com.game.loblib.utility.Manager;
import com.game.loblib.utility.android.FixedSizeArray;
import com.game.loblib.utility.area.Area;
import com.game.loblib.utility.area.Vertex;

public class PatrolDestinationBehavior extends Behavior {
	
	protected final int MAX_DESTINATIONS = 16;
	
	public class PatrolType {
		public static final int UNKNOWN = 0;
		public static final int CONTINUOUS = 1;
		public static final int ONCE_THROUGH = 2;
		public static final int SINGLE = 3;		
	}

	protected FixedSizeArray<Vertex> _destinations = new FixedSizeArray<Vertex>(MAX_DESTINATIONS);
	protected int _currentDestinationIndex = 0;
	protected Vertex _center = new Vertex();
	protected int _holdTime = 0;
	protected int _startOffset = 0;
	protected float _remainingTime = 0;
	protected boolean _holding;
	protected int _patrolType = PatrolType.CONTINUOUS;
	
	
	public PatrolDestinationBehavior() {
		_type = GeoDetectiveBehaviorType.PATROL_DESTINATION;
	}
	
	public PatrolDestinationBehavior(int holdTime) {
		_type = GeoDetectiveBehaviorType.PATROL_DESTINATION;
		_holdTime = holdTime;
	}
	
	public PatrolDestinationBehavior(int holdTime, int startOffset) {
		_type = GeoDetectiveBehaviorType.PATROL_DESTINATION;
		_holdTime = holdTime;
		_startOffset = startOffset;
	}
	
	public PatrolDestinationBehavior(int holdTime, int startOffset, int patrolType) {
		_type = GeoDetectiveBehaviorType.PATROL_DESTINATION;
		_holdTime = holdTime;
		_startOffset = startOffset;
		_patrolType = patrolType;
	}
	
	public void setPauseTime(int holdTime) {
		_holdTime = holdTime;
	}

	public void setStartOffset(int startOffset) {
		_startOffset = startOffset;
	}
	
	public void setPatrolType(int patrolType) {
		_patrolType = patrolType;
	}
	
	public void addDestination(float x, float y) {
		if (_destinations.getCount() < MAX_DESTINATIONS)
			_destinations.add(new Vertex(x, y));
		else
			Logger.e(_tag, "cannot add destination; max exceeded");
	}
	
	public void setDestinationIndex(int index) {
		if (index < _destinations.getCount()) {
			updateDestination(index);
		}
		else
			Logger.e(_tag, "Unable to set index; index out of bounds");
	}
	
	@Override
	public void init() {
		_currentDestinationIndex = 0;
		_remainingTime = Math.max(_holdTime - _startOffset, 0);
		_holding = false;
	}
	
	@Override
	public void onEnable() {
		if (_destinations.getCount() < 2)
			Logger.e(_tag, "PatrolDestinationBehavior requires at least 2 destinations");
		if (_entity.Attributes.Area.Position.equals(_destinations.get(_currentDestinationIndex)) && !_holding) {
			_currentDestinationIndex++;
			if (_currentDestinationIndex >= _destinations.getCount())
				_currentDestinationIndex = 0;
		}
		if (_entity.Attributes.Destination != _destinations.get(_currentDestinationIndex)) {
			Manager.Message.sendMessage(GeoDetectiveMessageType.PATROL_START, _entity);
			Area.sync(_entity.Attributes.Destination, _destinations.get(_currentDestinationIndex));
		}
	}
	
	@Override
	protected void onUpdate(float updateRatio) {
		if (_holding) {
			float elapsedTime = updateRatio * 50f / 3f;	
			_remainingTime -= elapsedTime;
			if (_remainingTime <= 0) {
				updateDestination();
			}
		}
		else {
			_entity.Attributes.Area.getCenter(_center);
			if (_center.equals(_destinations.get(_currentDestinationIndex))) {
				//Manager.Message.sendMessage(MessageType.DESTINATION_REACHED, _entity);
				if (_patrolType == PatrolType.SINGLE ||
						(_patrolType == PatrolType.ONCE_THROUGH && 
						_currentDestinationIndex == _destinations.getCount() - 1))
					disable();
				else if (_holdTime > 0)
					_holding = true;
				else
					updateDestination();
			}
		}
	}
	
	protected void updateDestination() {
		updateDestination(_currentDestinationIndex + 1);
	}
	
	protected void updateDestination(int newIndex) {
		Manager.Message.sendMessage(GeoDetectiveMessageType.PATROL_START, _entity);
		_currentDestinationIndex = newIndex;
		if (_currentDestinationIndex >= _destinations.getCount())
			_currentDestinationIndex = 0;
		Area.sync(_entity.Attributes.Destination, _destinations.get(_currentDestinationIndex));
		_holding = false;
		_remainingTime = _holdTime;
	}
	
	@Override
	protected void setTag() {
		_tag.setLength(0);
		_tag.append(_entity.getTag());
		_tag.append(": PatrolDestinationBehavior");
	}

}
