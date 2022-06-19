package com.jobsity.leolira.javachallenge.exception;

public class InputArgumentsException extends BowlingException {

	private static final long serialVersionUID = -4408287769226683443L;
	private static final String ERROR_GROUP = "Input arguments";

	public InputArgumentsException(String message) {
		super(message);
	}

	@Override
	public String getErrorGroup() {
		return ERROR_GROUP;
	}
}
