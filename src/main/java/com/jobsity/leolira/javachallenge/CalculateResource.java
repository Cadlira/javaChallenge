package com.jobsity.leolira.javachallenge;

import com.jobsity.leolira.javachallenge.exception.BowlingException;

public interface CalculateResource<R, P> {
	R calculate(P param) throws BowlingException;
}
