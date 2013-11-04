package com.game.geodetective.behavior;

import com.game.loblib.behavior.Behavior;
import com.game.loblib.utility.Manager;

public class TextRenderBehavior extends Behavior {

	protected String _text;
	
	public TextRenderBehavior(String text) {
		_type = GeoDetectiveBehaviorType.TEXT_RENDER;
		_text = text;
	}
	
	public void SetText(String text) {
		_text = text;
	}
	
	@Override
	public void onUpdate(float updateRatio) {
		Manager.Sprite.drawText(_text, _entity.Attributes.Area.Position.X, _entity.Attributes.Area.Position.Y);
	}
	
	@Override 
	protected void setTag() {
		_tag.setLength(0);
		_tag.append(_entity.getTag());
		_tag.append(": RenderBehavior");
	}

}
