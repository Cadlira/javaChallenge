package com.jobsity.leolira.javachallenge;

import com.jobsity.leolira.javachallenge.exception.BowlingException;

public interface ValidateResource<R> {
	void validate() throws BowlingException;
	R getResource() throws BowlingException;
}
