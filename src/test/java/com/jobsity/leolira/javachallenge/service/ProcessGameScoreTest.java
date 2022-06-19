package com.jobsity.leolira.javachallenge.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jobsity.leolira.javachallenge.ValidateResource;
import com.jobsity.leolira.javachallenge.exception.BowlingException;
import com.jobsity.leolira.javachallenge.exception.ProcessScoreException;
import com.jobsity.leolira.javachallenge.model.Frame;
import com.jobsity.leolira.javachallenge.model.PlayerChance;
import com.jobsity.leolira.javachallenge.model.PlayerScore;

@ExtendWith(MockitoExtension.class)
public class ProcessGameScoreTest {

	@Test
	public void itProcessGameScoreSuccess(@Mock ValidateResource<List<Frame>> framesValidate,
			@Mock ValidateResource<List<PlayerChance>> playerChanceValidate, @Mock Frame frame,
			@Mock PlayerScore playerScore1, @Mock PlayerScore playerScore2) throws BowlingException {

		List<PlayerChance> chances = Arrays.asList(new PlayerChance("Player1", "5"), new PlayerChance("Player2", "5"));
		List<Frame> frames = Arrays.asList(frame);

		Mockito.when(playerChanceValidate.getResource()).thenReturn(chances);
		Mockito.when(framesValidate.getResource()).thenReturn(frames);
		Mockito.when(frame.getRound()).thenReturn(1);
		Mockito.when(frame.calculatePlayerScore("Player1")).thenReturn(playerScore1);
		Mockito.when(frame.calculatePlayerScore("Player2")).thenReturn(playerScore2);

		ProcessGameScore processGameScore = new ProcessGameScore(framesValidate, playerChanceValidate);

		assertDoesNotThrow(() -> processGameScore.process());
	}

	@Test
	public void itGetsProcessResult(@Mock ValidateResource<List<Frame>> framesValidate,
			@Mock ValidateResource<List<PlayerChance>> playerChanceValidate, @Mock Frame frame,
			@Mock PlayerScore playerScore1, @Mock PlayerScore playerScore2) throws BowlingException {

		List<PlayerChance> chances = Arrays.asList(new PlayerChance("Player1", "5"), new PlayerChance("Player2", "5"));
		List<Frame> frames = Arrays.asList(frame);

		Mockito.when(playerChanceValidate.getResource()).thenReturn(chances);
		Mockito.when(framesValidate.getResource()).thenReturn(frames);
		Mockito.when(frame.getRound()).thenReturn(1);
		Mockito.when(frame.calculatePlayerScore("Player1")).thenReturn(playerScore1);
		Mockito.when(frame.calculatePlayerScore("Player2")).thenReturn(playerScore2);

		ProcessGameScore processGameScore = new ProcessGameScore(framesValidate, playerChanceValidate);

		processGameScore.process();

		assertThat(processGameScore.getProcessedResult().size()).isEqualTo(1);
		assertThat(processGameScore.getProcessedResult().get(0).getPlayerScores().size()).isEqualTo(2);
	}

	@Test
	public void itThrowsProcessScoreExceptionWhenResultIsNull(@Mock ValidateResource<List<Frame>> framesValidate,
			@Mock ValidateResource<List<PlayerChance>> playerChanceValidate) throws BowlingException {

		ProcessGameScore processGameScore = new ProcessGameScore(framesValidate, playerChanceValidate);

		ProcessScoreException exception = assertThrows(ProcessScoreException.class, () -> processGameScore.getProcessedResult());
		
		assertThat(exception.getMessage()).isEqualTo("The game score has not yet been processed");
	}
}
