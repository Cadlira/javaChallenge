package com.jobsity.leolira.javachallenge.service.validate;

import java.io.File;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.jobsity.leolira.javachallenge.CalculateResource;
import com.jobsity.leolira.javachallenge.InnerValidate;
import com.jobsity.leolira.javachallenge.ValidateResource;
import com.jobsity.leolira.javachallenge.exception.BowlingException;
import com.jobsity.leolira.javachallenge.exception.PlayerChanceException;
import com.jobsity.leolira.javachallenge.model.PlayerChance;

public class ValidatePlayerChances implements ValidateResource<List<PlayerChance>> {

	private final ValidateResource<String> fileNameValidate;
	private final CalculateResource<List<PlayerChance>, String> playerChancesCalculate;
	private final InnerValidate<List<PlayerChance>> extraPlayerChancesValidate;
	
	private List<PlayerChance> playerChances;

	public ValidatePlayerChances(ValidateResource<String> fileNameValidate,
			CalculateResource<List<PlayerChance>, String> playerChancesCalculate,
			InnerValidate<List<PlayerChance>> extraPlayerChancesValidate) {
		this.fileNameValidate = fileNameValidate;
		this.playerChancesCalculate = playerChancesCalculate;
		this.extraPlayerChancesValidate = extraPlayerChancesValidate;
	}

	@Override
	public List<PlayerChance> getResource() throws PlayerChanceException {
		validateEmptyPlayerChances();

		return this.playerChances;
	}

	@Override
	public void validate() throws BowlingException {
		String fileName = this.fileNameValidate.getResource();

		validateEmptyFileName(fileName);

		File playerChancesFile = new File(fileName);

		validateExistingFile(fileName, playerChancesFile);

		validateIsNotDirectory(fileName, playerChancesFile);

		validateIsNotEmpty(fileName, playerChancesFile);

		this.playerChances = this.playerChancesCalculate.calculate(fileName);
		
		this.extraPlayerChancesValidate.validate(playerChances);
	}

	private void validateIsNotEmpty(String fileName, File playerChancesFile) throws PlayerChanceException {
		if (playerChancesFile.length() == 0) {
			throw new PlayerChanceException(String.format("The file '%s' is empty", fileName));
		}
	}

	private void validateIsNotDirectory(String fileName, File playerChancesFile) throws PlayerChanceException {
		if (playerChancesFile.isDirectory()) {
			throw new PlayerChanceException(String.format("The file '%s' is a directory", fileName));
		}
	}

	private void validateExistingFile(String fileName, File playerChancesFile) throws PlayerChanceException {
		if (!playerChancesFile.exists()) {
			throw new PlayerChanceException(String.format("The player chances file '%s' does not exists", fileName));
		}
	}

	private void validateEmptyFileName(String fileName) throws PlayerChanceException {
		if (StringUtils.isEmpty(fileName)) {
			throw new PlayerChanceException("A valid player chances file must be provided");
		}
	}

	private void validateEmptyPlayerChances() throws PlayerChanceException {
		if (Objects.isNull(playerChances)) {
			throw new PlayerChanceException("The player chances file has not yet been validated");
		}
	}
}
