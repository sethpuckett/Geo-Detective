package com.game.geodetective.utility.area;

import com.game.geodetective.utility.android.TObjectPool;

public class CirclePool extends TObjectPool<Circle> {
	protected StringBuffer _tag;
	
	public CirclePool(int size) {
		super(size);
		_tag = new StringBuffer("CirclePool");
		fill();
	}
	
	@Override
	protected void fill() {
		int size = getSize();
		for (int x = 0; x < size; x++) {
			getAvailable().add(new Circle());
		}
	}
}
