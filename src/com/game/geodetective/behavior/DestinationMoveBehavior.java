package com.game.geodetective.behavior;

import com.game.loblib.behavior.Behavior;
import com.game.loblib.behavior.BehaviorType;
import com.game.loblib.exception.UndefinedVertexException;
import com.game.loblib.messaging.MessageType;
import com.game.loblib.utility.Logger;
import com.game.loblib.utility.Manager;
import com.game.loblib.utility.area.Area;
import com.game.loblib.utility.area.Vertex;

public class DestinationMoveBehavior extends Behavior {

	protected boolean _useCenter;
	
	public DestinationMoveBehavior() {
		_type = GeoDetectiveBehaviorType.DESTINATION_MOVE;
		_useCenter = true;
	}
	
	public DestinationMoveBehavior(boolean useCenter) {
		_type = GeoDetectiveBehaviorType.DESTINATION_MOVE;
		_useCenter = useCenter;
	}
	
	@Override
	protected void onUpdate(float updateRatio) {
		try {
			// allocate and set variables
			Vertex direction = Manager.Vertex.allocate();
			Vertex destination = Manager.Vertex.allocate();
			float speed;
			Vertex position = Manager.Vertex.allocate();
			Vertex velocity = Manager.Vertex.allocate();
			Area.sync(direction, _entity.Attributes.Direction);
			Area.sync(destination, _entity.Attributes.Destination);
			speed = _entity.Attributes.Speed;
			if (_useCenter)
				_entity.Attributes.Area.getCenter(position);
			else
				Area.sync(position, _entity.Attributes.Area.Position);
			
			// update
			updatePosition(position, direction, speed, destination, updateRatio);
			Vertex.normalize(position, destination, direction);
			
			// set entity attributes
			if (_useCenter)
				_entity.Attributes.Area.setCenter(position);
			else
				Area.sync(_entity.Attributes.Area.Position, position);
			Area.sync(_entity.Attributes.Direction, direction);
			
			// release allocated objects
			Manager.Vertex.release(direction);
			Manager.Vertex.release(destination);
			Manager.Vertex.release(position);
			Manager.Vertex.release(velocity);
		}
		catch (UndefinedVertexException e) {
			Logger.e(_tag, "Undefined Vertex", e);
		}
	}
	
	protected void updatePosition(Vertex position, Vertex direction, float speed, Vertex destination, float  multiplier) throws UndefinedVertexException {
 		// do nothing if resting on destination
		if (destination.equals(position)&& direction.Undefined)
			return;
		if (!direction.Undefined) {
			// allocate variables
			Vertex potentialPosition = Manager.Vertex.allocate();
			Vertex totalMovement = Manager.Vertex.allocate();
			Area.sync(potentialPosition, position);
			
			setPotentialPosition(position, direction, speed, destination, multiplier, totalMovement, potentialPosition);
			Area.sync(position, potentialPosition);
			
			if (position.equals(destination))
				Manager.Message.sendMessage(MessageType.DESTINATION_REACHED, _entity);
			
			// free allocations
			Manager.Vertex.release(potentialPosition);
			Manager.Vertex.release(totalMovement);
		}
	}
	
	protected void setPotentialPosition(Vertex position, Vertex direction, float speed, Vertex destination, float multiplier, Vertex totalMovement, Vertex potentialPosition) throws UndefinedVertexException {
		if (!destination.Undefined && Vertex.distanceSquared(position, destination) < (_entity.Attributes.Speed * 5f)) {
			Vertex.sub(destination, position, totalMovement);		// get total movement
			Area.sync(potentialPosition, destination);			// set potential center
		}
		else {
			Vertex.mul(direction, speed, totalMovement); 		// get raw movement
			Vertex.mul(totalMovement, multiplier);				// adjust w/ multiplier
			Vertex.add(position, totalMovement, potentialPosition);	// set potential center
		}
	}
	
	@Override
	protected void setTag() {
		_tag.setLength(0);
		_tag.append(_entity.getTag());
		_tag.append(": DestinationMoveBehavior");
	}

}
