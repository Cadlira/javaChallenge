package com.jobsity.leolira.javachallenge.exception;

public class FrameException extends BowlingException {

	private static final long serialVersionUID = 8174182320408674834L;
	private static final String ERROR_GROUP = "Frame";

	public FrameException(String message) {
		super(message);
	}

	@Override
	public String getErrorGroup() {
		return ERROR_GROUP;
	}

}
