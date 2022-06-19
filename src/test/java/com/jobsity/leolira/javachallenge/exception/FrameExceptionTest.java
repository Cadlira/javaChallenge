package com.jobsity.leolira.javachallenge.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class FrameExceptionTest {

	@Test
	public void itShouldReturnsExceptionMessage() {
		BowlingException frameException = new FrameException("Frame exception message");
		
		assertThat(frameException.getMessage()).isEqualTo("Frame exception message");
	}
	
	@Test
	public void itShouldReturnsFrameErrorGroup() {
		BowlingException frameException = new FrameException("Frame exception message");
		
		assertThat(frameException.getErrorGroup()).isEqualTo("Frame");
	}
}
