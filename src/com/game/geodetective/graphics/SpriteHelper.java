package com.game.geodetective.graphics;
import com.game.geodetective.R;
import com.game.geodetective.utility.Global;
import com.game.geodetective.utility.Logger;

public class SpriteHelper {
	protected static final StringBuffer _tag = new StringBuffer("SpriteHelper");
	
	public static void setupSprite(Sprite s, int image) {
		switch (image) {
		default:
			Logger.e(_tag, "Invalid Image");
			break;
		}
		Global.View.bindTexture(s.Texture);
	}
}
