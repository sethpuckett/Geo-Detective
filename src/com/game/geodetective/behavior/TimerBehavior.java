package com.game.geodetective.behavior;

import com.game.loblib.behavior.Behavior;
import com.game.loblib.messaging.MessageType;
import com.game.loblib.utility.Manager;
import com.game.loblib.utility.TimerType;

public class TimerBehavior extends Behavior {

	protected int _timerType;
	protected float _totalTime;
	
	protected float _timeRemaining;
	
	public TimerBehavior() {
		_type = GeoDetectiveBehaviorType.TIMER;
	}
	
	public TimerBehavior(int timerType, float time) {
		_type = GeoDetectiveBehaviorType.TIMER;
		_timerType = timerType;
		_totalTime = time;
	}
	
	public void setTimerType(int timerType) {
		_timerType = timerType;
	}
	
	public void setTime(float time) {
		_totalTime = time;
	}
	
	public float getRemainingTime() {
		return _timeRemaining;
	}
	
	public void rest() {
		init();
	}
	
	@Override
	public void init() {
		_timeRemaining = _totalTime;
	}
	
	@Override
	protected void onUpdate(float updateRatio) {
		float elapsedTime = updateRatio * 50f / 3f;	
		_timeRemaining -= elapsedTime;
		
		if (_timeRemaining <= 0) {
			Manager.Message.sendMessage(MessageType.TIMER_ALARM, _entity);
			init();
			if (_timerType == TimerType.SINGLE)
				disable();
		}
	}
	
	@Override
	protected void setTag() {
		_tag.setLength(0);
		_tag.append(_entity.getTag());
		_tag.append(": TimerBehavior");
	}

}
