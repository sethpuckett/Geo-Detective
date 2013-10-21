package com.game.geodetective.behavior;

public class BehaviorType {
	public static final long UNKNOWN 		= 0;
	public static final long ALL 			= Long.MAX_VALUE;
	
	//rendering
	public static final long RENDER 		= 1 << 0;
	public static final long RENDER_TRAIL 	= 1 << 1;
	public static final long TILE_RENDER	= 1 << 2;
	public static final long WALL_RENDER	= 1 << 3;
	public static final long PLAYER_RENDER 	= 1 << 4;
	public static final long MIMIC_RENDER	= 1 << 5;
	public static final long BLINK			= 1 << 6;
	public static final long MONSTER_RENDER	= 1 << 7;
	public static final long DELAYED_RENDER	= 1 << 28;
	
	//movement
	public static final long PLAYER_MOVE 	= 1 << 8;
	public static final long TOUCH_DESTINATION = 1 << 9;
	public static final long ATTACH			= 1 << 10;
	public static final long CAMERA_DRAG 		= 1 << 12;
	public static final long MOVEMENT_MODIFIER = 1 << 13;
	public static final long PATROL_DESTINATION = 1 << 14;
	public static final long DESTINATION_MOVE = 1 << 40;
	public static final long CIRCLE_MOVE = 1 << 41;
	public static final long DOOR_ANCHOR = 1 << 42;
	public static final long REVERSE_TOUCH_DESTINATION = 1 << 43;
	
	//collisions
	public static final long COLLISION_SENDER = 1 << 15;
	public static final long COLLISION_CHECK = 1 << 16;
	public static final long GOAL_REACH 	= 1 << 17;
	public static final long DAMAGE_CHECK	= 1 << 21;
	public static final long GAME_ITEM_CHECK = 1 << 22;
	public static final long TRIGGER_PATH = 1 << 23;
	
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
	public static final long GOAL_INDICATOR	= 1 << 34;
	public static final long GAME_ITEM	=  1 << 35;
	public static final long ENTITY_LINK	=  1 << 36;
	
	
}
