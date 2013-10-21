package com.game.geodetective.graphics;

import java.util.Comparator;

public class TextureComparator implements Comparator<Texture> {
	@Override
	public int compare(Texture t1, Texture t2) {
		return t2.ResourceId - t1.ResourceId;
	}
}
