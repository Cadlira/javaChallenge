package com.jobsity.leolira.javachallenge;

import com.jobsity.leolira.javachallenge.exception.BowlingException;

public interface PrintResourceResult<L> {
	void print() throws BowlingException;
	L getPrintableLines() throws BowlingException;
}
