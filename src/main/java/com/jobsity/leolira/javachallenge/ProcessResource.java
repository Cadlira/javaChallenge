package com.jobsity.leolira.javachallenge;

import com.jobsity.leolira.javachallenge.exception.BowlingException;

public interface ProcessResource<R> {
	void process() throws BowlingException;
	R getProcessedResult() throws BowlingException;
}
