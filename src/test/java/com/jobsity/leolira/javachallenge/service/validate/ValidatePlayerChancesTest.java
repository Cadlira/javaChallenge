package com.jobsity.leolira.javachallenge.service.validate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
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

	@TempDir
	private static Path tempDir;

	@BeforeAll
	public static void setUp() throws IOException {
		String lineSeparator = System.getProperty("line.separator");

		Files.createFile(tempDir.resolve("empty.txt"));

		final Path negative = Files.createFile(tempDir.resolve("negative.txt"));
		Files.write(negative, "player\t10".getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
		Files.write(negative, lineSeparator.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
		Files.write(negative, "player\t-5".getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
	}

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
		String path = tempDir.toAbsolutePath().toString();

		Mockito.when(fileNameValidate.getResource()).thenReturn(path);

		ValidatePlayerChances validatePlayerChances = new ValidatePlayerChances(fileNameValidate,
				playerChancesCalculate, extraPlayerChancesValidate);

		PlayerChanceException exception = assertThrows(PlayerChanceException.class,
				() -> validatePlayerChances.validate());

		assertThat(exception.getMessage()).isEqualTo(String.format("The file '%s' is a directory", path));
	}

	@Test
	public void itThrowsAPlayerChanceExceptionWhenFileIsEmpty(@Mock ValidateResource<String> fileNameValidate,
			@Mock CalculateResource<List<PlayerChance>, String> playerChancesCalculate,
			@Mock InnerValidate<List<PlayerChance>> extraPlayerChancesValidate) throws BowlingException {

		String path = tempDir.resolve("empty.txt").toAbsolutePath().toString();

		Mockito.when(fileNameValidate.getResource()).thenReturn(path);

		ValidatePlayerChances validatePlayerChances = new ValidatePlayerChances(fileNameValidate,
				playerChancesCalculate, extraPlayerChancesValidate);

		PlayerChanceException exception = assertThrows(PlayerChanceException.class,
				() -> validatePlayerChances.validate());

		assertThat(exception.getMessage()).isEqualTo(String.format("The file '%s' is empty", path));
	}

	@Test
	public void itValidatesPlayerChancesFile(@Mock ValidateResource<String> fileNameValidate,
			@Mock CalculateResource<List<PlayerChance>, String> playerChancesCalculate,
			@Mock InnerValidate<List<PlayerChance>> extraPlayerChancesValidate, @Mock List<PlayerChance> playerChances)
			throws BowlingException {

		String path = tempDir.resolve("negative.txt").toAbsolutePath().toString();

		Mockito.when(fileNameValidate.getResource()).thenReturn(path);

		Mockito.when(playerChancesCalculate.calculate(path)).thenReturn(playerChances);

		ValidatePlayerChances validatePlayerChances = new ValidatePlayerChances(fileNameValidate,
				playerChancesCalculate, extraPlayerChancesValidate);

		assertDoesNotThrow(() -> validatePlayerChances.validate());
	}

	@Test
	public void itGetsresource(@Mock ValidateResource<String> fileNameValidate,
			@Mock CalculateResource<List<PlayerChance>, String> playerChancesCalculate,
			@Mock InnerValidate<List<PlayerChance>> extraPlayerChancesValidate, @Mock List<PlayerChance> playerChances)
			throws BowlingException {

		String path = tempDir.resolve("negative.txt").toAbsolutePath().toString();

		Mockito.when(fileNameValidate.getResource()).thenReturn(path);

		Mockito.when(playerChancesCalculate.calculate(path)).thenReturn(playerChances);

		ValidatePlayerChances validatePlayerChances = new ValidatePlayerChances(fileNameValidate,
				playerChancesCalculate, extraPlayerChancesValidate);

		validatePlayerChances.validate();

		assertThat(validatePlayerChances.getResource()).isEqualTo(playerChances);
	}
}
