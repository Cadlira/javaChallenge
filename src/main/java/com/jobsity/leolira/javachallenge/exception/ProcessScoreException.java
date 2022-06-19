package com.jobsity.leolira.javachallenge.exception;

public class ProcessScoreException extends BowlingException {

	private static final long serialVersionUID = -5731846738142240644L;
	private static final String ERROR_GROUP = "Process score";

	public ProcessScoreException(String message) {
		super(message);
	}

	@Override
	public String getErrorGroup() {
		return ERROR_GROUP;
	}

}
