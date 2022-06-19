package com.jobsity.leolira.javachallenge.service;

import static com.jobsity.leolira.javachallenge.util.LambdaUtil.distinctByKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.jobsity.leolira.javachallenge.ProcessResource;
import com.jobsity.leolira.javachallenge.ValidateResource;
import com.jobsity.leolira.javachallenge.exception.BowlingException;
import com.jobsity.leolira.javachallenge.exception.ProcessScoreException;
import com.jobsity.leolira.javachallenge.model.Frame;
import com.jobsity.leolira.javachallenge.model.FrameScore;
import com.jobsity.leolira.javachallenge.model.PlayerChance;

public class ProcessGameScore implements ProcessResource<List<FrameScore>> {

	private final ValidateResource<List<Frame>> framesValidate;
	private final ValidateResource<List<PlayerChance>> playerChanceValidate;
	
	private List<FrameScore> frameScores;
	private List<String> players;

	public ProcessGameScore(ValidateResource<List<Frame>> framesValidate,
			ValidateResource<List<PlayerChance>> playerChanceValidate) {
		this.framesValidate = framesValidate;
		this.playerChanceValidate = playerChanceValidate;
	}

	@Override
	public void process() throws BowlingException {
		loadPlayers();

		List<Frame> frames = framesValidate.getResource();

		this.frameScores = new ArrayList<FrameScore>();
		for (Frame frame : frames) {
			calculateFrameScore(frame);
		}
	}

	private void calculateFrameScore(Frame frame) {
		FrameScore currentFrameScore = new FrameScore(frame.getRound());
		this.frameScores.add(currentFrameScore);
		
		for (String player : players) {
			currentFrameScore.addPlayerScore(frame.calculatePlayerScore(player));
		}
	}

	@Override
	public List<FrameScore> getProcessedResult() throws BowlingException {
		if (Objects.isNull(frameScores)) {
			throw new ProcessScoreException("The game score has not yet been processed");
		}
		
		return this.frameScores;
	}


	private void loadPlayers() throws BowlingException {
		List<PlayerChance> chances = playerChanceValidate.getResource();
		players = chances.stream().filter(distinctByKey(chance -> chance.getPlayer())).map(chance -> chance.getPlayer())
				.sorted().collect(Collectors.toList());
	}
}
