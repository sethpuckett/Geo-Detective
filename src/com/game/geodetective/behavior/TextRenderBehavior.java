package com.game.geodetective.behavior;

import com.game.loblib.behavior.Behavior;
import com.game.loblib.utility.Manager;

public class TextRenderBehavior extends Behavior {

	protected String _text;
	protected boolean _centerX;
	protected boolean _centerY;
	
	public TextRenderBehavior(String text, boolean centerX, boolean centerY) {
		_type = GDBehaviorType.TEXT_RENDER;
		_text = text;
		_centerX = centerX;
		_centerY = centerY;
	}
	
	public void SetText(String text) {
		_text = text;
	}
	
	public void SetCenter(boolean centerX, boolean centerY) {
		_centerX = centerX;
		_centerY = centerY;
	}
	
	@Override
	public void onUpdate(float updateRatio) {
		Manager.Sprite.drawText(_text, _entity.Attributes.Area.Position.X, _entity.Attributes.Area.Position.Y, _centerX, _centerY);
	}
	
	@Override 
	protected void setTag() {
		_tag.setLength(0);
		_tag.append(_entity.getTag());
		_tag.append(": RenderBehavior");
	}

}
