package com.game.geodetective.utility.area;

import com.game.geodetective.utility.Direction;

public class RectangleCollision {
	public int CollisionCount = 0;
	public int CollisionDirection1 = Direction.UNKNOWN;
	public int CollisionDirection2 = Direction.UNKNOWN;
	public Vertex Collision1 = new Vertex(true);
	public Vertex Collision2 = new Vertex(true);
}
