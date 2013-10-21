package com.game.geodetective.entity;

import com.game.geodetective.graphics.Sprite;
import com.game.geodetective.utility.area.Rectangle;
import com.game.geodetective.utility.area.Vertex;

public class EntityAttributes {
	// Orientation
	public final Rectangle Area = new Rectangle();
	
	// Movement/Physics
	public final Vertex Destination = new Vertex();
	public final Vertex Direction = new Vertex();
	public float Speed = 0f;
	
	// Drawing
	public Sprite Sprite = null;
}
