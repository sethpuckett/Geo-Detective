package com.game.geodetective.graphics;

import com.game.geodetective.R;
import com.game.loblib.graphics.Sprite;
import com.game.loblib.graphics.SpriteHelper;
import com.game.loblib.utility.Global;

// Helper class for loading specific images
public class GDSpriteHelper extends SpriteHelper {

	@Override
	public void setupSprite(Sprite s, int image) {
		switch (image) {
		case GDImage.SPLASH_LOGO:
			s.Texture.ResourceId = R.drawable.splash_logo;
			s.Frames = FrameHelper._256x256;
			break;
		case GDImage.CONTINUE_BUTTON:
			s.Texture.ResourceId = R.drawable.menu_continue_button;
			s.Frames = FrameHelper._256x128;
			break;
		case GDImage.NEW_CASE_BUTTON:
			s.Texture.ResourceId = R.drawable.menu_new_game_button;
			s.Frames = FrameHelper._256x128;
			break;
		case GDImage.CREDITS_BUTTON:
			s.Texture.ResourceId = R.drawable.title_credits_button;
			s.Frames = FrameHelper.CreditsButton;
			break;
		case GDImage.CREDITS:
			s.Texture.ResourceId = R.drawable.credits_credits;
			s.Frames = FrameHelper._512x512;
			break;
		case GDImage.BLACK:
			s.Texture.ResourceId = R.drawable.common_black;
			s.Frames = FrameHelper.Color;
			break;
		case GDImage.WHITE:
			s.Texture.ResourceId = R.drawable.common_white;
			s.Frames = FrameHelper.Color;
			break;
		case GDImage.RED:
			s.Texture.ResourceId = R.drawable.common_red;
			s.Frames = FrameHelper.Color;
			break;
		case GDImage.YELLOW:
			s.Texture.ResourceId = R.drawable.common_yellow;
			s.Frames = FrameHelper.Color;
			break;
		case GDImage.ORANGE:
			s.Texture.ResourceId = R.drawable.common_orange;
			s.Frames = FrameHelper.Color;
			break;
		case GDImage.GREEN:
			s.Texture.ResourceId = R.drawable.common_green;
			s.Frames = FrameHelper.Color;
			break;
		case GDImage.BLUE:
			s.Texture.ResourceId = R.drawable.common_blue;
			s.Frames = FrameHelper.Color;
			break;
		case GDImage.TITLE_LOGO:
			s.Texture.ResourceId = R.drawable.title_maze_title;
			s.Frames = FrameHelper._512x256;
			break;
		case GDImage.CASE_DESCRIPTION_BACKGROUND:
			s.Texture.ResourceId = R.drawable.casedescription_background;
			s.Frames = FrameHelper._256x512;
			break;
		case GDImage.CASE_DESCRIPTION_NEWSPAPER:
			s.Texture.ResourceId = R.drawable.casedescription_newspaper;
			s.Frames = FrameHelper._256x256;
			break;
		case GDImage.CASE_DESCRIPTION_BORDER:
			s.Texture.ResourceId = R.drawable.casedescription_border;
			s.Frames = FrameHelper._64x64;
			break;
		case GDImage.CASE_DESCRIPTION_INSTRUCTIONS:
			s.Texture.ResourceId = R.drawable.casedescription_instructions;
			s.Frames = FrameHelper._512x64;
			break;
		case GDImage.CASE_DESCRIPTION_START_BUTTON:
			s.Texture.ResourceId = R.drawable.casedescription_background;
			s.Frames = FrameHelper._256x256;
			break;
		default:
			super.setupSprite(s, image);
			break;
		}
		Global.View.bindTexture(s.Texture);
	}
}