package com.jobsity.leolira.javachallenge.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PlayerScoreTest {

	@Test
	public void itCreatesAFailPlayerScoreForAbsentPlayer() {
		PlayerScore playerScore = PlayerScore.createAbsentPlayer("Player name");
		
		assertThat(playerScore.getPlayer()).isEqualTo("Player name");
		assertThat(playerScore.getScore()).isEqualTo(0);
		assertThat(playerScore.getPinfalls().size()).isEqualTo(2);
		assertThat(playerScore.getPinfalls().get(0)).isEqualTo("F");
		assertThat(playerScore.getPinfalls().get(1)).isEqualTo("F");
		assertTrue(playerScore.isOpenFrame());
	}
	
	@Test
	public void itAddsAPinfall() {
		PlayerScore playerScore = new PlayerScore("Player name", 5);
		playerScore.addPinFall("5");
		
		assertThat(playerScore.getPinfalls().size()).isEqualTo(1);
		assertThat(playerScore.getPinfalls().get(0)).isEqualTo("5");
	}
	
	@Test
	public void itMakesASpare() {
		PlayerScore playerScore = new PlayerScore("Player name", 5);
		playerScore.makeSpare();
		
		assertTrue(playerScore.isSpare());
		assertFalse(playerScore.isOpenFrame());
	}
	
	@Test
	public void itMakesAStrike() {
		PlayerScore playerScore = new PlayerScore("Player name", 5);
		playerScore.makeStrike();
		
		assertTrue(playerScore.isStrike());
		assertFalse(playerScore.isOpenFrame());
	}
}
