package com.game.geodetective.behavior;

import com.game.loblib.behavior.Behavior;
import com.game.loblib.graphics.Sprite;
import com.game.loblib.messaging.MessageType;
import com.game.loblib.utility.Manager;
import com.game.loblib.utility.MathHelper;
import com.game.loblib.utility.TweenType;
import com.game.loblib.utility.android.FixedSizeArray;

public class TweenBehavior extends Behavior {

	protected int _tweenType;
	protected float _elapsedChange;
	protected float _totalChange;
	protected float _tweenTime;
	
	protected Sprite _sprite;
	protected FixedSizeArray<Sprite> _spriteList;
	protected boolean _spriteListSet = false;
	protected boolean _spriteSet = false;
	
	public TweenBehavior(int tweenType, float totalChange, float tweenTime) {
		_type = GeoDetectiveBehaviorType.TWEEN;
		_tweenType = tweenType;
		_totalChange = totalChange;
		_tweenTime = tweenTime;
	}
	
	public TweenBehavior(int tweenType, float totalChange, float tweenTime, Sprite sprite) {
		_type = GeoDetectiveBehaviorType.TWEEN;
		_tweenType = tweenType;
		_totalChange = totalChange;
		_tweenTime = tweenTime;
		_sprite = sprite;
		_spriteSet = true;
	}
	
	public TweenBehavior(int tweenType, float totalChange, float tweenTime, FixedSizeArray<Sprite> spriteList) {
		_type = GeoDetectiveBehaviorType.TWEEN;
		_tweenType = tweenType;
		_totalChange = totalChange;
		_tweenTime = tweenTime;
		_spriteList = spriteList;
		_spriteListSet = true;
	}
	
	public void setTweenType(int tweenType) {
		_tweenType = tweenType;
	}
	
	public int getTweenType() {
		return _tweenType;
	}
	
	public void setTotalChange(float totalChange) {
		_totalChange = totalChange;
	}
	
	public float getTotalChange() {
		return _totalChange;
	}
	
	public void setTweenTime(float tweenTime) {
		_tweenTime = tweenTime;
	}
	
	public float getTweenTime() {
		return _tweenTime;
	}
	
	@Override
	public void init() {
		if (!_spriteSet && !_spriteListSet)
			_sprite = _entity.Attributes.Sprite;
		_spriteSet = true;
	}
	
	@Override
	protected void onUpdate(float updateRatio) {
		float elapsedTime = updateRatio * 50f / 3f;
		float curChange = (elapsedTime / _tweenTime) * _totalChange;
		float changeLeft = _totalChange - _elapsedChange;
		curChange = Math.abs(curChange) < Math.abs(changeLeft) ? curChange : changeLeft;

		switch (_tweenType) {
		case TweenType.XPOSITION:
			if (_spriteListSet) {
				int count = _spriteList.getCount();
				for (int i = 0; i < count; i++)
					_spriteList.get(i).Area.changePosition(curChange, 0f);
			}
			else
				_sprite.Area.changePosition(curChange, 0f);
			break;
		case TweenType.YPOSITION:
			if (_spriteListSet) {
				int count = _spriteList.getCount();
				for (int i = 0; i < count; i++)
					_spriteList.get(i).Area.changePosition(0f, curChange);
			}
			else
				_sprite.Area.changePosition(0f, curChange);
			break;
		case TweenType.SIZE:
			if (_spriteListSet) {
				int count = _spriteList.getCount();
				for (int i = 0; i < count; i++)
					_spriteList.get(i).Area.changeSize(curChange, curChange);
			}
			else
				_sprite.Area.changeSize(curChange, curChange);
			break;
		case TweenType.ALPHA:
			if (_spriteListSet) {
				int count = _spriteList.getCount();
				for (int i = 0; i < count; i++) {
					Sprite sprite = _spriteList.get(i);
					sprite.Alpha = MathHelper.clamp(0f, 1f, sprite.Alpha + curChange);
				}
			}
			else
				_sprite.Alpha = MathHelper.clamp(0f, 1f, _sprite.Alpha + curChange);
			break;
		}
		
		_elapsedChange += curChange;
		
		if (Math.abs(_elapsedChange) >= Math.abs(_totalChange)) {
			Manager.Message.sendMessage(MessageType.TWEEN_FINISHED, _entity);
			disable();
		}
	}

	@Override
	protected void onEnable() {
		_elapsedChange = 0f;
	}

	@Override
	protected void setTag() {
		_tag.setLength(0);
		_tag.append(_entity.getTag());
		_tag.append(": TweenBehavior");
	}

}
