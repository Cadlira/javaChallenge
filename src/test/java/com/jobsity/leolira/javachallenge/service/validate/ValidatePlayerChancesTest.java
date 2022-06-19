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

import com.jobsity.leolira.javachallenge.CalculateResource;
import com.jobsity.leolira.javachallenge.InnerValidate;
import com.jobsity.leolira.javachallenge.ValidateResource;
import com.jobsity.leolira.javachallenge.exception.BowlingException;
import com.jobsity.leolira.javachallenge.exception.PlayerChanceException;
import com.jobsity.leolira.javachallenge.model.PlayerChance;

@ExtendWith(MockitoExtension.class)
public class ValidatePlayerChancesTest {

	@Test
	public void itThrowsAPlayerChanceExceptionWhenResourcesIsNull(@Mock ValidateResource<String> fileNameValidate,
			@Mock CalculateResource<List<PlayerChance>, String> playerChancesCalculate,
			@Mock InnerValidate<List<PlayerChance>> extraPlayerChancesValidate) {

		ValidatePlayerChances validatePlayerChances = new ValidatePlayerChances(fileNameValidate,
				playerChancesCalculate, extraPlayerChancesValidate);

		PlayerChanceException exception = assertThrows(PlayerChanceException.class,
				() -> validatePlayerChances.getResource());

		assertThat(exception.getMessage()).isEqualTo("The player chances file has not yet been validated");
	}

	@Test
	public void itThrowsAPlayerChanceExceptionWhenFileNameIsEmpty(@Mock ValidateResource<String> fileNameValidate,
			@Mock CalculateResource<List<PlayerChance>, String> playerChancesCalculate,
			@Mock InnerValidate<List<PlayerChance>> extraPlayerChancesValidate) throws BowlingException {

		Mockito.when(fileNameValidate.getResource()).thenReturn(null);

		ValidatePlayerChances validatePlayerChances = new ValidatePlayerChances(fileNameValidate,
				playerChancesCalculate, extraPlayerChancesValidate);

		PlayerChanceException exception = assertThrows(PlayerChanceException.class,
				() -> validatePlayerChances.validate());

		assertThat(exception.getMessage()).isEqualTo("A valid player chances file must be provided");
	}

	@Test
	public void itThrowsAPlayerChanceExceptionWhenFileDoesNotExists(@Mock ValidateResource<String> fileNameValidate,
			@Mock CalculateResource<List<PlayerChance>, String> playerChancesCalculate,
			@Mock InnerValidate<List<PlayerChance>> extraPlayerChancesValidate) throws BowlingException {

		Mockito.when(fileNameValidate.getResource()).thenReturn("nonExists.txt");

		ValidatePlayerChances validatePlayerChances = new ValidatePlayerChances(fileNameValidate,
				playerChancesCalculate, extraPlayerChancesValidate);

		PlayerChanceException exception = assertThrows(PlayerChanceException.class,
				() -> validatePlayerChances.validate());

		assertThat(exception.getMessage()).isEqualTo("The player chances file 'nonExists.txt' does not exists");
	}

	@Test
	public void itThrowsAPlayerChanceExceptionWhenFileIsADirectory(@Mock ValidateResource<String> fileNameValidate,
			@Mock CalculateResource<List<PlayerChance>, String> playerChancesCalculate,
			@Mock InnerValidate<List<PlayerChance>> extraPlayerChancesValidate) throws BowlingException {

		Mockito.when(fileNameValidate.getResource()).thenReturn("src/test/resources/negative/");

		ValidatePlayerChances validatePlayerChances = new ValidatePlayerChances(fileNameValidate,
				playerChancesCalculate, extraPlayerChancesValidate);

		PlayerChanceException exception = assertThrows(PlayerChanceException.class,
				() -> validatePlayerChances.validate());

		assertThat(exception.getMessage()).isEqualTo("The file 'src/test/resources/negative/' is a directory");
	}

	@Test
	public void itThrowsAPlayerChanceExceptionWhenFileIsEmpty(@Mock ValidateResource<String> fileNameValidate,
			@Mock CalculateResource<List<PlayerChance>, String> playerChancesCalculate,
			@Mock InnerValidate<List<PlayerChance>> extraPlayerChancesValidate) throws BowlingException {

		Mockito.when(fileNameValidate.getResource()).thenReturn("src/test/resources/negative/empty.txt");

		ValidatePlayerChances validatePlayerChances = new ValidatePlayerChances(fileNameValidate,
				playerChancesCalculate, extraPlayerChancesValidate);

		PlayerChanceException exception = assertThrows(PlayerChanceException.class,
				() -> validatePlayerChances.validate());

		assertThat(exception.getMessage()).isEqualTo("The file 'src/test/resources/negative/empty.txt' is empty");
	}

	@Test
	public void itValidatesPlayerChancesFile(@Mock ValidateResource<String> fileNameValidate,
			@Mock CalculateResource<List<PlayerChance>, String> playerChancesCalculate,
			@Mock InnerValidate<List<PlayerChance>> extraPlayerChancesValidate, @Mock List<PlayerChance> playerChances)
			throws BowlingException {

		Mockito.when(fileNameValidate.getResource()).thenReturn("src/test/resources/negative/negative.txt");

		Mockito.when(playerChancesCalculate.calculate("src/test/resources/negative/negative.txt")).thenReturn(playerChances);

		ValidatePlayerChances validatePlayerChances = new ValidatePlayerChances(fileNameValidate,
				playerChancesCalculate, extraPlayerChancesValidate);

		
		assertDoesNotThrow(() -> validatePlayerChances.validate());
	}
	
	@Test
	public void itGetsresource(@Mock ValidateResource<String> fileNameValidate,
			@Mock CalculateResource<List<PlayerChance>, String> playerChancesCalculate,
			@Mock InnerValidate<List<PlayerChance>> extraPlayerChancesValidate, @Mock List<PlayerChance> playerChances)
			throws BowlingException {

		Mockito.when(fileNameValidate.getResource()).thenReturn("src/test/resources/negative/negative.txt");

		Mockito.when(playerChancesCalculate.calculate("src/test/resources/negative/negative.txt")).thenReturn(playerChances);

		ValidatePlayerChances validatePlayerChances = new ValidatePlayerChances(fileNameValidate,
				playerChancesCalculate, extraPlayerChancesValidate);
		
		validatePlayerChances.validate();
		
		assertThat(validatePlayerChances.getResource()).isEqualTo(playerChances);
	}
}
