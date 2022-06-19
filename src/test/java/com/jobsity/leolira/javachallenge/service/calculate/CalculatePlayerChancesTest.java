package com.jobsity.leolira.javachallenge.service.calculate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.jobsity.leolira.javachallenge.exception.BowlingException;
import com.jobsity.leolira.javachallenge.exception.PlayerChanceException;
import com.jobsity.leolira.javachallenge.model.PlayerChance;

public class CalculatePlayerChancesTest {

	@Test
	public void itCalculatePlayerChancesPerfectFromFileSuccess() throws BowlingException {
		CalculatePlayerChances calculatePlayerChances = new CalculatePlayerChances();
		
		List<PlayerChance> chances = calculatePlayerChances.calculate("src/test/resources/positive/perfect.txt");
		
		assertThat(chances.size()).isEqualTo(12);
	}
	
	@Test
	public void itThrowsPlayerChanceExceptionWhenFileNotExists() throws BowlingException {
		CalculatePlayerChances calculatePlayerChances = new CalculatePlayerChances();
		
		PlayerChanceException exception = assertThrows(PlayerChanceException.class, () -> calculatePlayerChances.calculate("noexists.txt"));
		
		assertThat(exception.getMessage()).isEqualTo("A generic error was raised while reading the player chances file: noexists.txt");
	}
	
	@Test
	public void itThrowsPlayerChanceExceptionWhenPlayerChanceIsInvalid() throws BowlingException {
		CalculatePlayerChances calculatePlayerChances = new CalculatePlayerChances();
		
		PlayerChanceException exception = assertThrows(PlayerChanceException.class, () -> calculatePlayerChances.calculate("src/test/resources/negative/invalid-score.txt"));
		
		assertThat(exception.getMessage()).isEqualTo("The player chance is invalid due to: The pins attribute has a invalid value");
	}
	
	@Test
	public void itThrowsPlayerChanceExceptionWhenFileHasAInvalidLine() throws BowlingException {
		CalculatePlayerChances calculatePlayerChances = new CalculatePlayerChances();
		
		PlayerChanceException exception = assertThrows(PlayerChanceException.class, () -> calculatePlayerChances.calculate("src/test/resources/negative/free-text.txt"));
		
		assertThat(exception.getMessage()).isEqualTo("The player chances file has a invalid line");
	}
	
	@Test
	public void itThrowsPlayerChanceExceptionWhenFileHasABlankLine() throws BowlingException {
		CalculatePlayerChances calculatePlayerChances = new CalculatePlayerChances();
		
		PlayerChanceException exception = assertThrows(PlayerChanceException.class, () -> calculatePlayerChances.calculate("src/test/resources/negative/blank-line.txt"));
		
		assertThat(exception.getMessage()).isEqualTo("The player chances file has a empty line");
	}
}
