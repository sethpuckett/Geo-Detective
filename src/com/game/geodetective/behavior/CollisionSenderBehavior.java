package com.game.geodetective.behavior;

import com.game.loblib.behavior.Behavior;
import com.game.loblib.collision.ICollisionSender;
import com.game.loblib.utility.Manager;
import com.game.loblib.utility.area.Area;
import com.game.loblib.utility.area.AreaType;
import com.game.loblib.utility.area.IArea;
import com.game.loblib.utility.area.Rectangle;

public class CollisionSenderBehavior extends Behavior implements ICollisionSender {
	protected StringBuffer _tag = new StringBuffer("MainBlockBehavior");
	protected IArea _area;
	protected long _layers;
	protected Rectangle _prevArea = new Rectangle();

	public CollisionSenderBehavior(AreaType type, long collisionLayers) {
		_type = GeoDetectiveBehaviorType.COLLISION_SENDER;
		_area = Area.allocate(type);
		_layers = collisionLayers;
	}
	
	@Override
	protected void onUpdate(float updateRatio) {
		if (!_prevArea.equals(_entity.Attributes.Area)) {
			Manager.Collision.removeSender(this);
			Area.sync(_prevArea, _entity.Attributes.Area);
			Area.sync(_area, _entity.Attributes.Area);
			Manager.Collision.addSender(this);
		}
	}

	@Override
	protected void onDestroy() {
		Manager.Collision.removeSender(this);
	}
	
	@Override
	protected void onEnable() {
		Area.sync(_prevArea, _entity.Attributes.Area);
		Area.sync(_area, _entity.Attributes.Area);
		Manager.Collision.addSender(this);
	}

	@Override
	protected void onDisable() {
		Manager.Collision.removeSender(this);
	}

	@Override
	protected void setTag() {
		_tag.setLength(0);
		_tag.append(_entity.getTag());
		_tag.append(": MainBlockBehavior");
	}

	@Override
	public long getLayers() {
		return _layers;
	}

	@Override
	public IArea getArea() {
		return _area;
	}
}
