package com.game.geodetective.screen;

import com.game.geodetective.entity.EntityHelper;
import com.game.geodetective.graphics.GeoDetectiveImage;
import com.game.geodetective.graphics.GeoDetectiveSpriteLayer;
import com.game.loblib.screen.Screen;
import com.game.loblib.utility.Global;

public class OptionsScreen extends Screen {
	
	protected float _elapsedTime = 0;
	
	public OptionsScreen() {
		_type = GeoDetectiveScreenType.OPTIONS;
	}
	
	@Override
	public void update(float updateRatio) {
		_elapsedTime += (updateRatio / 60f);
		if (_elapsedTime > 2f)
			_code = GeoDetectiveScreenCode.TRANSITION_TITLE;
	}
	
	@Override
	public void onInit() {
		_entities.add(EntityHelper.graphic(GeoDetectiveImage.SPLASH_LOGO, GeoDetectiveSpriteLayer.UI_LOW, false, Global.Renderer.Width * .75f,
				Global.Renderer.Width * .75f, true, Global.Renderer.Width / 2, Global.Renderer.Height / 2));
		_entities.add(EntityHelper.graphic(GeoDetectiveImage.WHITE, GeoDetectiveSpriteLayer.BACKGROUND1, false, 
				Global.Renderer.Width, Global.Renderer.Height, false, 0, 0));
	}

	@Override
	public void onPause() {
		// Nothing to do
	}

	@Override
	public void onUnpause() {
		// Nothing to do
	}

	@Override
	public void onClose() {
		// Nothing to do
	}
}
