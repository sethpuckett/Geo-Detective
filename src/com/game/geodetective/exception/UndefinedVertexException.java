package com.game.geodetective.exception;

public class UndefinedVertexException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public UndefinedVertexException() {
	}
	
	public UndefinedVertexException(String msg) {
		super(msg);
	}
}
