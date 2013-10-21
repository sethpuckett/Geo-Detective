package com.game.geodetective.collision;

import com.game.geodetective.entity.GameEntity;
import com.game.geodetective.behavior.Behavior;
import com.game.geodetective.messaging.IMessageHandler;
import com.game.geodetective.messaging.Message;
import com.game.geodetective.messaging.MessageType;
import com.game.geodetective.utility.Manager;
import com.game.geodetective.utility.android.FixedSizeArray;

public class TriggerManager implements IMessageHandler {

	protected FixedSizeArray<ICollisionSender> _triggers = new FixedSizeArray<ICollisionSender>(64);
	protected FixedSizeArray<ICollisionSender> _remove = new FixedSizeArray<ICollisionSender>(64);
	protected boolean[] _triggerStates = new boolean[64];
	
	public void init() {
		Manager.Message.subscribe(this, MessageType.COLLISION);
	}
	
	public void update() {
		// check if triggers hit last update are still being hit; if not send message and remove from list
		int count = _triggers.getCount();
		for (int i = 0; i < count; i++) {
			if (!_triggerStates[i]) {
				Manager.Message.sendMessage(MessageType.TRIGGER_RELEASED, ((Behavior)(_triggers.get(i))).getEntity());
				_remove.add(_triggers.get(i));
			}
			else
				_triggerStates[i] = false;
		}
		if (_remove.getCount() > 0) {
			_triggers.removeAll(_remove, false);
			_remove.clear();
		}
	}
	
	@Override
	public void handleMessage(Message message) {
		if (message.Type == MessageType.COLLISION) {
			ICollisionSender sender = message.getData();
			if ((sender.getLayers() & CollisionLayer.TRIGGER) > 0) {
				int trigIndex = _triggers.find(sender, false);
				if (trigIndex == -1) {
					Manager.Message.sendMessage(MessageType.TRIGGER_HIT, ((Behavior)sender).getEntity());
					_triggers.add(sender);
					_triggerStates[_triggers.getCount() - 1] = true;
				}
				else {
					_triggerStates[trigIndex] = true;
				}
			}
		}
	}

	public boolean hit(GameEntity entity) {
		boolean result = false;
		
		int count = _triggers.getCount();
		for (int i = 0; i < count; i ++) {
			if (((Behavior)_triggers.get(i)).getEntity() == entity) {
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	 public void flush() {
		_triggers.clear();
		_remove.clear();
	}
}
