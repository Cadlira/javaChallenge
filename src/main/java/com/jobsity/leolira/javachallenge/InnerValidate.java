package com.jobsity.leolira.javachallenge;

import com.jobsity.leolira.javachallenge.exception.BowlingException;

public interface InnerValidate<P> {
	void validate(P param) throws BowlingException;
}
