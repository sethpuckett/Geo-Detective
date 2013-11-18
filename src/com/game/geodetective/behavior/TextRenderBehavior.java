package com.game.geodetective.behavior;

import com.game.loblib.behavior.Behavior;
import com.game.loblib.utility.Manager;

public class TextRenderBehavior extends Behavior {

	protected String _text;
	protected boolean _centerX;
	protected boolean _centerY;
	protected float _width;
	protected float _red;
	protected float _green;
	protected float _blue;
	protected float _alpha;
	
	public TextRenderBehavior(String text, boolean centerX, boolean centerY) {
		_type = GDBehaviorType.TEXT_RENDER;
		_text = text;
		_centerX = centerX;
		_centerY = centerY;
		
		_width = 0f;
		_red = 0f;
		_green = 0f;
		_blue = 0f;
		_alpha = 1f;
	}
	
	public TextRenderBehavior(String text, boolean centerX, boolean centerY, float width, float red, float green, float blue, float alpha) {
		_type = GDBehaviorType.TEXT_RENDER;
		_text = text;
		_centerX = centerX;
		_centerY = centerY;
		
		_width = width;
		_red = red;
		_green = green;
		_blue = blue;
		_alpha = alpha;
	}
	
	public void setText(String text) {
		_text = text;
	}
	
	public void setCenter(boolean centerX, boolean centerY) {
		_centerX = centerX;
		_centerY = centerY;
	}
	
	public void setWidth(float width) {
		_width = width;
	}
	
	public void setProperties(float red, float green, float blue, float alpha) {
		_red = red;
		_green = green;
		_blue = blue;
		_alpha = alpha;
	}
	
	@Override
	public void onUpdate(float updateRatio) {
		Manager.Sprite.drawText(_text, _entity.Attributes.Area.Position.X, _entity.Attributes.Area.Position.Y, _centerX, _centerY, _width, _red, _green, _blue, _alpha);
	}
	
	@Override 
	protected void setTag() {
		_tag.setLength(0);
		_tag.append(_entity.getTag());
		_tag.append(": RenderBehavior");
	}

}
