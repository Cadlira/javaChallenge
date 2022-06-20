package com.jobsity.leolira.javachallenge;

import java.util.List;

import com.jobsity.leolira.javachallenge.exception.BowlingException;
import com.jobsity.leolira.javachallenge.exception.PlayerChanceException;
import com.jobsity.leolira.javachallenge.model.Frame;
import com.jobsity.leolira.javachallenge.model.FrameScore;
import com.jobsity.leolira.javachallenge.model.PlayerChance;
import com.jobsity.leolira.javachallenge.service.PrintGameResult;
import com.jobsity.leolira.javachallenge.service.ProcessGameScore;
import com.jobsity.leolira.javachallenge.service.calculate.CalculateFrames;
import com.jobsity.leolira.javachallenge.service.calculate.CalculatePlayerChances;
import com.jobsity.leolira.javachallenge.service.validate.ValidateFrame;
import com.jobsity.leolira.javachallenge.service.validate.ValidateInputArguments;
import com.jobsity.leolira.javachallenge.service.validate.ValidateInvalidPlayerChancesCount;
import com.jobsity.leolira.javachallenge.service.validate.ValidatePlayerChances;
import com.jobsity.leolira.javachallenge.service.validate.supporter.ArrayInputArguments;

public class Bowling {

	private static final String ERROR_MESSAGE = "An error has been found and the application will be closed.%nErro group: %s%nError message: %s %n%nPlease fix the errors and try again!";

	private final ValidateResource<String> validateInputArguments;
	private final ValidateResource<List<PlayerChance>> validatePlayerChances;
	private final ValidateResource<List<Frame>> validateFrames;
	private final ProcessResource<List<FrameScore>> processGameScore;
	private final PrintResourceResult<List<String>> printGameResult;

	public static void main(String[] args) {
		try {

			Bowling bowling = Bowling.create(args);

			bowling.validate();

			bowling.process();

			bowling.printResult();

		} catch (BowlingException bowlingException) {
			System.out.println(
					String.format(ERROR_MESSAGE, bowlingException.getErrorGroup(), bowlingException.getMessage()));
		}

	}

	public static Bowling create(String[] args) throws PlayerChanceException {
		InputArguments<List<String>> inputArgs = new ArrayInputArguments(args);
		ValidateResource<String> validateArgs = new ValidateInputArguments(inputArgs);

		CalculateResource<List<PlayerChance>, String> calculatePlayerChance = new CalculatePlayerChances();
		InnerValidate<List<PlayerChance>> invalidCountValidate = new ValidateInvalidPlayerChancesCount();
		ValidateResource<List<PlayerChance>> validateChances = new ValidatePlayerChances(validateArgs,
				calculatePlayerChance, invalidCountValidate);

		CalculateResource<List<Frame>, List<PlayerChance>> calculateFrames = new CalculateFrames();
		ValidateResource<List<Frame>> validateFrames = new ValidateFrame(validateChances, calculateFrames);

		ProcessResource<List<FrameScore>> processGameScore = new ProcessGameScore(validateFrames, validateChances);

		PrintResourceResult<List<String>> printGameResult = new PrintGameResult(processGameScore);

		return new Bowling(validateArgs, validateChances, validateFrames, processGameScore, printGameResult);
	}

	public Bowling(ValidateResource<String> validateInputArguments,
			ValidateResource<List<PlayerChance>> validatePlayerChanceFile, ValidateResource<List<Frame>> validateFrames,
			ProcessResource<List<FrameScore>> processGameScore, PrintResourceResult<List<String>> printGameResult) {
		this.validateInputArguments = validateInputArguments;
		this.validatePlayerChances = validatePlayerChanceFile;
		this.validateFrames = validateFrames;
		this.processGameScore = processGameScore;
		this.printGameResult = printGameResult;
	}

	public void validate() throws BowlingException {

		this.validateInputArguments.validate();

		this.validatePlayerChances.validate();

		this.validateFrames.validate();
	}

	public void process() throws BowlingException {
		this.processGameScore.process();
	}

	public void printResult() throws BowlingException {
		this.printGameResult.print();
	}

}
