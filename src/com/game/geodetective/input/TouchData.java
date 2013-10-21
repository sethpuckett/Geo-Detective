package com.game.geodetective.input;

import com.game.geodetective.utility.android.AllocationGuard;
import com.game.geodetective.utility.area.Area;
import com.game.geodetective.utility.area.AreaType;
import com.game.geodetective.utility.area.IArea;

public class TouchData extends AllocationGuard {
	
	protected final ITouchListener _listener;
	public final IArea TouchArea;
	public final boolean UseCamera;
	public final boolean PrimaryTouchOnly;
	public final int MotionTypes;
	
	public TouchData(ITouchListener listener, AreaType type, boolean useCamera, boolean maintainCenter, boolean primaryTouchOnly, int motionTypes) {
		_listener = listener;
		TouchArea = Area.allocate(type, maintainCenter);
		UseCamera = useCamera;
		PrimaryTouchOnly = primaryTouchOnly;
		MotionTypes = motionTypes;
	}
	
	public ITouchListener getListener() {
		return _listener;
	}
}
