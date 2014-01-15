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
			s.Frames = FrameHelper._1024x64;
			break;
		case GDImage.CASE_DESCRIPTION_INSTRUCTIONS:
			s.Texture.ResourceId = R.drawable.casedescription_instructions;
			s.Frames = FrameHelper._512x64;
			break;
		case GDImage.CASE_DESCRIPTION_START_BUTTON:
			s.Texture.ResourceId = R.drawable.casedescription_background;
			s.Frames = FrameHelper._256x256;
			break;
		case GDImage.CITY_CLOCK_BORDER:
			s.Texture.ResourceId = R.drawable.city_clock_border;
			s.Frames = FrameHelper._1024x64;
			break;
		case GDImage.CITY_SCROLL_UP_OPEN:
			s.Texture.ResourceId = R.drawable.city_scroll_up_open;
			s.Frames = FrameHelper._64x64;
			break;
		case GDImage.CITY_SCROLL_UP_CLOSED:
			s.Texture.ResourceId = R.drawable.city_scroll_up_closed;
			s.Frames = FrameHelper._64x64;
			break;
		case GDImage.CITY_SCROLL_DOWN_OPEN:
			s.Texture.ResourceId = R.drawable.city_scroll_down_open;
			s.Frames = FrameHelper._64x64;
			break;
		case GDImage.CITY_SCROLL_DOWN_CLOSED:
			s.Texture.ResourceId = R.drawable.city_scroll_down_closed;
			s.Frames = FrameHelper._64x64;
			break;
		case GDImage.CITY_INFO_TEXT_BOX:
			s.Texture.ResourceId = R.drawable.city_info_textbox_placeholder;
			s.Frames = FrameHelper._512x512;
			break;
		case GDImage.CITY_TRAVEL_CENTER_BUTTON:
			s.Texture.ResourceId = R.drawable.city_travel_icon;
			s.Frames = FrameHelper._128x128;
			break;
		case GDImage.CLUE_LOCATION_MUSEUM_LABEL:
			s.Texture.ResourceId = R.drawable.city_clue_location_museum;
			s.Frames = FrameHelper._512x64;
			break;
		case GDImage.CLUE_LOCATION_HISTORICAL_SOCIETY_LABEL:
			s.Texture.ResourceId = R.drawable.city_clue_location_historical_society;
			s.Frames = FrameHelper._512x64;
			break;
		case GDImage.CLUE_LOCATION_TAVERN_LABEL:
			s.Texture.ResourceId = R.drawable.city_clue_location_tavern;
			s.Frames = FrameHelper._512x64;
			break;
		case GDImage.CLUE_LOCATION_STADIUM_LABEL:
			s.Texture.ResourceId = R.drawable.city_clue_location_stadium;
			s.Frames = FrameHelper._512x64;
			break;
		case GDImage.CLUE_LOCATION_LIBRARY_LABEL:
			s.Texture.ResourceId = R.drawable.city_clue_location_library;
			s.Frames = FrameHelper._512x64;
			break;
		case GDImage.CLUE_LOCATION_TRAVEL_CENTER_LABEL:
			s.Texture.ResourceId = R.drawable.city_clue_location_travel_center;
			s.Frames = FrameHelper._512x64;
			break;
		case GDImage.CLUE_LOCATION_COURTHOUSE_LABEL:
			s.Texture.ResourceId = R.drawable.city_clue_location_courthouse;
			s.Frames = FrameHelper._512x64;
			break;
		case GDImage.CLUE_LOCATION_BANK_LABEL:
			s.Texture.ResourceId = R.drawable.city_clue_location_bank;
			s.Frames = FrameHelper._512x64;
			break;
		case GDImage.CLUE_LOCATION_MUSEUM:
			s.Texture.ResourceId = R.drawable.cluelocation_image_placeholder;
			s.Frames = FrameHelper._512x512;
			break;
		case GDImage.CLUE_LOCATION_HISTORICAL_SOCIETY:
			s.Texture.ResourceId = R.drawable.cluelocation_image_placeholder;
			s.Frames = FrameHelper._512x512;
			break;
		case GDImage.CLUE_LOCATION_TAVERN:
			s.Texture.ResourceId = R.drawable.cluelocation_image_placeholder;
			s.Frames = FrameHelper._512x512;
			break;
		case GDImage.CLUE_LOCATION_STADIUM:
			s.Texture.ResourceId = R.drawable.cluelocation_image_placeholder;
			s.Frames = FrameHelper._512x512;
			break;
		case GDImage.CLUE_LOCATION_LIBRARY:
			s.Texture.ResourceId = R.drawable.cluelocation_image_placeholder;
			s.Frames = FrameHelper._512x512;
			break;
		case GDImage.CLUE_LOCATION_TRAVEL_CENTER:
			s.Texture.ResourceId = R.drawable.cluelocation_image_placeholder;
			s.Frames = FrameHelper._512x512;
			break;
		case GDImage.CLUE_LOCATION_COURTHOUSE:
			s.Texture.ResourceId = R.drawable.cluelocation_image_placeholder;
			s.Frames = FrameHelper._512x512;
			break;
		case GDImage.CLUE_LOCATION_BANK:
			s.Texture.ResourceId = R.drawable.cluelocation_image_placeholder;
			s.Frames = FrameHelper._512x512;
			break;
		case GDImage.CLUE_LOCATION_CLUE_TEXT_BOX:
			s.Texture.ResourceId = R.drawable.cluelocation_clue_text_box_placeholder;
			s.Frames = FrameHelper._512x512;
			break;
		case GDImage.CLUE_LOCATION_BACK_BUTTON:
			s.Texture.ResourceId = R.drawable.menu_back_button;
			s.Frames = FrameHelper._256x128;
			break;
		case GDImage.TRAVEL_WORLD:
			s.Texture.ResourceId = R.drawable.travel_world;
			s.Frames = FrameHelper._1024x512;
			break;
		case GDImage.TRAVEL_BACK_BUTTON:
			s.Texture.ResourceId = R.drawable.menu_back_button;
			s.Frames = FrameHelper._256x128;
			break;
		case GDImage.TRAVEL_FLY_BUTTON:
			s.Texture.ResourceId = R.drawable.travel_fly_button;
			s.Frames = FrameHelper.TravelButton;
			s.FrameCount = 2;
			break;
		case GDImage.CITY_IMAGE_KABUL:
			s.Texture.ResourceId = R.drawable.city_image_placeholder;
			s.Frames = FrameHelper._512x512;
			break;
		case GDImage.CITY_IMAGE_BUENOS_AIRES:
			s.Texture.ResourceId = R.drawable.city_image_placeholder;
			s.Frames = FrameHelper._512x512;
			break;
		case GDImage.CITY_IMAGE_SYDNEY:
			s.Texture.ResourceId = R.drawable.city_image_placeholder;
			s.Frames = FrameHelper._512x512;
			break;
		case GDImage.CITY_IMAGE_VIENNA:
			s.Texture.ResourceId = R.drawable.city_image_placeholder;
			s.Frames = FrameHelper._512x512;
			break;
		case GDImage.CITY_IMAGE_NASSAU:
			s.Texture.ResourceId = R.drawable.city_image_placeholder;
			s.Frames = FrameHelper._512x512;
			break;
		case GDImage.CITY_IMAGE_MANAMA:
			s.Texture.ResourceId = R.drawable.city_image_placeholder;
			s.Frames = FrameHelper._512x512;
			break;
		case GDImage.CITY_IMAGE_DHAKA:
			s.Texture.ResourceId = R.drawable.city_image_placeholder;
			s.Frames = FrameHelper._512x512;
			break;
		case GDImage.POPUP_BACKGROUND:
			s.Texture.ResourceId = R.drawable.popup_body;
			s.Frames = FrameHelper._128x128;
			break;
		default:
			super.setupSprite(s, image);
			break;
		}
		Global.View.bindTexture(s.Texture);
	}
}