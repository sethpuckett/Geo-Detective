package com.game.geodetective.exception;

public class PoolExhaustedException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public PoolExhaustedException() {
	}
	
	public PoolExhaustedException(String msg) {
		super(msg);
	}
}
