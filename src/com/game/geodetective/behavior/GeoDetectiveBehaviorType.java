package com.game.geodetective.behavior;

import com.game.loblib.behavior.BehaviorType;

public class GeoDetectiveBehaviorType extends BehaviorType {
	
	//rendering
	public static final long RENDER 		= 1 << 0;
	public static final long TILE_RENDER	= 1 << 2;
	public static final long BLINK			= 1 << 6;
	public static final long DELAYED_RENDER	= 1 << 28;
	public static final long TEXT_RENDER	= 1 << 29;
	
	//movement
	public static final long TOUCH_DESTINATION = 1 << 9;
	public static final long ATTACH			= 1 << 10;
	public static final long CAMERA_DRAG 		= 1 << 12;
	public static final long MOVEMENT_MODIFIER = 1 << 13;
	public static final long PATROL_DESTINATION = 1 << 14;
	public static final long DESTINATION_MOVE = 1 << 40;
	public static final long CIRCLE_MOVE = 1 << 41;
	public static final long REVERSE_TOUCH_DESTINATION = 1 << 43;
	
	//collisions
	public static final long COLLISION_SENDER = 1 << 15;
	public static final long COLLISION_CHECK = 1 << 16;
	
	//input
	public static final long BUTTON 		= 1 << 18;
	public static final long SCREEN_DRAG	= 1 << 19;
	
	//animation
	public static final long TWEEN 			= 1 << 25;
	public static final long FADE 			= 1 << 26;
	public static final long FADE_CHAIN		= 1 << 27;
	
	//misc
	public static final long SCROLLING_TILE	= 1 << 32;
	public static final long TIMER			= 1 << 33;
	public static final long ENTITY_LINK	=  1 << 36;
}
