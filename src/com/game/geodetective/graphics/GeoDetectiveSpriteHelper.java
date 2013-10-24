package com.game.geodetective.graphics;

import com.game.loblib.graphics.Sprite;
import com.game.loblib.graphics.SpriteHelper;
import com.game.loblib.utility.Global;

// Helper class for loading specific images
public class GeoDetectiveSpriteHelper extends SpriteHelper {

	@Override
	public void setupSprite(Sprite s, int image) {
		switch (image) {
		default:
			super.setupSprite(s, image);
			break;
		}
		Global.View.bindTexture(s.Texture);
	}
}