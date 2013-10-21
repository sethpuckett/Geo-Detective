package com.game.geodetective.utility.area;

public class VertexManager {
	protected VertexPool _pool;
	
	public VertexManager() {
		_pool = new VertexPool(64);
	}
	
	public Vertex allocate() {
		Vertex v = _pool.allocate();
		v.Undefined = false;
		v.X = 0;
		v.Y = 0;
		return v;
	}
	
	public Vertex allocate(float x, float y) {
		Vertex v = _pool.allocate();
		v.Undefined = false;
		v.X = x;
		v.Y = y;
		return v;
	}
	
	public void release(Vertex v) {
		_pool.release(v);
	}
}
