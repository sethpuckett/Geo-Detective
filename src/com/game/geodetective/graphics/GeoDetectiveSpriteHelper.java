package com.game.geodetective.graphics;

import com.game.geodetective.R;
import com.game.loblib.graphics.Sprite;
import com.game.loblib.graphics.SpriteHelper;
import com.game.loblib.utility.Global;

// Helper class for loading specific images
public class GeoDetectiveSpriteHelper extends SpriteHelper {

	@Override
	public void setupSprite(Sprite s, int image) {
		switch (image) {
		case GeoDetectiveImage.SPLASH_LOGO:
			s.Texture.ResourceId = R.drawable.splash_logo;
			s.Frames = FrameHelper._256x256;
			break;
		case GeoDetectiveImage.NEW_GAME_BUTTON:
			s.Texture.ResourceId = R.drawable.menu_new_game_button;
			s.Frames = FrameHelper._256x128;
			break;
		case GeoDetectiveImage.CONTINUE_BUTTON:
			s.Texture.ResourceId = R.drawable.menu_continue_button;
			s.Frames = FrameHelper._256x128;
			break;
		case GeoDetectiveImage.CREDITS_BUTTON:
			s.Texture.ResourceId = R.drawable.title_credits_button;
			s.Frames = FrameHelper.CreditsButton;
			break;
		case GeoDetectiveImage.BACK_BUTTON:
			s.Texture.ResourceId = R.drawable.menu_back_button;
			s.Frames = FrameHelper._256x128;
			break;
		case GeoDetectiveImage.CREDITS:
			s.Texture.ResourceId = R.drawable.credits_credits;
			s.Frames = FrameHelper._512x512;
			break;
		case GeoDetectiveImage.SOUND_BUTTON:
			s.Texture.ResourceId = R.drawable.menu_sound_button;
			s.Frames = FrameHelper.ToggleButton;
			s.FrameCount = 2;
			break;
		case GeoDetectiveImage.BLACK:
			s.Texture.ResourceId = R.drawable.common_black;
			s.Frames = FrameHelper.Color;
			break;
		case GeoDetectiveImage.WHITE:
			s.Texture.ResourceId = R.drawable.common_white;
			s.Frames = FrameHelper.Color;
			break;
		case GeoDetectiveImage.RED:
			s.Texture.ResourceId = R.drawable.common_red;
			s.Frames = FrameHelper.Color;
			break;
		case GeoDetectiveImage.YELLOW:
			s.Texture.ResourceId = R.drawable.common_yellow;
			s.Frames = FrameHelper.Color;
			break;
		case GeoDetectiveImage.ORANGE:
			s.Texture.ResourceId = R.drawable.common_orange;
			s.Frames = FrameHelper.Color;
			break;
		case GeoDetectiveImage.GREEN:
			s.Texture.ResourceId = R.drawable.common_green;
			s.Frames = FrameHelper.Color;
			break;
		case GeoDetectiveImage.BLUE:
			s.Texture.ResourceId = R.drawable.common_blue;
			s.Frames = FrameHelper.Color;
			break;
		case GeoDetectiveImage.TITLE_LOGO:
			s.Texture.ResourceId = R.drawable.title_maze_title;
			s.Frames = FrameHelper._512x256;
			break;
		case GeoDetectiveImage.SCROLLING_STONE_WALL:
			s.Texture.ResourceId = R.drawable.menu_dark_stone_h_tile;
			s.Frames = FrameHelper.MenuStoneTile;
			break;
		default:
			super.setupSprite(s, image);
			break;
		}
		Global.View.bindTexture(s.Texture);
	}
}