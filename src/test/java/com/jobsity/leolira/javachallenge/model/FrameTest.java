package com.jobsity.leolira.javachallenge.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jobsity.leolira.javachallenge.exception.FrameException;

@ExtendWith(MockitoExtension.class)
public class FrameTest {
	@Test
	public void itThrowsAFrameExcepionWhenFrameRoundIsOutOfRangeGreaterThan10() {
		FrameException exception = assertThrows(FrameException.class, () -> new Frame(11));

		assertThat(exception.getMessage()).isEqualTo("There must be exactly 10 frames per game");
	}

	@Test
	public void itThrowsAFrameExcepionWhenFrameRoundIsOutOfRangeLessThan1() {
		FrameException exception = assertThrows(FrameException.class, () -> new Frame(0));

		assertThat(exception.getMessage()).isEqualTo("There must be exactly 10 frames per game");
	}

	@Test
	public void itGetsTheRound() throws FrameException {
		Frame frame = new Frame(2);

		assertThat(frame.getRound()).isEqualTo(2);
	}

	@Test
	public void itGetsTheNextFrame() throws FrameException {
		Frame frame = new Frame(2);
		Frame nextFrame = new Frame(3);

		frame.setNextFrame(nextFrame);

		assertThat(frame.getNextFrame()).isEqualTo(nextFrame);
	}

	@Test
	public void itIsNotTheLastFrameWhenRoundIsLessThan10() throws FrameException {
		Frame frame = new Frame(2);

		assertFalse(frame.isLastFrame());
	}

	@Test
	public void itIsTheLastFrameWhenRoundIs10() throws FrameException {
		Frame frame = new Frame(10);

		assertTrue(frame.isLastFrame());
	}

	@Test
	public void itAddsAPlayerFrame(@Mock PlayerFrame playerFrame) throws FrameException {
		Mockito.when(playerFrame.getPlayer()).thenReturn("Player name");

		Frame frame = new Frame(1);
		frame.addPlayerFrame(playerFrame);

		assertThat(frame.getPlayerFrameByPlayer("Player name")).hasValue(playerFrame);
	}

	@Test
	public void itThrowsAFrameExcepionWhenAddedFramePlayerAlreadyExists(@Mock PlayerFrame playerFrame)
			throws FrameException {
		Mockito.when(playerFrame.getPlayer()).thenReturn("Player name");

		Frame frame = new Frame(1);
		frame.addPlayerFrame(playerFrame);

		FrameException exception = assertThrows(FrameException.class, () -> frame.addPlayerFrame(playerFrame));

		assertThat(exception.getMessage()).isEqualTo("Already exists a player frame for the player Player name");
	}

	@Test
	public void itCalculatesAbsentPlayerScoreWhenPlayerFrameNotFound() throws FrameException {
		Frame frame = new Frame(1);
		PlayerScore playerScore = frame.calculatePlayerScore("Player name");

		assertNotNull(playerScore);
		assertThat(playerScore.getScore()).isEqualTo(0);
	}

	@Test
	public void itCalculatesScoreForOpenFrame(@Mock PlayerFrame playerFrame, @Mock List<Chance> chances,
			@Mock Chance chance1, @Mock Chance chance2) throws FrameException {
		List<String> pinsList = Arrays.asList("3", "5");

		Mockito.when(playerFrame.getPlayer()).thenReturn("Player name");
		Mockito.when(playerFrame.hasStrike()).thenReturn(false);
		Mockito.when(playerFrame.hasSpare()).thenReturn(false);
		Mockito.when(playerFrame.getPinsAsList()).thenReturn(pinsList);
		Mockito.when(playerFrame.getChances()).thenReturn(chances);

		Mockito.when(chances.size()).thenReturn(2);
		Mockito.when(chances.get(0)).thenReturn(chance1);
		Mockito.when(chances.get(1)).thenReturn(chance2);

		Mockito.when(chance1.getPinsAsInt()).thenReturn(3);
		Mockito.when(chance2.getPinsAsInt()).thenReturn(5);

		Frame frame = new Frame(1);
		frame.addPlayerFrame(playerFrame);

		PlayerScore playerScore = frame.calculatePlayerScore("Player name");

		assertNotNull(playerScore);
		assertThat(playerScore.getScore()).isEqualTo(8);
		assertThat(playerScore.getPinfalls()).isEqualTo(pinsList);
		assertTrue(playerScore.isOpenFrame());
	}

	@Test
	public void itCalculatesScoreForSpareFrameAndNullNextFrame(@Mock PlayerFrame playerFrame,
			@Mock List<Chance> chances) throws FrameException {
		List<String> pinsList = Arrays.asList("5", "5");

		Mockito.when(playerFrame.getPlayer()).thenReturn("Player name");
		Mockito.when(playerFrame.hasStrike()).thenReturn(false);
		Mockito.when(playerFrame.hasSpare()).thenReturn(true);
		Mockito.when(playerFrame.getPinsAsList()).thenReturn(pinsList);
		Mockito.when(playerFrame.getChances()).thenReturn(chances);
		Mockito.when(playerFrame.getTotalPins()).thenReturn(10);

		Mockito.when(chances.size()).thenReturn(2);

		Frame frame = new Frame(1);
		frame.addPlayerFrame(playerFrame);

		PlayerScore playerScore = frame.calculatePlayerScore("Player name");

		assertNotNull(playerScore);
		assertThat(playerScore.getScore()).isEqualTo(10);
		assertThat(playerScore.getPinfalls()).isEqualTo(pinsList);
		assertTrue(playerScore.isSpare());
	}
	
	@Test
	public void itCalculatesScoreForSpareFrameAndNonNullNextFrame(@Mock PlayerFrame playerFrame, @Mock PlayerFrame nextPlayerFrame,
			@Mock List<Chance> chances, @Mock List<Chance> nextChances, @Mock Chance chance1, @Mock Chance chance2) throws FrameException {
		List<String> pinsList = Arrays.asList("5", "5");

		Mockito.when(playerFrame.getPlayer()).thenReturn("Player name");
		Mockito.when(playerFrame.hasStrike()).thenReturn(false);
		Mockito.when(playerFrame.hasSpare()).thenReturn(true);
		Mockito.when(playerFrame.getPinsAsList()).thenReturn(pinsList);
		Mockito.when(playerFrame.getChances()).thenReturn(chances);
		Mockito.when(playerFrame.getTotalPins()).thenReturn(10);

		Mockito.when(chances.size()).thenReturn(2);
		
		Mockito.when(nextPlayerFrame.getPlayer()).thenReturn("Player name");
		Mockito.when(nextPlayerFrame.getChances()).thenReturn(nextChances);
		
		Mockito.when(nextChances.size()).thenReturn(2);
		Mockito.when(nextChances.get(0)).thenReturn(chance1);

		Mockito.when(chance1.getPinsAsInt()).thenReturn(2);

		Frame frame = new Frame(1);
		frame.addPlayerFrame(playerFrame);
		
		Frame nextFrame = new Frame(2);
		nextFrame.addPlayerFrame(nextPlayerFrame);

		frame.setNextFrame(nextFrame);
		
		PlayerScore playerScore = frame.calculatePlayerScore("Player name");

		assertNotNull(playerScore);
		assertThat(playerScore.getScore()).isEqualTo(12);
		assertThat(playerScore.getPinfalls()).isEqualTo(pinsList);
		assertTrue(playerScore.isSpare());
	}
	
	@Test
	public void itCalculatesScoreForStrikeFrameAndNullNextFrame(@Mock PlayerFrame playerFrame,
			@Mock List<Chance> chances) throws FrameException {
		List<String> pinsList = Arrays.asList("10");

		Mockito.when(playerFrame.getPlayer()).thenReturn("Player name");
		Mockito.when(playerFrame.hasStrike()).thenReturn(true);
		Mockito.when(playerFrame.hasSpare()).thenReturn(false);
		Mockito.when(playerFrame.getPinsAsList()).thenReturn(pinsList);
		Mockito.when(playerFrame.getChances()).thenReturn(chances);
		Mockito.when(playerFrame.getTotalPins()).thenReturn(10);

		Mockito.when(chances.size()).thenReturn(2);

		Frame frame = new Frame(1);
		frame.addPlayerFrame(playerFrame);

		PlayerScore playerScore = frame.calculatePlayerScore("Player name");

		assertNotNull(playerScore);
		assertThat(playerScore.getScore()).isEqualTo(10);
		assertThat(playerScore.getPinfalls()).isEqualTo(pinsList);
		assertTrue(playerScore.isStrike());
	}
}
