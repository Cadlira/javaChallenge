package com.jobsity.leolira.javachallenge.service.validate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jobsity.leolira.javachallenge.InputArguments;
import com.jobsity.leolira.javachallenge.exception.BowlingException;
import com.jobsity.leolira.javachallenge.exception.InputArgumentsException;

@ExtendWith(MockitoExtension.class)
public class ValidateInputArgumentsTest {

	@Test
	public void itValidatesAValidInputArgument(@Mock InputArguments<List<String>> inputArguments, @Mock List<String> arguments) {
		Mockito.when(inputArguments.size()).thenReturn(1);
		Mockito.when(inputArguments.getArguments()).thenReturn(arguments);
		
		Mockito.when(arguments.get(0)).thenReturn("file path");
		
		ValidateInputArguments validateInputArguments = new ValidateInputArguments(inputArguments);
		
		assertDoesNotThrow(() -> validateInputArguments.validate());
	}
	
	@Test
	public void itGetsResource(@Mock InputArguments<List<String>> inputArguments, @Mock List<String> arguments) throws BowlingException {
		Mockito.when(inputArguments.size()).thenReturn(1);
		Mockito.when(inputArguments.getArguments()).thenReturn(arguments);
		
		Mockito.when(arguments.get(0)).thenReturn("file path");
		
		ValidateInputArguments validateInputArguments = new ValidateInputArguments(inputArguments);
		validateInputArguments.validate();
		
		assertThat(validateInputArguments.getResource()).isEqualTo("file path");
	}
	
	@Test
	public void itThrowsInputArgumentsExceptionWhenResourceIsNotReady(@Mock InputArguments<List<String>> inputArguments) {
		ValidateInputArguments validateInputArguments = new ValidateInputArguments(inputArguments);
		
		InputArgumentsException exception = assertThrows(InputArgumentsException.class, () -> validateInputArguments.getResource());
		
		assertThat(exception.getMessage()).isEqualTo("The input arguments have not yet been validated");
	}
	
	@Test
	public void itThrowsInputArgumentsExceptionWhenValidatingAndArgumentsSizeGreaterThan1(@Mock InputArguments<List<String>> inputArguments) {
		Mockito.when(inputArguments.size()).thenReturn(2);

		ValidateInputArguments validateInputArguments = new ValidateInputArguments(inputArguments);
		
		InputArgumentsException exception = assertThrows(InputArgumentsException.class, () -> validateInputArguments.validate());
		
		assertThat(exception.getMessage()).isEqualTo("Only one player chance file should be provided");
	}
	
	@Test
	public void itThrowsInputArgumentsExceptionWhenValidatingAndArgumentsIsEmpty(@Mock InputArguments<List<String>> inputArguments) {
		Mockito.when(inputArguments.isEmpty()).thenReturn(true);

		ValidateInputArguments validateInputArguments = new ValidateInputArguments(inputArguments);
		
		InputArgumentsException exception = assertThrows(InputArgumentsException.class, () -> validateInputArguments.validate());
		
		assertThat(exception.getMessage()).isEqualTo("A player chance file must be provided");
	}
}
