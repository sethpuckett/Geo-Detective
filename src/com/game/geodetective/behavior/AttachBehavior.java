package com.game.geodetective.behavior;

import com.game.loblib.behavior.Behavior;
import com.game.loblib.entity.GameEntity;
import com.game.loblib.utility.area.Vertex;

// Only attaches position to position. In the future may want to update
// to attach to center
public class AttachBehavior extends Behavior {

	protected GameEntity _attachEntity;
	protected Vertex _offset = new Vertex();
	protected boolean _fixedX = false;
	protected boolean _fixedY = false;
	protected float _fixedXPosition;
	protected float _fixedYPosition;
	
	public AttachBehavior(GameEntity attachEntity) {
		_attachEntity = attachEntity;
		_type = GDBehaviorType.ATTACH;
	}
	
	public AttachBehavior(GameEntity attachEntity, float xOffset, float yOffset) {
		_attachEntity = attachEntity;
		_offset.X = xOffset;
		_offset.Y = yOffset;
		_type = GDBehaviorType.ATTACH;
	}
	
	public void setOffset(float xOffset, float yOffset) {
		_offset.X = xOffset;
		_offset.Y = yOffset;
	}
	
	public void enableFixedX(float x) {
		_fixedXPosition = x;
		if (_entity != null)
			_entity.Attributes.Area.Position.X = _fixedXPosition;
		_fixedX = true;
	}
	
	public void enableFixedY(float y) {
		_fixedYPosition = y;
		if (_entity != null)
			_entity.Attributes.Area.Position.Y = _fixedYPosition;
		_fixedY = true;
	}
	
	public void disableFixedX() {
		_fixedX = false;
	}
	
	public void disableFixedY() {
		_fixedY = false;
	}
	
	@Override
	public void onUpdate(float updateRatio) {
		if (!_fixedX)
			_entity.Attributes.Area.Position.X = _attachEntity.Attributes.Area.Position.X + _offset.X;
		if (!_fixedY)
			_entity.Attributes.Area.Position.Y = _attachEntity.Attributes.Area.Position.Y + _offset.Y;
	}
	
	@Override
	protected void setTag() {
		_tag.setLength(0);
		_tag.append(_entity.getTag());
		_tag.append(": AttachBehavior");
	}
	
	@Override
	protected void onSetEntity() {
		if (_fixedX)
			_entity.Attributes.Area.Position.X = _fixedXPosition;
		if (_fixedY)
			_entity.Attributes.Area.Position.Y = _fixedYPosition;
	}

}
