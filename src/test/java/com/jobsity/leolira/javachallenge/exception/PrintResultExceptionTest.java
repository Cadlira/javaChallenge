package com.jobsity.leolira.javachallenge.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class PrintResultExceptionTest {

	@Test
	public void itShouldReturnsExceptionMessage() {
		BowlingException processScoreException = new ProcessScoreException("Process score exception message");

		assertThat(processScoreException.getMessage()).isEqualTo("Process score exception message");
	}

	@Test
	public void itShouldReturnsProcessScoretErrorGroup() {
		BowlingException processScoreException = new ProcessScoreException("Process score exception message");

		assertThat(processScoreException.getErrorGroup()).isEqualTo("Process score");
	}
}
