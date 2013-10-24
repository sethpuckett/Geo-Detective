package com.game.geodetective.graphics;

import com.game.loblib.utility.Logger;
import com.game.loblib.graphics.SpriteSet;

// Stores which layer draw calls should be applied to based on sprite queue index
public class GeoDetectiveSpriteSet extends SpriteSet {
	protected int[] _background1 = new int[128];
	protected int[] _background2 = new int[128];
	protected int[] _player = new int[128];
	protected int[] _mazeLow = new int[512];
	protected int[] _mazeHigh = new int[2048];
	protected int[] _foreground = new int[32];
	protected int[] _uiLow = new int[32];
	protected int[] _uiHigh = new int[32];
	protected int[] _uiTop = new int[32];
	
	protected int _background1Count = 0;
	protected int _background2Count = 0;
	protected int _playerCount = 0;
	protected int _mazeLowCount = 0;
	protected int _mazeHighCount = 0;
	protected int _foregroundCount = 0;
	protected int _uiLowCount = 0;
	protected int _uiHighCount = 0;
	protected int _uiTopCount = 0;
	
	protected final int _layerCount = 9;
	
	// add a sprite queue index to a particular layer
	public void addIndex(int index, int layer) {
		switch (layer) {
		case GeoDetectiveSpriteLayer.BACKGROUND1:
			_background1[_background1Count++] = index;
			break;
		case GeoDetectiveSpriteLayer.BACKGROUND2:
			_background2[_background2Count++] = index;
			break;
		case GeoDetectiveSpriteLayer.PLAYER:
			_player[_playerCount++] = index;
			break;
		case GeoDetectiveSpriteLayer.MAZE_LOW:
			_mazeLow[_mazeLowCount++] = index;
			break;
		case GeoDetectiveSpriteLayer.MAZE_HIGH:
			_mazeHigh[_mazeHighCount++] = index;
			break;
		case GeoDetectiveSpriteLayer.FOREGROUND:
			_foreground[_foregroundCount++] = index;
			break;
		case GeoDetectiveSpriteLayer.UI_LOW:
			_uiLow[_uiLowCount++] = index;
			break;
		case GeoDetectiveSpriteLayer.UI_HIGH:
			_uiHigh[_uiHighCount++] = index;
			break;
		case GeoDetectiveSpriteLayer.UI_TOP:
			_uiTop[_uiTopCount++] = index;
			break;
		}
	}

	// clears sprite set of all draw calls
	public void clear() {
		_background1Count = 0;
		_background2Count = 0;
		_playerCount = 0;
		_mazeLowCount = 0;
		_mazeHighCount = 0;
		_foregroundCount = 0;
		_uiLowCount = 0;
		_uiHighCount = 0;
		_uiTopCount = 0;
	}

	// returns all draw call indices for a particular layer
	public int[] getLayer(int layer) {
		switch (layer) {
		case GeoDetectiveSpriteLayer.BACKGROUND1:
			return _background1;
		case GeoDetectiveSpriteLayer.BACKGROUND2:
			return _background2;
		case GeoDetectiveSpriteLayer.PLAYER:
			return _player;
		case GeoDetectiveSpriteLayer.MAZE_LOW:
			return _mazeLow;
		case GeoDetectiveSpriteLayer.MAZE_HIGH:
			return _mazeHigh;
		case GeoDetectiveSpriteLayer.FOREGROUND:
			return _foreground;
		case GeoDetectiveSpriteLayer.UI_LOW:
			return _uiLow;
		case GeoDetectiveSpriteLayer.UI_HIGH:
			return _uiHigh;
		case GeoDetectiveSpriteLayer.UI_TOP:
			return _uiTop;
		default:
			Logger.e(_tag, "Invalid layer");
			return null;
		}
	}

	// gets the number of draw calls currently associated with a particular layer
	public int getSpriteCount(int layer) {
		switch (layer) {
		case GeoDetectiveSpriteLayer.BACKGROUND1:
			return _background1Count;
		case GeoDetectiveSpriteLayer.BACKGROUND2:
			return _background2Count;
		case GeoDetectiveSpriteLayer.PLAYER:
			return _playerCount;
		case GeoDetectiveSpriteLayer.MAZE_LOW:
			return _mazeLowCount;
		case GeoDetectiveSpriteLayer.MAZE_HIGH:
			return _mazeHighCount;
		case GeoDetectiveSpriteLayer.FOREGROUND:
			return _foregroundCount;
		case GeoDetectiveSpriteLayer.UI_LOW:
			return _uiLowCount;
		case GeoDetectiveSpriteLayer.UI_HIGH:
			return _uiHighCount;
		case GeoDetectiveSpriteLayer.UI_TOP:
			return _uiTopCount;
		default:
			Logger.e(_tag, "Invalid layer");
			return 0;
		}
	}
	
	public int getLayerCount() {
		return _layerCount;
	}

}
