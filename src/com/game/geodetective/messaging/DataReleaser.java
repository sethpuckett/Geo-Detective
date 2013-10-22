package com.game.geodetective.messaging;

// Used to free resources when MessageData contains managed resources
public interface DataReleaser {
    // Example Use:
    //msg.Releaser = new DataReleaser() {
    //	public <T> void release(T t) {Manager.Vertex.release((Vertex)t);}
    //};
	
	<T> void release(T t);
}
