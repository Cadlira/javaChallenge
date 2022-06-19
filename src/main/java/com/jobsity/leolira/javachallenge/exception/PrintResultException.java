package com.jobsity.leolira.javachallenge.exception;

public class PrintResultException extends BowlingException {

	private static final long serialVersionUID = 617500296144920486L;
	private static final String ERROR_GROUP = "Print result";

	public PrintResultException(String message) {
		super(message);
	}

	@Override
	public String getErrorGroup() {
		return ERROR_GROUP;
	}

}
