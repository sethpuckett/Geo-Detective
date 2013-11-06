package com.game.geodetective.behavior;

import com.game.loblib.graphics.Sprite;
import com.game.loblib.messaging.MessageType;
import com.game.loblib.utility.FadeType;
import com.game.loblib.utility.Manager;
import com.game.loblib.utility.TweenType;
import com.game.loblib.utility.android.FixedSizeArray;

public class FadeBehavior extends TweenBehavior {

	protected float _fadeInTime;
	protected float _fadeOutTime;
	protected boolean _initOn;
	protected int _fadeType;

	protected boolean _fadingIn;
	protected boolean _fadingOut;

	public FadeBehavior(float fadeInTime, float fadeOutTime) {
		super(TweenType.ALPHA, -1f, fadeOutTime);
		_type = GDBehaviorType.FADE;
		_fadeInTime = fadeInTime;
		_fadeOutTime = fadeOutTime;
		_fadeType = FadeType.SINGLE;
		_initOn = true;
	}

	public FadeBehavior(float fadeInTime, float fadeOutTime, boolean initOn, int fadeType) {
		super(TweenType.ALPHA, initOn ? -1 : 1f, initOn ? fadeOutTime
				: fadeInTime);
		_type = GDBehaviorType.FADE;
		_fadeInTime = fadeInTime;
		_fadeOutTime = fadeOutTime;
		_initOn = initOn;
		_fadeType = fadeType;
	}

	public FadeBehavior(float fadeInTime, float fadeOutTime, boolean initOn, int fadeType,
			FixedSizeArray<Sprite> spriteList) {
		super(TweenType.ALPHA, initOn ? -1 : 1f, initOn ? fadeOutTime
				: fadeInTime, spriteList);
		_type = GDBehaviorType.FADE;
		_fadeInTime = fadeInTime;
		_fadeOutTime = fadeOutTime;
		_initOn = initOn;
		_fadeType = fadeType;
	}

	@Override
	public void onEnable() {
		super.onEnable();
		if (!_fadingIn && !_fadingOut)
			_elapsedChange = _totalChange;
	}

	@Override
	public void onDisable() {
		super.onDisable();

		if (Math.abs(_elapsedChange) >= Math.abs(_totalChange)) {
			if (_fadingIn)
				Manager.Message.sendMessage(MessageType.FADE_IN_COMPLETE,
						_entity);
			else if (_fadingOut)
				Manager.Message.sendMessage(MessageType.FADE_OUT_COMPLETE,
						_entity);
		}

		if (_fadeType == FadeType.CONTINUOUS) {
			if (_fadingIn)
				fadeOut();
			else if (_fadingOut)
				fadeIn();
		}
		else {
			_fadingIn = false;
			_fadingOut = false;
		}
	}

	@Override
	public void init() {
		super.init();
		
		_fadingIn = false;
		_fadingOut = false;

		if (_initOn) {
			if (_spriteListSet) {
				int count = _spriteList.getCount();
				for (int i = 0; i < count; i++)
					_spriteList.get(i).Alpha = 1f;
			} else
				_sprite.Alpha = 1f;
		} else {
			if (_spriteListSet) {
				int count = _spriteList.getCount();
				for (int i = 0; i < count; i++)
					_spriteList.get(i).Alpha = 0f;
			} else
				_sprite.Alpha = 0f;
		}

	}

	@Override
	public void onUpdate(float updateRatio) {
		super.onUpdate(updateRatio);
	}

	@Override
	protected void setTag() {
		_tag.setLength(0);
		_tag.append(_entity.getTag());
		_tag.append(": FadeBehavior");
	}

	public void fadeIn() {
		_fadingOut = false;
		_fadingIn = true;
		_elapsedChange = 0f;
		setTweenTime(_fadeInTime);
		setTotalChange(1f);
		enable();
	}

	public void fadeOut() {
		_fadingOut = true;
		_fadingIn = false;
		_elapsedChange = 0f;
		setTweenTime(_fadeOutTime);
		setTotalChange(-1f);
		enable();
	}
	
	public void stop(boolean visible) {
		if (visible) {
			if (_spriteListSet) {
				int count = _spriteList.getCount();
				for (int i = 0; i < count; i++)
					_spriteList.get(i).Alpha = 1f;
			} else
				_sprite.Alpha = 1f;
		} else {
			if (_spriteListSet) {
				int count = _spriteList.getCount();
				for (int i = 0; i < count; i++)
					_spriteList.get(i).Alpha = 0f;
			} else
				_sprite.Alpha = 0f;
		}
		
		_fadingIn = false;
		_fadingOut = false;
		
		disable();
	}
}
