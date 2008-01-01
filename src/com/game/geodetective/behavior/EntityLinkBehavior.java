package com.game.geodetective.behavior;

import com.game.loblib.behavior.Behavior;
import com.game.loblib.entity.GameEntity;

public class EntityLinkBehavior extends Behavior {
	
	protected GameEntity _linkedEntity = null;

	public EntityLinkBehavior() {
		_type = GeoDetectiveBehaviorType.ENTITY_LINK;
	}
	
	public EntityLinkBehavior(GameEntity linkedEntity) {
		_type = GeoDetectiveBehaviorType.ENTITY_LINK;
		_linkedEntity = linkedEntity;
	}
	
	public void setLinkedEntity(GameEntity linkedEntity) {
		_linkedEntity = linkedEntity;
	}
	
	public GameEntity getLinkedEntity() {
		return _linkedEntity;
	}

	@Override
	protected void setTag() {
		_tag.setLength(0);
		_tag.append(_entity.getTag());
		_tag.append(": EntityLinkBehavior");
	}

}
