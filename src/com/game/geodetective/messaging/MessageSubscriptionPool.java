package com.game.geodetective.messaging;

import com.game.geodetective.utility.android.TObjectPool;

public class MessageSubscriptionPool extends TObjectPool<MessageSubscription> {
	protected StringBuffer _tag;
	
	public MessageSubscriptionPool(int size) {
		super(size);
		_tag = new StringBuffer("MessageSubscriptionPool");
		fill();
	}
	
	@Override
	protected void fill() {
		int size = getSize();
		for (int x = 0; x < size; x++) {
			getAvailable().add(new MessageSubscription());
		}
	}

}
