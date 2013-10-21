package com.game.geodetective.collision;

import com.game.geodetective.utility.area.IArea;

public interface ICollisionSender {
	long getLayers();
	IArea getArea();
}
