package com.game.geodetective.collision;

import com.game.loblib.collision.CollisionLayer;

public class GDCollisionLayer extends CollisionLayer {
	public final static long MAIN_BLOCK = 	1 << 1;
	public final static long PLAYER =		1 << 2;
	public final static long DAMAGE =		1 << 3;
	public final static long GOAL =			1 << 4;
	public final static long GAME_ITEM =	1 << 5;
}
