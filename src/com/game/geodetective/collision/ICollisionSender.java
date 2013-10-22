package com.game.geodetective.collision;

import com.game.geodetective.utility.area.IArea;

// Implementers of this interface can be used to test for collisions
public interface ICollisionSender {
	long getLayers();
	IArea getArea();
}
