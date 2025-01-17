package com.jobsity.leolira.javachallenge.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PlayerChanceTest {

	@Test
	public void itGetsThePlayer() {
		PlayerChance playerChance = new PlayerChance("Player name", "5");
		
		assertThat(playerChance.getPlayer()).isEqualTo("Player name");
	}
	
	@Test
	public void itGetsTheChance() {
		PlayerChance playerChance = new PlayerChance("Player name", "5");
		
		assertNotNull(playerChance.getPins());
		assertThat(playerChance.getPins()).isInstanceOf(Chance.class);
	}
	
	@Test
	public void itGetsPinsAsInt() {
		PlayerChance playerChance = new PlayerChance("Player name", "5");
		
		assertThat(playerChance.getPinsAsInt()).isEqualTo(5);
	}
	
	@Test
	public void itIsStrikeFor10Pinfalls() {
		PlayerChance playerChance = new PlayerChance(null, "10");
		
		assertTrue(playerChance.isStrike());
	}
	
	@Test
	public void itIsNotStrikeForLessThen10Pinfalls() {
		PlayerChance playerChance = new PlayerChance(null, "9");
		
		assertFalse(playerChance.isStrike());
	}
	
	@Test
	public void itIsValidWhenPlayerIsValidAndPinsAreValid() {
		PlayerChance playerChance = new PlayerChance("Player name", "5");
		
		assertTrue(playerChance.isValid());
	}
	
	@Test
	public void itIsInvalidWhenPlayerIsEmpty() {
		PlayerChance playerChance = new PlayerChance(null, "5");
		
		assertFalse(playerChance.isValid());
		assertThat(playerChance.getInvalidCause()).isEqualTo("The player is empty");
	}
	
	@Test
	public void itIsInvalidWhenPinsIsEmpty() {
		PlayerChance playerChance = new PlayerChance("Player name", null);
		
		assertFalse(playerChance.isValid());
		assertThat(playerChance.getInvalidCause()).isEqualTo("The pins attribute is empty");
	}
	
	@Test
	public void itIsInvalidWhenPinshasAInvalidValue() {
		PlayerChance playerChance = new PlayerChance("Player name", "X");
		
		assertFalse(playerChance.isValid());
		assertThat(playerChance.getInvalidCause()).isEqualTo("The pins attribute has a invalid value");
	}
	
	@Test
	public void itIsInvalidWhenPinsIsOutOfRange() {
		PlayerChance playerChance = new PlayerChance("Player name", "11");
		
		assertFalse(playerChance.isValid());
		assertThat(playerChance.getInvalidCause()).isEqualTo("The pins attribute is less than 0 or greater than 10");
	}
}
