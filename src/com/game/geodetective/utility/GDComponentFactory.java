package com.game.geodetective.utility;

import com.game.geodetective.collision.GDCollisionManager;
import com.game.geodetective.graphics.GDSpriteHelper;
import com.game.geodetective.screen.GDScreenManager;
import com.game.geodetective.sound.GDMusicHelper;
import com.game.loblib.collision.CollisionManager;
import com.game.loblib.graphics.SpriteHelper;
import com.game.loblib.screen.ScreenManager;
import com.game.loblib.sound.MusicHelper;
import com.game.loblib.utility.CommonData;
import com.game.loblib.utility.ComponentFactory;
import com.game.loblib.utility.GameSettings;
import com.game.loblib.utility.Global;

public class GDComponentFactory extends ComponentFactory {
	
	@Override
	public Global CreateGlobal() {
		return new GDGlobal();
	}
	
	@Override
	public CollisionManager CreateCollisionManager() {
		return new GDCollisionManager();
	}
	
	@Override
	public SpriteHelper CreateSpriteHelper() {
		return new GDSpriteHelper();
	}
	
	@Override
	public ScreenManager CreateScreenManager() {
		return new GDScreenManager();
	}
	
	@Override
	public MusicHelper CreateMusicHelper() {
		return new GDMusicHelper();
	}
	
	@Override
	public CommonData CreateCommonData() {
		return new GDCommonData();
	}
	
	@Override
	public GameSettings CreateGameSettings() {
		return new GDGameSettings();
	}
}
