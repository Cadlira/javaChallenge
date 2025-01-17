package com.jobsity.leolira.javachallenge.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ChanceTest {

	@Test
	public void itGetsThePins() {
		Chance chance = new Chance("5");
		
		assertThat(chance.getPins()).isEqualTo("5");
	}
	
	@Test
	public void itIsNotOutOfRangeForPinsBetween0And10() {
		Chance chance = new Chance("5");
		
		assertFalse(chance.isOutOfRange());
	}
	
	@Test
	public void itIsOutOfRangeForPinsLessThen0() {
		Chance chance = new Chance("-1");
		
		assertTrue(chance.isOutOfRange());
	}
	
	@Test
	public void itIsOutOfRangeForPinsGreaterThen10() {
		Chance chance = new Chance("11");
		
		assertTrue(chance.isOutOfRange());
	}
	
	@Test
	public void itIsEmptyForNullPins() {
		Chance chance = new Chance(null);
		
		assertTrue(chance.isEmpty());
	}
	
	@Test
	public void itIsNumericForNumberStrings() {
		Chance chance = new Chance("20");
		
		assertTrue(chance.isNumber());
	}
	
	@Test
	public void itIsValidWhenPinsValueIsF() {
		Chance chance = new Chance("F");
		
		assertFalse(chance.hasInvalidValue());
	}
	
	@Test
	public void itIsValidWhenPinsValueIsNumeric() {
		Chance chance = new Chance("10");
		
		assertFalse(chance.hasInvalidValue());
	}
	
	@Test
	public void itIsInvalidWhenPinsValueIsNotNumericNitherF() {
		Chance chance = new Chance("X");
		
		assertTrue(chance.hasInvalidValue());
	}
	
	@Test
	public void itReturns_0_WhenGettingPinsAsInt_F_Pins() {
		Chance chance = new Chance("F");
		
		assertThat(chance.getPinsAsInt()).isEqualTo(0);
	}
	
	@Test
	public void itReturnsNegative_1_WhenGettingPinsAsIntNon_F_Pins() {
		Chance chance = new Chance("X");
		
		assertThat(chance.getPinsAsInt()).isEqualTo(-1);
	}
}
