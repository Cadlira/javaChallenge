package com.jobsity.leolira.javachallenge.service.calculate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.jobsity.leolira.javachallenge.exception.BowlingException;
import com.jobsity.leolira.javachallenge.model.Frame;
import com.jobsity.leolira.javachallenge.model.PlayerChance;

public class CalculateFramesTest {

	@Test
	public void itCalculatesFramesSuccessForPerfect() throws BowlingException {
		List<PlayerChance> chances = new ArrayList<PlayerChance>();
		for (int i = 0; i < 12; i++) {
			chances.add(new PlayerChance("Player", "10"));
		}
		
		CalculateFrames calculateFrames = new CalculateFrames();
		
		List<Frame> frames = calculateFrames.calculate(chances);
		
		assertThat(frames.size()).isEqualTo(10);
	}
}
