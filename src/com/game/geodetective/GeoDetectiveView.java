package com.game.geodetective;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.game.geodetective.messaging.IMessageHandler;
import com.game.geodetective.graphics.Texture;
import com.game.geodetective.messaging.Message;
import com.game.geodetective.messaging.MessageType;
import com.game.geodetective.utility.Global;
import com.game.geodetective.utility.Manager;
import com.game.geodetective.utility.android.FixedSizeArray;

public class GeoDetectiveView extends GLSurfaceView implements IMessageHandler {
	protected FixedSizeArray<Texture> _unloadedTextures = new FixedSizeArray<Texture>(256);

	public GeoDetectiveView(Context context) {
		super(context);
		//setDebugFlags(DEBUG_CHECK_GL_ERROR | DEBUG_LOG_GL_CALLS);
	}
	
	public void init() {
		Manager.Message.subscribe(this, MessageType.SURFACE_CREATED);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return Global.Game.onTouchEvent(event);
	}

	public void bindTexture(final Texture tex) {
		if (Global.Renderer.isSurfaceCreated()) {
			queueEvent(new Runnable() {
				public void run() {
					Global.Renderer.bindTexture(tex);
				}});
		}
		else
			_unloadedTextures.add(tex);
	}
	
	@Override
	public void handleMessage(Message message) {
		if (message.Type == MessageType.SURFACE_CREATED)
			loadTextures();
	}
	
	// loads textures for all sprites in _unloadedSprites
	protected void loadTextures() {		
		int count = _unloadedTextures.getCount();
		for (int i = 0; i < count; i++) {
			bindTexture(_unloadedTextures.get(i));
		}
		_unloadedTextures.clear();
	}
}
