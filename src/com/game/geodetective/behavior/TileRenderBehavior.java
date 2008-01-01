package com.game.geodetective.behavior;

import com.game.loblib.behavior.Behavior;
import com.game.loblib.graphics.Sprite;
import com.game.loblib.utility.Logger;
import com.game.loblib.utility.Manager;
import com.game.loblib.utility.android.FixedSizeArray;

public class TileRenderBehavior extends Behavior {

	protected FixedSizeArray<Sprite> _sprites;
	protected int _layer;
	
	public TileRenderBehavior(int image, int layer, float tileLength, float areaPositionX, float areaPositionY, float areaLength) {
		_type = GeoDetectiveBehaviorType.TILE_RENDER;
		_layer = layer;
		
		if (areaLength % tileLength != 0) {
			Logger.e(_tag, "areaLength must be divisible by tileLength");
			return;
		}
		
		int tileRowCount = (int)(areaLength / tileLength);
		int tileCount = tileRowCount * tileRowCount;
		
		float xPos = areaPositionX;
		float yPos = areaPositionY;
		_sprites = new FixedSizeArray<Sprite>(tileCount);
		for (int i = 0; i < tileCount; i++) {
			Sprite sprite = Manager.Sprite.make(image);
			sprite.Area.MaintainCenter = false;
			sprite.Area.setPosition(xPos, yPos);
			sprite.Area.setSize(tileLength + 1, tileLength + 1);
			_sprites.add(sprite);
			
			xPos += tileLength;
			if (xPos >= areaPositionX + areaLength) {
				xPos = areaPositionX;
				yPos += tileLength;
			}
		}
	}
	
	@Override
	protected void onUpdate(float updateRatio) {
		int count = _sprites.getCount();
		for (int i = 0; i < count; i++)
			Manager.Sprite.draw(_sprites.get(i), _layer);
	}

	@Override
	protected void onDestroy() {
		int count = _sprites.getCount();
		for (int i = 0; i < count; i++) {
			Manager.Sprite.release(_sprites.get(i));
		}
		_sprites = null;
	}
	
	@Override
	protected void setTag() {
		_tag.setLength(0);
		_tag.append(_entity.getTag());
		_tag.append(": TileRenderBehavior");
	}

}
