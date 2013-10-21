package com.game.geodetective.input;

import com.game.geodetective.utility.android.TObjectPool;

public class GameMotionEventPool extends TObjectPool<GameMotionEvent> {
	protected StringBuffer _tag;
	
	public GameMotionEventPool(int size) {
		super(size);
		_tag = new StringBuffer("GameMotionEventPool");
		fill();
	}
	
	@Override
	protected void fill() {
		int size = getSize();
		for (int x = 0; x < size; x++) {
			getAvailable().add(new GameMotionEvent());
		}
	}

}
