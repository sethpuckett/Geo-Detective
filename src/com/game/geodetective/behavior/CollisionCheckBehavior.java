package com.game.geodetective.behavior;

import com.game.loblib.behavior.Behavior;
import com.game.loblib.collision.CollisionLayer;
import com.game.loblib.collision.ICollisionSender;
import com.game.loblib.utility.Manager;
import com.game.loblib.utility.area.Vertex;

public class CollisionCheckBehavior extends Behavior {

	protected long _collisionLayers = CollisionLayer.UNKNOWN;
	protected Vertex _center = new Vertex();
	
	public CollisionCheckBehavior(long layers) {
		_type = GeoDetectiveBehaviorType.COLLISION_CHECK;
		_collisionLayers = layers;
	}
	
	@Override
	protected void onUpdate(float updateRatio) {
		_entity.Attributes.Area.getCenter(_center);
		ICollisionSender sender = Manager.Collision.getCollision(_center, _collisionLayers);
		if (sender != null)
			onCollision(sender);
	}
	
	protected void onCollision(ICollisionSender sender) {
		// Nothing to do
	}

	@Override
	protected void setTag() {
		_tag.setLength(0);
		_tag.append(_entity.getTag());
		_tag.append(": CollisionCheckBehavior");
	}

}
