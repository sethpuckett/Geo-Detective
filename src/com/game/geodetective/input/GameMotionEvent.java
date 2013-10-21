package com.game.geodetective.input;

import com.game.geodetective.utility.Global;
import com.game.geodetective.utility.android.AllocationGuard;
import com.game.geodetective.utility.area.Vertex;

public class GameMotionEvent extends AllocationGuard {
	public Vertex ScreenCoords = new Vertex();
	public Vertex CameraCoords = new Vertex();
	public int Type = MotionType.UNKNOWN;
	public boolean Primary = false;
	public int PointerId = 0;
	
	public GameMotionEvent() {
	}
	
	public void init(float x, float y, int type, boolean primary, int pointerId) {
		ScreenCoords.X = x;
		ScreenCoords.Y = Global.Renderer.Height - y;
		CameraCoords.X = ScreenCoords.X + Global.Camera.CameraArea.Position.X;
		CameraCoords.Y = ScreenCoords.Y + Global.Camera.CameraArea.Position.Y;
		Type = type;
		Primary = primary;
		PointerId = pointerId;
	}
}
