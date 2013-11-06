package com.game.geodetective.behavior;

import com.game.loblib.behavior.Behavior;
import com.game.loblib.graphics.Sprite;
import com.game.loblib.utility.Manager;
import com.game.loblib.utility.area.Rectangle;

public class RenderBehavior extends Behavior {
	protected Sprite _sprite;
	protected int _layer;
	
	public RenderBehavior(int layer) {
		_layer = layer;
		_type = GDBehaviorType.RENDER;
	}
	
	public void setLayer(int layer) {
		_layer = layer;
	}
	
	@Override
	public void onUpdate(float updateRatio) {
		Rectangle.sync(_entity.Attributes.Sprite.Area, _entity.Attributes.Area);
		if (_entity.Attributes.Sprite.isValid())
			Manager.Sprite.draw(_entity.Attributes.Sprite, _layer);	
	}
	
	@Override 
	protected void setTag() {
		_tag.setLength(0);
		_tag.append(_entity.getTag());
		_tag.append(": RenderBehavior");
	}
}
