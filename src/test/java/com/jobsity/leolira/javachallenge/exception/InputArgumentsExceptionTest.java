package com.jobsity.leolira.javachallenge.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class InputArgumentsExceptionTest {

	@Test
	public void itShouldReturnsExceptionMessage() {
		BowlingException inputArgumentsException = new InputArgumentsException("Input arguments exception message");

		assertThat(inputArgumentsException.getMessage()).isEqualTo("Input arguments exception message");
	}

	@Test
	public void itShouldReturnsInputArgumentsErrorGroup() {
		BowlingException inputArgumentsException = new InputArgumentsException("Input arguments exception message");

		assertThat(inputArgumentsException.getErrorGroup()).isEqualTo("Input arguments");
	}
}
