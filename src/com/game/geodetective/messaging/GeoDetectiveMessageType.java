package com.game.geodetective.messaging;

import com.game.loblib.messaging.MessageType;

public class GeoDetectiveMessageType extends MessageType {
	public final static long DESTINATION_REACHED =	1 << 19;	//data: GameEntity
	public final static long FADE_CHAIN_COMPLETE =	1 << 23;	//data: GameEntity
	public final static long PATROL_START =			1 << 24;	//data: GameEntity
}
