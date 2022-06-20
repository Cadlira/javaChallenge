package com.jobsity.leolira.javachallenge;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jobsity.leolira.javachallenge.model.Frame;
import com.jobsity.leolira.javachallenge.model.FrameScore;
import com.jobsity.leolira.javachallenge.model.PlayerChance;

@ExtendWith(MockitoExtension.class)
public class BowlingTest {

	@Test
	public void itValidatesResources(@Mock ValidateResource<String> validateInputArguments,
			@Mock ValidateResource<List<PlayerChance>> validatePlayerChanceFile,
			@Mock ValidateResource<List<Frame>> validateFrames,
			@Mock ProcessResource<List<FrameScore>> processGameScore,
			@Mock PrintResourceResult<List<String>> printGameResult) {

		Bowling bowling = new Bowling(validateInputArguments, validatePlayerChanceFile, validateFrames,
				processGameScore, printGameResult);
		
		assertDoesNotThrow(() -> bowling.validate());
	}
	
	@Test
	public void itProcessResources(@Mock ValidateResource<String> validateInputArguments,
			@Mock ValidateResource<List<PlayerChance>> validatePlayerChanceFile,
			@Mock ValidateResource<List<Frame>> validateFrames,
			@Mock ProcessResource<List<FrameScore>> processGameScore,
			@Mock PrintResourceResult<List<String>> printGameResult) {

		Bowling bowling = new Bowling(validateInputArguments, validatePlayerChanceFile, validateFrames,
				processGameScore, printGameResult);
		
		assertDoesNotThrow(() -> bowling.process());
	}
	
	@Test
	public void itPrintResourcesResult(@Mock ValidateResource<String> validateInputArguments,
			@Mock ValidateResource<List<PlayerChance>> validatePlayerChanceFile,
			@Mock ValidateResource<List<Frame>> validateFrames,
			@Mock ProcessResource<List<FrameScore>> processGameScore,
			@Mock PrintResourceResult<List<String>> printGameResult) {

		Bowling bowling = new Bowling(validateInputArguments, validatePlayerChanceFile, validateFrames,
				processGameScore, printGameResult);
		
		assertDoesNotThrow(() -> bowling.printResult());
	}
}
