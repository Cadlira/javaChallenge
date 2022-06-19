package com.jobsity.leolira.javachallenge.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jobsity.leolira.javachallenge.exception.FrameException;

@ExtendWith(MockitoExtension.class)
public class PlayerFrameTest {

	@Test
	public void itThrowsAFrameExcepionWhenFrameRoundIsOutOfRangeGreaterThan10() {
		FrameException exception = assertThrows(FrameException.class, () -> new PlayerFrame("Player name", 11));
		
		assertThat(exception.getMessage()).isEqualTo("There must be exactly 10 frames per game");
	}
	
	@Test
	public void itThrowsAFrameExcepionWhenFrameRoundIsOutOfRangeLessThan1() {
		FrameException exception = assertThrows(FrameException.class, () -> new PlayerFrame("Player name", 0));
		
		assertThat(exception.getMessage()).isEqualTo("There must be exactly 10 frames per game");
	}
	
	@Test
	public void itGetsPlayer() throws FrameException {
		PlayerFrame playerFrame = new PlayerFrame("Player name", 1);
		
		assertThat(playerFrame.getPlayer()).isEqualTo("Player name");
	}
	
	@Test
	public void itAddsAValidChance(@Mock Chance chance) throws FrameException {
		PlayerFrame playerFrame = new PlayerFrame("Player name", 1);
		playerFrame.addChance(chance);
		
		assertThat(playerFrame.getChances().size()).isEqualTo(1);
		assertThat(playerFrame.getChances().get(0)).isEqualTo(chance);
	}
	
	@Test
	public void itThrowsAFrameExcepionWhenItIsCompletedAndAChanceIsAdded(@Mock Chance chance1, @Mock Chance chance2, @Mock Chance chance3) throws FrameException {
		PlayerFrame playerFrame = new PlayerFrame("Player name", 1);
		playerFrame.addChance(chance1);
		playerFrame.addChance(chance2);
		
		FrameException exception = assertThrows(FrameException.class, () -> playerFrame.addChance(chance3));
		
		assertThat(exception.getMessage()).isEqualTo("The frame is already completed for player 'Player name'");
	}
	
	@Test
	public void itHasAStpareWhenPinfallsSumAre10(@Mock Chance chance1, @Mock Chance chance2) throws FrameException {
		Mockito.when(chance1.getPinsAsInt()).thenReturn(5);
		Mockito.when(chance2.getPinsAsInt()).thenReturn(5);
		
		PlayerFrame playerFrame = new PlayerFrame("Player name", 1);
		playerFrame.addChance(chance1);
		playerFrame.addChance(chance2);
		
		assertTrue(playerFrame.hasSpare());
	}
	
	@Test
	public void itHasNotAStpareWhenPinfallsSumAre10(@Mock Chance chance1, @Mock Chance chance2) throws FrameException {
		Mockito.when(chance1.getPinsAsInt()).thenReturn(5);
		Mockito.when(chance2.getPinsAsInt()).thenReturn(2);
		
		PlayerFrame playerFrame = new PlayerFrame("Player name", 1);
		playerFrame.addChance(chance1);
		playerFrame.addChance(chance2);
		
		assertFalse(playerFrame.hasSpare());
	}
	
	@Test
	public void itHasAStrikeWhenChanceAddedHas10Pinfalls(@Mock Chance chance) throws FrameException {
		Mockito.when(chance.getPinsAsInt()).thenReturn(10);
		
		PlayerFrame playerFrame = new PlayerFrame("Player name", 1);
		playerFrame.addChance(chance);
		
		assertTrue(playerFrame.hasStrike());
	}
	
	@Test
	public void itHasAStrikeWhenFirstChanceAddedHas10PinfallsInFinalFrame(@Mock Chance chance1, @Mock Chance chance2, @Mock Chance chance3) throws FrameException {
		Mockito.when(chance1.getPinsAsInt()).thenReturn(10);		
		
		PlayerFrame playerFrame = new PlayerFrame("Player name", 10);
		playerFrame.addChance(chance1);
		playerFrame.addChance(chance2);
		playerFrame.addChance(chance3);
		
		assertTrue(playerFrame.hasStrike());
	}
	
	@Test
	public void itHasAStrikeWhenLastChanceAddedHas10PinfallsInASpareFinalFrame(@Mock Chance chance1, @Mock Chance chance2, @Mock Chance chance3) throws FrameException {
		Mockito.when(chance1.getPinsAsInt()).thenReturn(5);
		Mockito.when(chance2.getPinsAsInt()).thenReturn(5);
		Mockito.when(chance3.getPinsAsInt()).thenReturn(10);
		
		PlayerFrame playerFrame = new PlayerFrame("Player name", 10);
		playerFrame.addChance(chance1);
		playerFrame.addChance(chance2);
		playerFrame.addChance(chance3);
		
		assertTrue(playerFrame.hasStrike());
	}
	
	@Test
	public void itHasNotAStrikeWhenChanceAddedHasLessThen10Pinfalls(@Mock Chance chance) throws FrameException {
		Mockito.when(chance.getPinsAsInt()).thenReturn(9);
		
		PlayerFrame playerFrame = new PlayerFrame("Player name", 1);
		playerFrame.addChance(chance);
		
		assertFalse(playerFrame.hasStrike());
	}
	
	@Test
	public void itHasNotAStrikeWhenLastChanceAddedHasLessThen10PinfallsInASpareFinalFrame(@Mock Chance chance1, @Mock Chance chance2, @Mock Chance chance3) throws FrameException {
		Mockito.when(chance1.getPinsAsInt()).thenReturn(5);
		Mockito.when(chance2.getPinsAsInt()).thenReturn(5);
		Mockito.when(chance3.getPinsAsInt()).thenReturn(1);
		
		PlayerFrame playerFrame = new PlayerFrame("Player name", 10);
		playerFrame.addChance(chance1);
		playerFrame.addChance(chance2);
		playerFrame.addChance(chance3);
		
		assertFalse(playerFrame.hasStrike());
	}
	
	@Test
	public void itReturnsChancePinsAsList(@Mock Chance chance1, @Mock Chance chance2) throws FrameException {
		Mockito.when(chance1.getPins()).thenReturn("5");
		Mockito.when(chance2.getPins()).thenReturn("F");
		
		PlayerFrame playerFrame = new PlayerFrame("Player name", 2);
		playerFrame.addChance(chance1);
		playerFrame.addChance(chance2);
		
		assertThat(playerFrame.getPinsAsList().size()).isEqualTo(2);
		assertThat(playerFrame.getPinsAsList().get(0)).isEqualTo("5");
		assertThat(playerFrame.getPinsAsList().get(1)).isEqualTo("F");
	}
	
	@Test
	public void itReturnsPinsSum(@Mock Chance chance1, @Mock Chance chance2, @Mock Chance chance3) throws FrameException {
		Mockito.when(chance1.getPinsAsInt()).thenReturn(10);
		Mockito.when(chance2.getPinsAsInt()).thenReturn(5);
		Mockito.when(chance3.getPinsAsInt()).thenReturn(2);
		
		PlayerFrame playerFrame = new PlayerFrame("Player name", 10);
		playerFrame.addChance(chance1);
		playerFrame.addChance(chance2);
		playerFrame.addChance(chance3);
		
		assertThat(playerFrame.getTotalPins()).isEqualTo(17);
	}
	
	@Test
	public void itIsCompletedWhenOrdinaryFrameHasStrike(@Mock Chance chance) throws FrameException {
		Mockito.when(chance.getPinsAsInt()).thenReturn(10);		
		
		PlayerFrame playerFrame = new PlayerFrame("Player name", 2);
		playerFrame.addChance(chance);
		
		assertTrue(playerFrame.isCompleted());
	}
	
	@Test
	public void itIsCompletedWhenOrdinaryFrameHas2Chances(@Mock Chance chance1, @Mock Chance chance2) throws FrameException {
		Mockito.when(chance1.getPinsAsInt()).thenReturn(5);
		Mockito.when(chance2.getPinsAsInt()).thenReturn(2);
		
		PlayerFrame playerFrame = new PlayerFrame("Player name", 2);
		playerFrame.addChance(chance1);
		playerFrame.addChance(chance2);
		
		assertTrue(playerFrame.isCompleted());
	}
	
	@Test
	public void itIsCompletedWhenFinalFrameHasStrikeAnd3Chances(@Mock Chance chance1, @Mock Chance chance2, @Mock Chance chance3) throws FrameException {
		Mockito.when(chance1.getPinsAsInt()).thenReturn(10);
		
		PlayerFrame playerFrame = new PlayerFrame("Player name", 10);
		playerFrame.addChance(chance1);
		playerFrame.addChance(chance2);
		playerFrame.addChance(chance3);
		
		assertTrue(playerFrame.isCompleted());
	}
}
