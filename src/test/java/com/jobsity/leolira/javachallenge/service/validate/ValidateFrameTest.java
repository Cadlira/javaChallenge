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
import com.jobsity.leolira.javachallenge.ValidateResource;
import com.jobsity.leolira.javachallenge.exception.BowlingException;
import com.jobsity.leolira.javachallenge.exception.FrameException;
import com.jobsity.leolira.javachallenge.model.Frame;
import com.jobsity.leolira.javachallenge.model.PlayerChance;

@ExtendWith(MockitoExtension.class)
public class ValidateFrameTest {

	@Test
	public void itValidateFramesWithSuccess(@Mock ValidateResource<List<PlayerChance>> playerChanceValidate,
			@Mock CalculateResource<List<Frame>, List<PlayerChance>> framesCalculate, @Mock List<PlayerChance> chances,
			@Mock List<Frame> frames) throws BowlingException {

		Mockito.when(playerChanceValidate.getResource()).thenReturn(chances);
		Mockito.when(framesCalculate.calculate(chances)).thenReturn(frames);
		Mockito.when(frames.size()).thenReturn(10);

		ValidateFrame validateFrame = new ValidateFrame(playerChanceValidate, framesCalculate);

		assertDoesNotThrow(() -> validateFrame.validate());
	}

	@Test
	public void itThrowsAFrameExcepionWhenFrameRoundIsNotEqualsTo10(
			@Mock ValidateResource<List<PlayerChance>> playerChanceValidate,
			@Mock CalculateResource<List<Frame>, List<PlayerChance>> framesCalculate, @Mock List<PlayerChance> chances,
			@Mock List<Frame> frames) throws BowlingException {

		Mockito.when(playerChanceValidate.getResource()).thenReturn(chances);
		Mockito.when(framesCalculate.calculate(chances)).thenReturn(frames);
		Mockito.when(frames.size()).thenReturn(9);

		ValidateFrame validateFrame = new ValidateFrame(playerChanceValidate, framesCalculate);

		FrameException exception = assertThrows(FrameException.class, () -> validateFrame.validate());

		assertThat(exception.getMessage()).isEqualTo("There must be exactly 10 frames per game");
	}

	@Test
	public void itGetsResource(@Mock ValidateResource<List<PlayerChance>> playerChanceValidate,
			@Mock CalculateResource<List<Frame>, List<PlayerChance>> framesCalculate, @Mock List<PlayerChance> chances,
			@Mock List<Frame> frames) throws BowlingException {

		Mockito.when(playerChanceValidate.getResource()).thenReturn(chances);
		Mockito.when(framesCalculate.calculate(chances)).thenReturn(frames);
		Mockito.when(frames.size()).thenReturn(10);

		ValidateFrame validateFrame = new ValidateFrame(playerChanceValidate, framesCalculate);

		validateFrame.validate();

		assertThat(validateFrame.getResource()).isEqualTo(frames);
	}

	@Test
	public void itThrowsAFrameExcepionWhenResourceIsNull(
			@Mock ValidateResource<List<PlayerChance>> playerChanceValidate,
			@Mock CalculateResource<List<Frame>, List<PlayerChance>> framesCalculate) throws BowlingException {

		ValidateFrame validateFrame = new ValidateFrame(playerChanceValidate, framesCalculate);

		FrameException exception = assertThrows(FrameException.class, () -> validateFrame.getResource());

		assertThat(exception.getMessage()).isEqualTo("Frames has not yet been validated");
	}
}
