package com.game.geodetective.behavior;

import com.game.geodetective.messaging.GDMessageType;
import com.game.loblib.behavior.Behavior;
import com.game.loblib.graphics.Sprite;
import com.game.loblib.messaging.MessageType;
import com.game.loblib.utility.FadeType;
import com.game.loblib.utility.Logger;
import com.game.loblib.utility.Manager;
import com.game.loblib.utility.android.FixedSizeArray;

public class FadeChainBehavior extends Behavior {

	protected final int MAX_SPRITE_COUNT = 20;

	public static final int STATE_PRESTART = -1;
	public static final int STATE_FADEIN = 0;
	public static final int STATE_HOLD = 1;
	public static final int STATE_FADEOUT = 2;
	public static final int STATE_OFF = 3;
	
	protected float[][] _stateChangeTimingGrid;
	protected int[] _stateArray;
	protected FixedSizeArray<Sprite> _spriteChain;
	protected float _maxAlpha;
	protected float _fadeInTime;
	protected float _fadeoutTime;
	protected float _holdTime;
	protected float _advanceSpriteTime;
	protected int _layer;
	protected int _fadeType;
	protected float _pauseTime;
	
	protected int _currentSpriteIndex;
	protected float _elapsedTime;
	protected float _cycleTime = 0;
	
	public FadeChainBehavior(int layer, float maxAlpha, float fadeInTime, float fadeOutTime, float holdTime, float advanceSpriteTime, int fadeType) {
		_type = GDBehaviorType.FADE_CHAIN;
		
		_stateArray = new int[MAX_SPRITE_COUNT];
		_stateChangeTimingGrid = new float[MAX_SPRITE_COUNT][4];
		_spriteChain = new FixedSizeArray<Sprite>(MAX_SPRITE_COUNT);
		_layer = layer;
		_maxAlpha = maxAlpha;
		_fadeInTime = fadeInTime;
		_fadeoutTime = fadeOutTime;
		_holdTime = holdTime;
		_advanceSpriteTime = advanceSpriteTime;
		_fadeType = fadeType;
		_pauseTime = 0f;
	}
	
	public FadeChainBehavior(int layer, float maxAlpha, float fadeInTime, float fadeOutTime, float holdTime, float advanceSpriteTime, int fadeType, float pauseTime) {
		_type = GDBehaviorType.FADE_CHAIN;
		
		_stateArray = new int[MAX_SPRITE_COUNT];
		_stateChangeTimingGrid = new float[MAX_SPRITE_COUNT][4];
		_spriteChain = new FixedSizeArray<Sprite>(MAX_SPRITE_COUNT);
		_layer = layer;
		_maxAlpha = maxAlpha;
		_fadeInTime = fadeInTime;
		_fadeoutTime = fadeOutTime;
		_holdTime = holdTime;
		_advanceSpriteTime = advanceSpriteTime;
		_fadeType = fadeType;
		_pauseTime = pauseTime;
	}
	
	public void addSprite(Sprite sprite) {
		if (_spriteChain.getCount() >= MAX_SPRITE_COUNT)
			Logger.e(_tag, "Cannot add sprite; max count exceded");
		else
			_spriteChain.add(sprite);
	}
	
	public int getState(int index) {
		int state = 0;
		if (index >= _stateArray.length)
			Logger.e(_tag, "sprite index out of range");
		else
			state = _stateArray[index];
		return state;
	}
	
	@Override
	public void onEnable() {
		init();
	}
	
	@Override
	public void init() {
		_currentSpriteIndex = 0;
		_elapsedTime = 0;
		java.util.Arrays.fill(_stateArray, STATE_PRESTART);
		int count = _spriteChain.getCount();
		for (int i = 0; i < count; i++) {
			_spriteChain.get(i).Alpha = 0f;
			
			_stateChangeTimingGrid[i][STATE_FADEIN] = _advanceSpriteTime * i;
			_stateChangeTimingGrid[i][STATE_HOLD] = _stateChangeTimingGrid[i][STATE_FADEIN] + _fadeInTime;
			_stateChangeTimingGrid[i][STATE_FADEOUT] = _stateChangeTimingGrid[i][STATE_HOLD] + _holdTime;
			_stateChangeTimingGrid[i][STATE_OFF] = _stateChangeTimingGrid[i][STATE_FADEOUT] + _fadeoutTime;
			if (i == 0)
				_cycleTime = _stateChangeTimingGrid[i][STATE_OFF] + _pauseTime;
		}
	}
	
	@Override
	public void onUpdate(float updateRatio) {
		_elapsedTime += updateRatio * 50f / 3f;	
		
		int count = _spriteChain.getCount();
		
		// Update State
		for (int i = 0; i < count; i++) {
			if (_stateArray[i] != STATE_OFF &&
					_stateChangeTimingGrid[i][_stateArray[i] + 1] < _elapsedTime) {
				if (_stateArray[i] == STATE_FADEOUT && _fadeType == FadeType.CONTINUOUS) {
					_stateArray[i] = STATE_PRESTART;
					_stateChangeTimingGrid[i][STATE_FADEIN] = _elapsedTime + _pauseTime;
					_stateChangeTimingGrid[i][STATE_HOLD] = _stateChangeTimingGrid[i][STATE_FADEIN] + _fadeInTime;
					_stateChangeTimingGrid[i][STATE_FADEOUT] = _stateChangeTimingGrid[i][STATE_HOLD] + _holdTime;
					_stateChangeTimingGrid[i][STATE_OFF] = _stateChangeTimingGrid[i][STATE_FADEOUT] + _fadeoutTime;
				}
				else
					_stateArray[i]++;
				
				Manager.Message.sendMessage(MessageType.FADE_STATE_CHANGE, _entity);
				
				if (_stateArray[i] == STATE_PRESTART)
					_spriteChain.get(i).Alpha = 0;
				if (_stateArray[i] == STATE_HOLD)
					_spriteChain.get(i).Alpha = _maxAlpha;
				else if (_stateArray[i] == STATE_OFF) {
					_spriteChain.get(i).Alpha = 0;
					if (i == _spriteChain.getCount() - 1) {
						Manager.Message.sendMessage(GDMessageType.FADE_CHAIN_COMPLETE, _entity);
						disable();
					}
				}
			}
		}
		
		// Apply state logic
		for (int i = 0; i < count; i++) {
			switch (_stateArray[i]) {
			case STATE_PRESTART:
				break;
			case STATE_FADEIN:
				_spriteChain.get(i).Alpha = ((_elapsedTime - _stateChangeTimingGrid[i][STATE_FADEIN]) / _fadeInTime) * _maxAlpha;
				Manager.Sprite.draw(_spriteChain.get(i), _layer);
				break;
			case STATE_HOLD:
				Manager.Sprite.draw(_spriteChain.get(i), _layer);
				break;
			case STATE_FADEOUT:
				_spriteChain.get(i).Alpha = _maxAlpha - (((_elapsedTime - _stateChangeTimingGrid[i][STATE_FADEOUT]) / _fadeoutTime) * _maxAlpha);
				Manager.Sprite.draw(_spriteChain.get(i), _layer);
				break;
			case STATE_OFF:
				break;
			}
		}
		
		
	}
	
	@Override
	protected void setTag() {
		_tag.setLength(0);
		_tag.append(_entity.getTag());
		_tag.append(": FadeChainBehavior");
	}

}
