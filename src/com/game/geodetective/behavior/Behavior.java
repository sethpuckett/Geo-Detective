package com.game.geodetective.behavior;

import com.game.geodetective.entity.GameEntity;
import com.game.geodetective.utility.android.AllocationGuard;

public abstract class Behavior extends AllocationGuard {
	protected StringBuffer _tag = new StringBuffer("Behavior");;
	protected long _type = BehaviorType.UNKNOWN;
	protected GameEntity _entity;
	protected boolean _enabled;
	
	public Behavior(){
	}
	
	public long getType() {
		return _type;
	}
	
	public void setEntity(GameEntity entity) {
		_entity = entity;
		setTag();
		onSetEntity();
	}
	
	public GameEntity getEntity() {
		return _entity;
	}
	
	public StringBuffer getTag() {
		return _tag;
	}
	
	public void update(float updateRatio) {
		onUpdate(updateRatio);
	}
	
	public void init() {
		// nothing to do
	}
	
	public void destroy() {
		onDestroy();
	}
	
	public void enable() {
		if (!_enabled) {
			_enabled = true;
			onEnable();
		}
	}
	
	public void disable() {
		if (_enabled) {
			_enabled = false;
			onDisable();
		}
	}
	
	public boolean isEnabled() {
		return _enabled;
	}

	protected void onDestroy()
	{
		// nothing to do
	}
	
	protected void onUpdate(float updateRatio)
	{
		// nothing to do
	}
	
	protected void onEnable()
	{
		// nothing to do
	}
	
	protected void onDisable()
	{
		// nothing to do
	}
	
	protected void onSetEntity() {
		// nothing to do
	}
	
	protected abstract void setTag();
}
