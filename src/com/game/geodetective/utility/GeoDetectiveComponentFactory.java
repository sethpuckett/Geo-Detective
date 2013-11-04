package com.game.geodetective.utility;

import com.game.geodetective.collision.GeoDetectiveCollisionManager;
import com.game.geodetective.graphics.GeoDetectiveSpriteHelper;
import com.game.geodetective.screen.GeoDetectiveScreenManager;
import com.game.geodetective.sound.GeoDetectiveMusicHelper;
import com.game.loblib.collision.CollisionManager;
import com.game.loblib.graphics.SpriteHelper;
import com.game.loblib.screen.ScreenManager;
import com.game.loblib.sound.MusicHelper;
import com.game.loblib.utility.CommonData;
import com.game.loblib.utility.ComponentFactory;
import com.game.loblib.utility.GameSettings;
import com.game.loblib.utility.Global;

public class GeoDetectiveComponentFactory extends ComponentFactory {
	
	@Override
	public Global CreateGlobal() {
		return new GeoDetectiveGlobal();
	}
	
	@Override
	public CollisionManager CreateCollisionManager() {
		return new GeoDetectiveCollisionManager();
	}
	
	@Override
	public SpriteHelper CreateSpriteHelper() {
		return new GeoDetectiveSpriteHelper();
	}
	
	@Override
	public ScreenManager CreateScreenManager() {
		return new GeoDetectiveScreenManager();
	}
	
	@Override
	public MusicHelper CreateMusicHelper() {
		return new GeoDetectiveMusicHelper();
	}
	
	@Override
	public CommonData CreateCommonData() {
		return new GeoDetectiveCommonData();
	}
	
	@Override
	public GameSettings CreateGameSettings() {
		return new GeoDetectiveGameSettings();
	}
}
