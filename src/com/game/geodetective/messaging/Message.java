package com.game.geodetective.messaging;

import com.game.geodetective.messaging.DataReleaser;
import com.game.geodetective.messaging.MessageData;
import com.game.geodetective.messaging.MessageType;

public class Message {
	public long Type = MessageType.UNKNOWN;
	public boolean ReleaseData = false; // if true, data will released after message is sent
	// If ReleaseData is set to false and MessageData contains a managed resource a DataReleaser should be defined to free it when no longer needed
	public DataReleaser Releaser = null;
	
	protected MessageData<?> _messageData;

	public Message() {
	}

	public <T> void setData(T data) {
		_messageData = new MessageData<T>(data);
	}

	@SuppressWarnings("unchecked")
	public <T> T getData() {
		return (T)_messageData.getData();
	}
}