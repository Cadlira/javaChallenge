package com.jobsity.leolira.javachallenge.service.calculate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.jobsity.leolira.javachallenge.exception.BowlingException;
import com.jobsity.leolira.javachallenge.exception.PlayerChanceException;
import com.jobsity.leolira.javachallenge.model.PlayerChance;

public class CalculatePlayerChancesTest {

	@TempDir
	private static Path tempDir;

	@BeforeAll
	public static void setUp() throws IOException {
		String lineSeparator = System.getProperty("line.separator");
		
		final Path invalidScore = Files.createFile(tempDir.resolve("invalid-score.txt"));
		Files.write(invalidScore, "player\t10".getBytes(), StandardOpenOption.APPEND);
		Files.write(invalidScore, lineSeparator.getBytes(), StandardOpenOption.APPEND);
		Files.write(invalidScore, "player\tlorem".getBytes(), StandardOpenOption.APPEND);

		final Path freeText = Files.createFile(tempDir.resolve("free-text.txt"));
		Files.write(freeText, "player\t10".getBytes(), StandardOpenOption.APPEND);
		Files.write(freeText, lineSeparator.getBytes(), StandardOpenOption.APPEND);
		Files.write(freeText, "free text writing".getBytes(), StandardOpenOption.APPEND);

		final Path blankLine = Files.createFile(tempDir.resolve("blank-line.txt"));
		Files.write(blankLine, "player\t10".getBytes(), StandardOpenOption.APPEND);
		Files.write(blankLine, lineSeparator.getBytes(), StandardOpenOption.APPEND);
		Files.write(blankLine, "player\t10".getBytes(), StandardOpenOption.APPEND);
		Files.write(blankLine, lineSeparator.getBytes(), StandardOpenOption.APPEND);
		Files.write(blankLine, lineSeparator.getBytes(), StandardOpenOption.APPEND);
		Files.write(blankLine, "player\t10".getBytes(), StandardOpenOption.APPEND);

		final Path perfect = Files.createFile(tempDir.resolve("perfect.txt"));
		Files.write(perfect, "player\t10".getBytes(), StandardOpenOption.APPEND);
		for (int i = 0; i < 11; i++) {
			Files.write(perfect, lineSeparator.getBytes(), StandardOpenOption.APPEND);
			Files.write(perfect, "player\t10".getBytes(), StandardOpenOption.APPEND);
		}

	}

	@Test
	public void itCalculatePlayerChancesPerfectFromFileSuccess() throws BowlingException {
		CalculatePlayerChances calculatePlayerChances = new CalculatePlayerChances();

		List<PlayerChance> chances = calculatePlayerChances
				.calculate(tempDir.resolve("perfect.txt").toAbsolutePath().toString());

		assertThat(chances.size()).isEqualTo(12);
	}

	@Test
	public void itThrowsPlayerChanceExceptionWhenFileNotExists() throws BowlingException {
		CalculatePlayerChances calculatePlayerChances = new CalculatePlayerChances();

		PlayerChanceException exception = assertThrows(PlayerChanceException.class,
				() -> calculatePlayerChances.calculate("noexists.txt"));

		assertThat(exception.getMessage())
				.isEqualTo("A generic error was raised while reading the player chances file: noexists.txt");
	}

	@Test
	public void itThrowsPlayerChanceExceptionWhenPlayerChanceIsInvalid() throws BowlingException {
		CalculatePlayerChances calculatePlayerChances = new CalculatePlayerChances();

		PlayerChanceException exception = assertThrows(PlayerChanceException.class, () -> calculatePlayerChances
				.calculate(tempDir.resolve("invalid-score.txt").toAbsolutePath().toString()));

		assertThat(exception.getMessage())
				.isEqualTo("The player chance is invalid due to: The pins attribute has a invalid value");
	}

	@Test
	public void itThrowsPlayerChanceExceptionWhenFileHasAInvalidLine() throws BowlingException {
		CalculatePlayerChances calculatePlayerChances = new CalculatePlayerChances();

		PlayerChanceException exception = assertThrows(PlayerChanceException.class,
				() -> calculatePlayerChances.calculate(tempDir.resolve("free-text.txt").toAbsolutePath().toString()));

		assertThat(exception.getMessage()).isEqualTo("The player chances file has a invalid line");
	}

	@Test
	public void itThrowsPlayerChanceExceptionWhenFileHasABlankLine() throws BowlingException {
		CalculatePlayerChances calculatePlayerChances = new CalculatePlayerChances();

		PlayerChanceException exception = assertThrows(PlayerChanceException.class,
				() -> calculatePlayerChances.calculate(tempDir.resolve("blank-line.txt").toAbsolutePath().toString()));

		assertThat(exception.getMessage()).isEqualTo("The player chances file has a empty line");
	}
}
