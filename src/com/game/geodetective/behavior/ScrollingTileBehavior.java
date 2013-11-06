package com.game.geodetective.behavior;

import com.game.loblib.behavior.Behavior;
import com.game.loblib.graphics.Sprite;
import com.game.loblib.utility.Direction;
import com.game.loblib.utility.Global;
import com.game.loblib.utility.Manager;
import com.game.loblib.utility.MathHelper;
import com.game.loblib.utility.android.FixedSizeArray;
import com.game.loblib.utility.area.Vertex;

public class ScrollingTileBehavior extends Behavior {

	protected int _imageId = 0;
	protected int _direction;
	protected float _scrollSpeed = .5f;
	protected int _layer;
	protected float _startPos;
	
	float _fixedLength;
	boolean _fixedVertical;
	
	protected FixedSizeArray<Sprite> _sprites;
	protected int _currentSpriteIndex;
	protected Vertex _imageSize;
	
	public ScrollingTileBehavior(int image, int layer, int direction, float scrollSpeed) {
		_type = GDBehaviorType.SCROLLING_TILE;
		
		_imageId = image;
		_imageSize = new Vertex();
		_direction = direction;
		_scrollSpeed = scrollSpeed;
		_layer = layer;
		_startPos = 0;
		_fixedLength = 0f;
	}
	
	public ScrollingTileBehavior(int image, int layer, int direction, float scrollSpeed, float startPos) {
		_type = GDBehaviorType.SCROLLING_TILE;
		
		_imageId = image;
		_imageSize = new Vertex();
		_direction = direction;
		_scrollSpeed = scrollSpeed;
		_layer = layer;
		_startPos = startPos;
	}
	
	public ScrollingTileBehavior(int image, int layer, int direction, float scrollSpeed, float startPos, float fixedLength, boolean fixedVertical) {
		_type = GDBehaviorType.SCROLLING_TILE;
		
		_imageId = image;
		_imageSize = new Vertex();
		_direction = direction;
		_scrollSpeed = scrollSpeed;
		_layer = layer;
		_startPos = startPos;
		_fixedLength = fixedLength;
		_fixedVertical = fixedVertical;
	}
	
	public float getSpritePosition() {
		return _sprites.get(_currentSpriteIndex).Area.Position.X;
	}
	
	public void setSpritePosition(float position) {
		_currentSpriteIndex = 0;
		_sprites.get(0).Area.setPosition(position, 0);
		
		switch (_direction) {
		case Direction.LEFT:
			_sprites.get(0).Area.setPosition(position, 0);
			_sprites.get(1).Area.setPosition(position + _imageSize.X, 0);
			break;
		case Direction.RIGHT:
			_sprites.get(0).Area.setPosition(position, 0);
			_sprites.get(1).Area.setPosition(position - _imageSize.X, 0);
			break;
		case Direction.DOWN:
			_sprites.get(0).Area.setPosition(0, position);
			_sprites.get(1).Area.setPosition(0, position + _imageSize.Y);
			break;
		case Direction.UP:
			_sprites.get(0).Area.setPosition(0, position);
			_sprites.get(1).Area.setPosition(0, position - _imageSize.Y);
			break;
		}
	}
	
	public void setDirection(int direction) {
		_direction = direction;
		resetPosition();
	}
	
	public void setAlpha(float alpha) {
		int count = _sprites.getCount();
		for (int i = 0; i < count; i++) {
			_sprites.get(i).Alpha = alpha;
		}
	}
	
	public FixedSizeArray<Sprite> getSprites() {
		return _sprites;
	}
	
	@Override 
	public void init() {
		_currentSpriteIndex = 0;
		
		_sprites = new FixedSizeArray<Sprite>(2);
		_sprites.add(Manager.Sprite.make(_imageId));
		
		if (MathHelper.floatEq(_fixedLength, 0f))
			_imageSize.setPosition(_sprites.get(0).Frames[2], _sprites.get(0).Frames[1]);
		else {
			if (_fixedVertical) {
				float ratio = _fixedLength / _sprites.get(0).Frames[1];
				_imageSize.setPosition(_sprites.get(0).Frames[2] * ratio, _fixedLength);
			}
			else {
				float ratio = _fixedLength / _sprites.get(0).Frames[2];
				_imageSize.setPosition(_fixedLength, _sprites.get(0).Frames[1] * ratio);
			}
		}
		
		_sprites.get(0).UseCamera = false;
		_sprites.get(0).Area.MaintainCenter = false;
		_sprites.get(0).Area.setSize(_imageSize);
		
		_sprites.add(Manager.Sprite.make(_imageId));
		_sprites.get(1).UseCamera = false;
		_sprites.get(1).Area.MaintainCenter = false;
		_sprites.get(1).Area.setSize(_imageSize);
		
		resetPosition();
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
	protected void onUpdate(float updateRatio) {
		float totalMovement = _scrollSpeed * updateRatio;
		
		switch (_direction) {
		case Direction.LEFT:
			_sprites.get(0).Area.changePosition(-totalMovement, 0);
			_sprites.get(1).Area.changePosition(-totalMovement, 0);
			break;
		case Direction.RIGHT:
			_sprites.get(0).Area.changePosition(totalMovement, 0);
			_sprites.get(1).Area.changePosition(totalMovement, 0);
			break;
		case Direction.DOWN:
			_sprites.get(0).Area.changePosition(0, -totalMovement);
			_sprites.get(1).Area.changePosition(0, -totalMovement);
			break;
		case Direction.UP:
			_sprites.get(0).Area.changePosition(0, totalMovement);
			_sprites.get(1).Area.changePosition(0, totalMovement);
			break;
		}
		
		Sprite sprite = _sprites.get(_currentSpriteIndex);
		switch (_direction) {
		case Direction.LEFT:
			if (sprite.Area.Position.X + sprite.Area.Size.X < 0) {
				sprite.Area.changePosition(_imageSize.X * 2f, 0);
				_currentSpriteIndex = ~_currentSpriteIndex & 1;
			}
			break;
		case Direction.RIGHT:
			if (sprite.Area.Position.X > Global.Renderer.Width) {
				sprite.Area.changePosition(-_imageSize.X * 2f, 0);
				_currentSpriteIndex = ~_currentSpriteIndex & 1;
			}
			break;
		case Direction.DOWN:
			if (sprite.Area.Position.Y + sprite.Area.Size.Y < 0) {
				sprite.Area.changePosition(0, _imageSize.Y * 2f);
				_currentSpriteIndex = ~_currentSpriteIndex & 1;
			}
			break;
		case Direction.UP:
			if (sprite.Area.Position.Y > Global.Renderer.Height) {
				sprite.Area.changePosition(0, -_imageSize.Y * 2f);
				_currentSpriteIndex = ~_currentSpriteIndex & 1;
			}
			break;
		}
		
		Manager.Sprite.draw(_sprites.get(0), _layer);
		Manager.Sprite.draw(_sprites.get(1), _layer);
	}
	
	@Override
	protected void setTag() {
		_tag.setLength(0);
		_tag.append(_entity.getTag());
		_tag.append(": ButtonBehavior");
	}

	protected void resetPosition() {
		switch (_direction) {
		case Direction.LEFT:
			_sprites.get(0).Area.setPosition(_startPos, 0);
			_sprites.get(1).Area.setPosition(_startPos + _imageSize.X, 0);
			break;
		case Direction.RIGHT:
			_sprites.get(0).Area.setPosition(_startPos, 0);
			_sprites.get(1).Area.setPosition(_startPos - _imageSize.X, 0);
			break;
		case Direction.DOWN:
			_sprites.get(0).Area.setPosition(0, _startPos);
			_sprites.get(1).Area.setPosition(0, _startPos + _imageSize.Y);
			break;
		case Direction.UP:
			_sprites.get(0).Area.setPosition(0, _startPos);
			_sprites.get(1).Area.setPosition(0, _startPos - _imageSize.Y);
			break;
		}
	}


}
