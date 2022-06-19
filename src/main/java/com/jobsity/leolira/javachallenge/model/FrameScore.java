package com.jobsity.leolira.javachallenge.model;

import java.util.ArrayList;
import java.util.List;

public class FrameScore {
	private final int frameRound;
	private final List<PlayerScore> playerScores;
	
	public FrameScore(int frameRound) {
		this.frameRound = frameRound;
		this.playerScores = new ArrayList<PlayerScore>();
	}

	public int getFrameRound() {
		return frameRound;
	}

	public List<PlayerScore> getPlayerScores() {
		return playerScores;
	}
	
	public void addPlayerScore(PlayerScore playerScore) {
		this.playerScores.add(playerScore);
	}
}
