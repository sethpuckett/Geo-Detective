package com.game.geodetective.behavior;


public class DelayedRenderBehavior extends RenderBehavior {

	protected float _delayTime;
	protected float _fadeInTime;
	
	protected float _remainingDelayTime;
	protected float _remainingFadeTime;
	
	public DelayedRenderBehavior(int layer, float delayTime) {
		super(layer);
		_delayTime = delayTime;
		_fadeInTime = 0f;
	}
	
	public DelayedRenderBehavior(int layer, float delayTime, float fadeInTime) {
		super(layer);
		_delayTime = delayTime;
		_fadeInTime = fadeInTime;
	}
	
	@Override 
	public void init() {
		_remainingDelayTime = _delayTime;
		_remainingFadeTime = _fadeInTime;
	}
	
	@Override 
	public void onEnable() {
		_remainingDelayTime = _delayTime;
		_remainingFadeTime = _fadeInTime;
	}
	
	@Override
	public void onUpdate(float updateRatio) {
		float elapsedTime = updateRatio * 50f / 3f;	
		if (_remainingDelayTime > 0f)
			_remainingDelayTime -= elapsedTime;
		else if (_remainingFadeTime > 0f){
			_remainingFadeTime -= elapsedTime;
			if (_remainingFadeTime > 0f)
				_entity.Attributes.Sprite.Alpha = (_fadeInTime - _remainingFadeTime) / _fadeInTime;
			else
				_entity.Attributes.Sprite.Alpha = 1f;
			super.onUpdate(updateRatio);
		}
		else
			super.onUpdate(updateRatio);
	}
	
	@Override
	protected void setTag() {
		_tag.setLength(0);
		_tag.append(_entity.getTag());
		_tag.append(": DelayedRenderBehavior");
	}

}
