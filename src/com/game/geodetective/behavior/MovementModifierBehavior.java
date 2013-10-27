package com.game.geodetective.behavior;

import com.game.phase.collision.CollisionLayer;
import com.game.phase.collision.ICollisionSender;
import com.game.phase.exception.UndefinedVertexException;
import com.game.phase.utility.Direction;
import com.game.phase.utility.Logger;
import com.game.phase.utility.Manager;
import com.game.phase.utility.area.Area;
import com.game.phase.utility.area.IArea;
import com.game.phase.utility.area.Vertex;

public class MovementModifierBehavior extends Behavior {

	protected long _collisionLayers = CollisionLayer.MAIN_BLOCK;
	protected int _direction = Direction.UNKNOWN;
	protected float _power = 0;
	
	public MovementModifierBehavior() {
		_type = BehaviorType.MOVEMENT_MODIFIER;
	}
	
	public MovementModifierBehavior(int direction, float power) {
		_type = BehaviorType.MOVEMENT_MODIFIER;
		_direction = direction;
		_power = power;
	}
	
	public void setDirection(int direction) {
		_direction = direction;
	}
	
	public void setPower(float power) {
		_power = power;
	}
	
	@Override
	protected void onUpdate(float updateRatio) {
		try {
			if (_direction != Direction.UNKNOWN && _direction != Direction.NONE) {
				Vertex center = Manager.Vertex.allocate();
				_entity.Attributes.Area.getCenter(center);
				
				updatePosition(center, updateRatio);
				_entity.Attributes.Area.setCenter(center);
				Manager.Vertex.release(center);
			}
		}
		catch (UndefinedVertexException e) {
			Logger.e(_tag, "Undefined Vertex", e);
		}
	}
	
	@Override
	protected void setTag() {
		_tag.setLength(0);
		_tag.append(_entity.getTag());
		_tag.append(": MovementModifierBehavior");
	}

	protected void updatePosition(Vertex center, float  multiplier) throws UndefinedVertexException {
		// allocate variables
		Vertex potentialCenter = Manager.Vertex.allocate();
		Vertex totalMovement = Manager.Vertex.allocate();
		Area.sync(potentialCenter, center);
		
		switch (_direction) {
		case Direction.UP:
			totalMovement.X = 0;
			totalMovement.Y = _power * multiplier;
			break;
		case Direction.DOWN:
			totalMovement.X = 0;
			totalMovement.Y = -_power * multiplier;
			break;
		case Direction.RIGHT:
			totalMovement.X = _power * multiplier;
			totalMovement.Y = 0;
			break;
		case Direction.LEFT:
			totalMovement.X = -_power * multiplier;
			totalMovement.Y = 0;
			break;
		}
		
		Vertex.add(center, totalMovement, potentialCenter);	
		
		// set position if no collision
		ICollisionSender sender = Manager.Collision.getCollision(potentialCenter, _collisionLayers);
		if ((totalMovement.X == 0 && totalMovement.Y == 0) || sender == null)
			Area.sync(center, potentialCenter);
		// otherwise find best position w/ no collision
		else {
			Vertex.sub(potentialCenter, totalMovement);
			setNoCollisionPotentialCenter(potentialCenter, totalMovement);
			Area.sync(center, potentialCenter);
		}
		
		// free allocations
		Manager.Vertex.release(potentialCenter);
		Manager.Vertex.release(totalMovement);
	}
	
	protected void setNoCollisionPotentialCenter(Vertex potentialCenter, Vertex totalMovement) {		
		boolean xHit = true;
		
		// check X movement
		potentialCenter.X += totalMovement.X;
		ICollisionSender sender = Manager.Collision.getCollision(potentialCenter, _collisionLayers);
		if (sender != null) {
			IArea area = sender.getArea();
			if (totalMovement.X > 0)
				potentialCenter.X = area.getPositionX() - 1;
			else
				potentialCenter.X = area.getPositionX() + area.getWidth() + 1;
		}
		else {
			// if X movement did not cause collision reset for now
			// we want to move along axis that causes col. first
			potentialCenter.X -= totalMovement.X;
			xHit = false;
		}
		
		// check Y movement
		potentialCenter.Y += totalMovement.Y;
		sender = Manager.Collision.getCollision(potentialCenter, _collisionLayers);
		if (sender != null) {
			IArea area = sender.getArea();
			if (totalMovement.Y > 0)
				potentialCenter.Y = area.getPositionY() - 1;
			else
				potentialCenter.Y = area.getPositionY() + area.getHeight() + 1;
		}
		
		// recheck X movement
		if (!xHit) {
			potentialCenter.X += totalMovement.X;
			sender = Manager.Collision.getCollision(potentialCenter, _collisionLayers);
			if (sender != null) {
				IArea area = sender.getArea();
				if (totalMovement.X > 0)
					potentialCenter.X = area.getPositionX() - 1;
				else
					potentialCenter.X = area.getPositionX() + area.getWidth() + 1;
			}
		}
	}
	
}
