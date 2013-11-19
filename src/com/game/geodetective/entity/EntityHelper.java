package com.game.geodetective.entity;

import com.game.geodetective.behavior.ButtonBehavior;
import com.game.geodetective.behavior.CollisionSenderBehavior;
import com.game.geodetective.behavior.DelayedRenderBehavior;
import com.game.geodetective.behavior.DestinationMoveBehavior;
import com.game.geodetective.behavior.PatrolDestinationBehavior;
import com.game.geodetective.behavior.TextRenderBehavior;
import com.game.geodetective.behavior.PatrolDestinationBehavior.PatrolType;
import com.game.geodetective.behavior.RenderBehavior;
import com.game.geodetective.behavior.ScrollingTileBehavior;
import com.game.geodetective.behavior.TileRenderBehavior;
import com.game.geodetective.behavior.TimerBehavior;
import com.game.loblib.collision.CollisionLayer;
import com.game.loblib.entity.GameEntity;
import com.game.loblib.utility.FullScreenType;
import com.game.loblib.utility.Global;
import com.game.loblib.utility.Logger;
import com.game.loblib.utility.Manager;
import com.game.loblib.utility.area.AreaType;

public class EntityHelper {
	public static final StringBuffer _tag = new StringBuffer("EntityHelper");

	public static GameEntity graphic(int image, int layer, boolean useCamera, float sizeX, float sizeY, boolean center, float positionX, float positionY) {
		GameEntity entity = new GameEntity();
		if (center) {
			entity.Attributes.Area.setCenter(positionX, positionY);
			entity.Attributes.Area.MaintainCenter = true;
		}
		else {
			entity.Attributes.Area.setPosition(positionX, positionY);
			entity.Attributes.Area.MaintainCenter = false;
		}
		entity.Attributes.Area.setSize(sizeX, sizeY); 
		entity.Attributes.Sprite = Manager.Sprite.make(image);
		entity.Attributes.Sprite.UseCamera = useCamera;
		entity.addBehavior(new RenderBehavior(layer));
		return entity;
	}
	
	public static GameEntity delayedGraphic(int image, int layer, boolean useCamera, float sizeX, float sizeY, boolean center, float positionX, float positionY, float delayTime, float fadeInTime) {
		GameEntity entity = new GameEntity();
		if (center) {
			entity.Attributes.Area.setCenter(positionX, positionY);
			entity.Attributes.Area.MaintainCenter = true;
		}
		else {
			entity.Attributes.Area.setPosition(positionX, positionY);
			entity.Attributes.Area.MaintainCenter = false;
		}
		entity.Attributes.Area.setSize(sizeX, sizeY); 
		entity.Attributes.Sprite = Manager.Sprite.make(image);
		entity.Attributes.Sprite.UseCamera = useCamera;
		entity.addBehavior(new DelayedRenderBehavior(layer, delayTime, fadeInTime));
		return entity;
	}
	
	public static GameEntity fullscreenGraphic(int image, int layer) {
		return fullscreenGraphic(image, layer, FullScreenType.FixedBoth);
	}
	
	public static GameEntity fullscreenGraphic(int image, int layer, FullScreenType type) {
		float sizeX = 0;
		float sizeY = 0;
		float posX = 0;
		float posY = 0;
		
		GameEntity entity = graphic(image, layer, false, 1, 1, false, 0, 0);
		float rawImageX = entity.Attributes.Sprite.Frames[2];
		float rawImageY = entity.Attributes.Sprite.Frames[1];
		
		switch (type) {
		case FixedBoth:
			sizeX = Global.Renderer.Width;
			sizeY = Global.Renderer.Height;
			posX = 0;
			posY = 0;
			break;
		case FixedX:
			sizeX = Global.Renderer.Width;
			sizeY = rawImageY * (sizeX / rawImageX);
			posX = 0;
			posY = - ((sizeY - Global.Renderer.Height) / 2f);
			break;
		case FixedY:
			sizeY = Global.Renderer.Height;
			sizeX = rawImageX * (sizeY / rawImageY);
			posY = 0;
			posX = - ((sizeX - Global.Renderer.Width) / 2f);
			break;
		default:
			Logger.e(_tag, "Invalid FullScreenType");
			return null;
		}
		
		entity.Attributes.Area.setSize(sizeX, sizeY);
		entity.Attributes.Area.setPosition(posX, posY);
		
		return entity;
	}
	
	public static GameEntity scrollingGraphic(int image, int layer, int direction, float speed) {
		return scrollingGraphic(image, layer, direction, speed, 0f, 0f, true);
	}
	
	public static GameEntity scrollingGraphic(int image, int layer, int direction, float speed, float startPos) {
		return scrollingGraphic(image, layer, direction, speed, startPos, 0f, true);
	}
	
	public static GameEntity scrollingGraphic(int image, int layer, int direction, float speed, float startPos, float fixedLength, boolean fixedVertical) {
		GameEntity entity = new GameEntity();
		entity.addBehavior(new ScrollingTileBehavior(image, layer, direction, speed, startPos, fixedLength, fixedVertical));
		return entity;
	}
	
	public static GameEntity tiledGraphic(int image, int layer, float tileLength, float areaPositionX, float areaPositionY, float areaLength) {
		GameEntity entity = new GameEntity();
		entity.addBehavior(new TileRenderBehavior(image, layer, tileLength, areaPositionX, areaPositionY, areaLength));
		return entity;
	}
	
	public static GameEntity button(int image, int layer, boolean useCamera, float sizeX, float sizeY, boolean center, float positionX, float positionY, AreaType areaType) {
		GameEntity entity = graphic(image, layer, useCamera, sizeX, sizeY, center, positionX, positionY);
		entity.addBehavior(new ButtonBehavior(areaType, useCamera));
		return entity;
	}
	
	public static GameEntity button(int image, int layer, boolean useCamera, float sizeX, float sizeY, boolean center, float positionX, float positionY, AreaType areaType, boolean clickOnRelease) {
		GameEntity entity = graphic(image, layer, useCamera, sizeX, sizeY, center, positionX, positionY);
		entity.addBehavior(new ButtonBehavior(areaType, useCamera, clickOnRelease));
		return entity;
	}

	public static GameEntity textButton(String textName, String text, boolean useCamera, boolean centerX, boolean centerY, float positionX, float positionY, float red, float green, float blue, float alpha) {
		GameEntity entity = text(textName, text, positionX, positionY, centerX, centerY, 0f, red, green, blue, alpha);
		entity.addBehavior(new ButtonBehavior(AreaType.Rectangle, useCamera));
		return entity;
	}
	
	public static GameEntity textButton(String textName, String text, boolean useCamera, float sizeX, float sizeY, boolean centerX, boolean centerY, float positionX, float positionY, float red, float green, float blue, float alpha, boolean clickOnRelease) {
		GameEntity entity = text(textName, text, positionX, positionY, centerX, centerY, 0f, red, green, blue, alpha);
		entity.addBehavior(new ButtonBehavior(AreaType.Rectangle, useCamera, clickOnRelease));
		return entity;
	}

	public static GameEntity timer(int timerType, float time) {
		GameEntity entity = new GameEntity();
		entity.addBehavior(new TimerBehavior(timerType, time));
		return entity;
	}
	
	public static GameEntity trigger(float x, float y, int xSize, int ySize) {
		GameEntity entity = new GameEntity();
		entity.Attributes.Area.Position.X = x;
		entity.Attributes.Area.Position.Y = y;
		entity.Attributes.Area.Size.X = xSize;
		entity.Attributes.Area.Size.Y = ySize;
		entity.addBehavior(new CollisionSenderBehavior(AreaType.Rectangle, CollisionLayer.TRIGGER));
//		if (Debug.ShowTriggers) {
//			entity.Attributes.Sprite = Manager.Sprite.make(Image.RED);
//			entity.Attributes.Sprite.Alpha = .4f;
//			entity.addBehavior(new RenderBehavior(SpriteLayer.FOREGROUND));
//		}
		return entity;
	}
	
	public static GameEntity patrolAnchor(float x, float y, float speed) {
		return patrolAnchor (x, y, speed, 0, 0);
	}
	
	public static GameEntity patrolAnchor(float x, float y, float speed, int holdTime) {
		return patrolAnchor(x, y, speed, holdTime, 0);
	}

	public static GameEntity patrolAnchor(float x, float y, float speed, int holdTime, int startOffset) {
		return patrolAnchor(x, y, speed, holdTime, startOffset, PatrolType.CONTINUOUS);
	}
	
	public static GameEntity patrolAnchor(float x, float y, float speed, int holdTime, int startOffset, int patrolType) {
		GameEntity anchor = new GameEntity();
		anchor.Attributes.Area.Position.X = x;
		anchor.Attributes.Area.Position.Y = y;
		anchor.Attributes.Speed = speed;
		anchor.addBehavior(new DestinationMoveBehavior());
		anchor.addBehavior(new PatrolDestinationBehavior(holdTime, startOffset, patrolType));
		return anchor;
	}

	public static GameEntity text(String textName, String text, float x, float y, boolean centerX, boolean centerY) {
		GameEntity entity = new GameEntity();
		entity.Attributes.Area.Position.X = x;
		entity.Attributes.Area.Position.Y = y;
		entity.addBehavior(new TextRenderBehavior(textName, text, centerX, centerY));
		return entity;
	}
	
	public static GameEntity text(String textName, String text, float x, float y, boolean centerX, boolean centerY, float width, float red, float green, float blue, float alpha) {
		GameEntity entity = new GameEntity();
		entity.Attributes.Area.Position.X = x;
		entity.Attributes.Area.Position.Y = y;
		// TODO: testing
		entity.Attributes.Area.Size.X = 1000;
		entity.Attributes.Area.Size.Y = 200;
		entity.addBehavior(new TextRenderBehavior(textName, text, centerX, centerY, width, red, green, blue, alpha));
		return entity;
	}
}
