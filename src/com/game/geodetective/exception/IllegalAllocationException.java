package com.game.geodetective.exception;

public class IllegalAllocationException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public IllegalAllocationException() {
	}
	
	public IllegalAllocationException(String msg) {
		super(msg);
	}
}