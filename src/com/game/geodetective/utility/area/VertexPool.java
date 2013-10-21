package com.game.geodetective.utility.area;
import com.game.geodetective.utility.android.TObjectPool;

public class VertexPool extends TObjectPool<Vertex> {
	protected StringBuffer _tag;
	
	public VertexPool(int size) {
		super(size);
		_tag = new StringBuffer("VertexPool");
		fill();
	}
	
	@Override
	protected void fill() {
		int size = getSize();
		for (int x = 0; x < size; x++) {
			getAvailable().add(new Vertex());
		}
	}
}
