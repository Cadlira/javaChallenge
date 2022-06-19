package com.jobsity.leolira.javachallenge.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FrameScoreTest {

	@Test
	public void itGetsTheFrameRound() {
		FrameScore frameScore = new FrameScore(1);
		
		assertThat(frameScore.getFrameRound()).isEqualTo(1);
	}
	
	@Test
	public void itAddsAPlayerScore(@Mock PlayerScore playerScore) {
		FrameScore frameScore = new FrameScore(1);
		frameScore.addPlayerScore(playerScore);
		
		assertNotNull(frameScore.getPlayerScores());
		assertThat(frameScore.getPlayerScores().size()).isEqualTo(1);
		assertThat(frameScore.getPlayerScores().get(0)).isEqualTo(playerScore);
	}
}
