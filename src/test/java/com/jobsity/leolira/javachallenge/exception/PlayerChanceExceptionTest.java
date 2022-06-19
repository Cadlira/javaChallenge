package com.jobsity.leolira.javachallenge.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class PlayerChanceExceptionTest {

	@Test
	public void itShouldReturnsExceptionMessage() {
		BowlingException playerChanceException = new PlayerChanceException("Player chance exception message");

		assertThat(playerChanceException.getMessage()).isEqualTo("Player chance exception message");
	}

	@Test
	public void itShouldReturnsPlayerChanceErrorGroup() {
		BowlingException playerChanceException = new PlayerChanceException("Player chance exception message");

		assertThat(playerChanceException.getErrorGroup()).isEqualTo("Player chance");
	}
}
