package com.jobsity.leolira.javachallenge.service.calculate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

import com.jobsity.leolira.javachallenge.CalculateResource;
import com.jobsity.leolira.javachallenge.exception.BowlingException;
import com.jobsity.leolira.javachallenge.exception.PlayerChanceException;
import com.jobsity.leolira.javachallenge.model.PlayerChance;

public class CalculatePlayerChances implements CalculateResource<List<PlayerChance>, String> {

	private static final int TOKENS_NUMBER = 2;

	@Override
	public List<PlayerChance> calculate(String fileName) throws BowlingException {
		List<PlayerChance> playerChances = null;
		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(Files.newInputStream(Paths.get(fileName)), "UTF-8"))) {
			playerChances = new ArrayList<PlayerChance>();

			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				playerChances.add(this.createPlayerChange(line));
			}

		} catch (IOException e) {
			throw new PlayerChanceException(String
					.format("A generic error was raised while reading the player chances file: %s", e.getMessage()));
		}

		return playerChances;
	}

	private PlayerChance createPlayerChange(String line) throws PlayerChanceException {
		validateEmptyLine(line);

		StringTokenizer tokens = new StringTokenizer(line, "\t");
		validateInvalidLine(tokens);

		PlayerChance playerChance = new PlayerChance(tokens.nextToken(), tokens.nextToken());
		validatePlayerChance(playerChance);

		return playerChance;
	}

	private void validatePlayerChance(PlayerChance playerChance) throws PlayerChanceException {
		if (!playerChance.isValid()) {
			throw new PlayerChanceException("The player chance is invalid due to: " + playerChance.getInvalidCause());
		}
	}

	private void validateInvalidLine(StringTokenizer tokens) throws PlayerChanceException {
		if (tokens.countTokens() != TOKENS_NUMBER) {
			throw new PlayerChanceException("The player chances file has a invalid line");
		}
	}

	private void validateEmptyLine(String line) throws PlayerChanceException {
		if (StringUtils.isEmpty(line)) {
			throw new PlayerChanceException("The player chances file has a empty line");
		}
	}
}
