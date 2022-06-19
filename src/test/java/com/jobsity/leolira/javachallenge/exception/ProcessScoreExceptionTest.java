package com.jobsity.leolira.javachallenge.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ProcessScoreExceptionTest {

	@Test
	public void itShouldReturnsExceptionMessage() {
		BowlingException printResultException = new PrintResultException("Print result exception message");

		assertThat(printResultException.getMessage()).isEqualTo("Print result exception message");
	}

	@Test
	public void itShouldReturnsPrintResultErrorGroup() {
		BowlingException printResultException = new PrintResultException("Print result exception message");

		assertThat(printResultException.getErrorGroup()).isEqualTo("Print result");
	}
}
