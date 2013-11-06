package com.game.geodetective.behavior;

import com.game.loblib.behavior.Behavior;


public class BlinkBehavior extends Behavior {

	protected final int HOLD_ON = 0;
	protected final int FADE_OFF = 1;
	protected final int HOLD_OFF = 2;
	protected final int FADE_ON = 3;
	
	protected float _holdTime;
	protected float _fadeTime;
	protected float _timeRemaining;
	protected int _state;
	
	public BlinkBehavior() {
		_type = GDBehaviorType.BLINK;
	}
	
	public BlinkBehavior(float fadeTime, float holdTime) {
		_type = GDBehaviorType.BLINK;
		_fadeTime = fadeTime;
		_holdTime = holdTime;
	}
	
	public void setFadeTime(float fadeTime) {
		_fadeTime = fadeTime;
	}
	
	public void setHoldTime(float holdTime) {
		_holdTime = holdTime;
	}
	
	@Override
	public void init() {
		if (_holdTime > 0) {
			_state = HOLD_OFF;
			_entity.Attributes.Sprite.Alpha = 0f;
		}
		else
			_state = FADE_OFF;
		
		if (_state == HOLD_OFF)
			_timeRemaining = _holdTime;
		else
			_timeRemaining = _fadeTime;
	}
	
	@Override
	protected void onUpdate(float updateRatio) {
		float elapsedTime = updateRatio * 50f / 3f;	
		_timeRemaining -= elapsedTime;
		
		switch (_state) {
		case HOLD_ON:
			if (_timeRemaining <= 0) {
				if (_fadeTime > 0) {
					_state = FADE_OFF;
					_timeRemaining = _fadeTime;
				}
				else {
					_state = HOLD_OFF;
					_entity.Attributes.Sprite.Alpha = 0f;
					_timeRemaining = _holdTime;
				}
			}
			break;
		case FADE_OFF:
			if (_timeRemaining <= 0) {
				if (_holdTime > 0) {
					_state = HOLD_OFF;
					_entity.Attributes.Sprite.Alpha = 0f;
					_timeRemaining = _holdTime;
				}
				else {
					_state = FADE_ON;
					_entity.Attributes.Sprite.Alpha = 0f;
					_timeRemaining = _fadeTime;
				}
			}
			else
				_entity.Attributes.Sprite.Alpha = _timeRemaining / _fadeTime;
			break;
		case HOLD_OFF:
			if (_timeRemaining <= 0) {
				if (_fadeTime > 0) {
					_state = FADE_ON;
					_timeRemaining = _fadeTime;
				}
				else {
					_state = HOLD_ON;
					_entity.Attributes.Sprite.Alpha = 1f;
					_timeRemaining = _holdTime;
				}
			}
			break;
		case FADE_ON:
			if (_timeRemaining <= 0) {
				if (_holdTime > 0) {
					_state = HOLD_ON;
					_entity.Attributes.Sprite.Alpha = 1f;
					_timeRemaining = _holdTime;
				}
				else {
					_state = FADE_OFF;
					_entity.Attributes.Sprite.Alpha = 1f;
					_timeRemaining = _fadeTime;
				}
			}
			else
				_entity.Attributes.Sprite.Alpha = 1f - (_timeRemaining / _fadeTime);
			break;
		}
	}
	
	@Override
	protected void setTag() {
		_tag.setLength(0);
		_tag.append(_entity.getTag());
		_tag.append(": BlinkBehavior");
	}

}
