package com.jobsity.leolira.javachallenge.exception;

public class PlayerChanceException extends BowlingException {

	private static final long serialVersionUID = -6332505750409916554L;
	private static final String ERROR_GROUP = "Player chance";

	public PlayerChanceException(String message) {
		super(message);
	}

	@Override
	public String getErrorGroup() {
		return ERROR_GROUP;
	}
}
