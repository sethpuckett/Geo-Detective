package com.game.geodetective;

import com.game.geodetective.utility.GDGlobal;
import com.game.loblib.LobLibGame;
import com.game.loblib.messaging.IMessageHandler;
import com.game.loblib.messaging.Message;
import com.game.loblib.messaging.MessageType;
import com.game.loblib.utility.ComponentFactory;
import com.game.loblib.utility.Global;
import com.game.loblib.utility.Manager;

public class GDGame extends LobLibGame implements IMessageHandler {
	
	@Override
	public void init(ComponentFactory factory) {
		super.init(factory);
		
		Manager.Message.subscribe(this, MessageType.SURFACE_CREATED);
	}

	@Override
	public void handleMessage(Message message) {
		if (message.Type == MessageType.SURFACE_CREATED) {
	        GDGlobal.TextManager.addText("CALIBRI SMALL", "CALIBRI.TTF", 48, 6, 6);
	        GDGlobal.TextManager.addText("CALIBRI BIG", "CALIBRI.TTF", 72, 8, 8);
		}
	}
	
	@Override
	public void onPause() {
		Manager.Message.unsubscribe(this, MessageType.ALL);		
		super.onPause();
	}
	
	@Override
	public void onResume() {
		Manager.Message.subscribe(this, MessageType.SURFACE_CREATED);
		super.onResume();
	}
	
	@Override
	public void onStop() {
		Manager.Message.unsubscribe(this, MessageType.ALL);		
		super.onStop();
	}
	

}
