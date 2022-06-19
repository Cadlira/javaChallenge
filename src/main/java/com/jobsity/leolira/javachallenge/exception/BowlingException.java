package com.jobsity.leolira.javachallenge.exception;

public abstract class BowlingException extends Exception {
	private static final long serialVersionUID = -9178727900853797128L;

	public BowlingException(String message) {
		super(message);
	}

	public BowlingException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public abstract String getErrorGroup();
}
