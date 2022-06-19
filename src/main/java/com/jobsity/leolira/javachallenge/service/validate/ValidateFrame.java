package com.jobsity.leolira.javachallenge.service.validate;

import java.util.List;
import java.util.Objects;

import com.jobsity.leolira.javachallenge.CalculateResource;
import com.jobsity.leolira.javachallenge.ValidateResource;
import com.jobsity.leolira.javachallenge.exception.BowlingException;
import com.jobsity.leolira.javachallenge.exception.FrameException;
import com.jobsity.leolira.javachallenge.model.Frame;
import com.jobsity.leolira.javachallenge.model.PlayerChance;

public class ValidateFrame implements ValidateResource<List<Frame>> {
	private static final int MAX_FRAME_ROUND = 10;
	
	private final ValidateResource<List<PlayerChance>> playerChanceValidate;
	private final CalculateResource<List<Frame>, List<PlayerChance>> framesCalculate;
	
	private List<Frame> frames;
	
	public ValidateFrame(ValidateResource<List<PlayerChance>> playerChanceValidate, CalculateResource<List<Frame>, List<PlayerChance>> framesCalculate) {
		this.playerChanceValidate = playerChanceValidate;
		this.framesCalculate = framesCalculate;
	}

	@Override
	public void validate() throws BowlingException {
		List<PlayerChance> playerChances = this.playerChanceValidate.getResource();
		
		this.frames = this.framesCalculate.calculate(playerChances);
		
		if (this.frames.size() != MAX_FRAME_ROUND) {
			throw new FrameException("There must be exactly 10 frames per game");
		}
	}

	@Override
	public List<Frame> getResource() throws BowlingException {
		validateEmptyFrames();
		
		return this.frames;
	}

	private void validateEmptyFrames() throws FrameException {
		if (Objects.isNull(frames)) {
			throw new FrameException("Frames has not yet been validated");
		}
	}

}
