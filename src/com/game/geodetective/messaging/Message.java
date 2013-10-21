package com.game.geodetective.messaging;

public class Message {
	public long Type = MessageType.UNKNOWN;
	public boolean ReleaseData = false; // if true, data will released after message is sent
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
