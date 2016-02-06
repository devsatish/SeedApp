package com.project.exception;

public abstract class AbstractException extends RuntimeException {
	private static final long serialVersionUID = 2268598947312464025L;	
	public AbstractException(String errorMessage) {
		super(errorMessage);
	}
}
