package com.jobsity.leolira.javachallenge.service.validate.supporter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.jobsity.leolira.javachallenge.InputArguments;

public class ArrayInputArgumentsTest {

	@Test
	public void itGetsTheArgumentsList() {
		String[] args = {"arg1"};
		InputArguments<List<String>> inputArguments = new ArrayInputArguments(args);
		
		assertThat(inputArguments.getArguments().size()).isEqualTo(1);
		assertThat(inputArguments.getArguments().get(0)).isEqualTo("arg1");
	}
	
	@Test
	public void itIsEmptyWhenArgumentsListIsNull() {
		InputArguments<List<String>> inputArguments = new ArrayInputArguments(null);
		
		assertTrue(inputArguments.isEmpty());
	}
	
	@Test
	public void itIsEmptyWhenArgumentsListIsEmpty() {
		String[] args = new String[0];
		InputArguments<List<String>> inputArguments = new ArrayInputArguments(args);
		
		assertTrue(inputArguments.isEmpty());
	}
	
	@Test
	public void itNotEmptyWhenArgumentsListIsNotNullOrEmpty() {
		String[] args = {"arg1"};
		InputArguments<List<String>> inputArguments = new ArrayInputArguments(args);
		
		assertFalse(inputArguments.isEmpty());
	}
	
	@Test
	public void itSizeIs0WhenArgumentsListIsNull() {
		InputArguments<List<String>> inputArguments = new ArrayInputArguments(null);
		
		assertThat(inputArguments.size()).isEqualTo(0);
	}
	
	@Test
	public void itGetsSizeWhenArgumentsListIsNotNullOrEmpty() {
		String[] args = {"arg1"};
		InputArguments<List<String>> inputArguments = new ArrayInputArguments(args);
		
		assertThat(inputArguments.size()).isEqualTo(1);
	}
}
