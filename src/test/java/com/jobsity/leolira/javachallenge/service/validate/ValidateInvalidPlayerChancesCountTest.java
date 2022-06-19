package com.jobsity.leolira.javachallenge.service.validate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.jobsity.leolira.javachallenge.exception.PlayerChanceException;
import com.jobsity.leolira.javachallenge.model.PlayerChance;

public class ValidateInvalidPlayerChancesCountTest {

	@Test
	public void itValidatesPlayerChancesCount() {
		List<PlayerChance> chances = new ArrayList<PlayerChance>();
		for (int i = 0; i < 12; i++) {
			chances.add(new PlayerChance("Player", "10"));
		}
		
		ValidateInvalidPlayerChancesCount validate = new ValidateInvalidPlayerChancesCount();
		
		assertDoesNotThrow(() -> validate.validate(chances));
	}
	
	@Test
	public void itValidatesPlayerChancesCountForNoStrikes() {
		List<PlayerChance> chances = new ArrayList<PlayerChance>();
		for (int i = 0; i < 21; i++) {
			chances.add(new PlayerChance("Player", "5"));
		}
		
		ValidateInvalidPlayerChancesCount validate = new ValidateInvalidPlayerChancesCount();
		
		assertDoesNotThrow(() -> validate.validate(chances));
	}
	
	@Test
	public void itThrowsAPlayerChanceExceptionWhenValidateAndChancesAreLessThanMinChances() {
		List<PlayerChance> chances = new ArrayList<PlayerChance>();
		for (int i = 0; i < 10; i++) {
			chances.add(new PlayerChance("Player", "10"));
		}
		
		ValidateInvalidPlayerChancesCount validate = new ValidateInvalidPlayerChancesCount();
		
		PlayerChanceException exception = assertThrows(PlayerChanceException.class, () -> validate.validate(chances));
		
		assertThat(exception.getMessage()).isEqualTo("The player chances count is invalid fo player 'Player'");
	}
	
	@Test
	public void itThrowsAPlayerChanceExceptionWhenValidateAndChancesAreGreaterThanMaxChances() {
		List<PlayerChance> chances = new ArrayList<PlayerChance>();
		for (int i = 0; i < 13; i++) {
			chances.add(new PlayerChance("Player", "10"));
		}
		
		ValidateInvalidPlayerChancesCount validate = new ValidateInvalidPlayerChancesCount();
		
		PlayerChanceException exception = assertThrows(PlayerChanceException.class, () -> validate.validate(chances));
		
		assertThat(exception.getMessage()).isEqualTo("The player chances count is invalid fo player 'Player'");
	}
	
	@Test
	public void itThrowsAPlayerChanceExceptionWhenValidateAndChancesAreGreaterThanMaxChancesForStrike() {
		List<PlayerChance> chances = new ArrayList<PlayerChance>();
		for (int i = 0; i < 14; i++) {
			chances.add(new PlayerChance("Player", "10"));
		}
		
		ValidateInvalidPlayerChancesCount validate = new ValidateInvalidPlayerChancesCount();
		
		PlayerChanceException exception = assertThrows(PlayerChanceException.class, () -> validate.validate(chances));
		
		assertThat(exception.getMessage()).isEqualTo("The player chances count is invalid fo player 'Player'");
	}
	
	@Test
	public void itThrowsAPlayerChanceExceptionWhenValidateAndChancesAreNotEqualsMaxChancesForFinalSpare() {
		List<PlayerChance> chances = new ArrayList<PlayerChance>();
		for (int i = 0; i < 8; i++) {
			chances.add(new PlayerChance("Player", "10"));
		}
		chances.add(new PlayerChance("Player", "5"));
		chances.add(new PlayerChance("Player", "5"));
		
		ValidateInvalidPlayerChancesCount validate = new ValidateInvalidPlayerChancesCount();
		
		PlayerChanceException exception = assertThrows(PlayerChanceException.class, () -> validate.validate(chances));
		
		assertThat(exception.getMessage()).isEqualTo("The player chances count is invalid fo player 'Player'");
	}
}
