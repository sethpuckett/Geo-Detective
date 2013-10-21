package com.game.geodetective.messaging;

import com.game.geodetective.utility.android.TObjectPool;

public class MessagePool extends TObjectPool<Message> {
	protected StringBuffer _tag;
	
	public MessagePool(int size) {
		super(size);
		_tag = new StringBuffer("MessagePool");
		fill();
	}
	
	@Override
	protected void fill() {
		int size = getSize();
		for (int x = 0; x < size; x++) {
			getAvailable().add(new Message());
		}
	}
}