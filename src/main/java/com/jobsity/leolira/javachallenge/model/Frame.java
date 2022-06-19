package com.jobsity.leolira.javachallenge.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.jobsity.leolira.javachallenge.exception.FrameException;

public class Frame {
	private static final int MIN_FRAME_ROUND = 1;
	private static final int MAX_FRAME_ROUND = 10;
	private static final int SPECIAL_PLAYER_CHANCES = 3;
	private static final int ORDNARY_PLAYER_CHANCES = 2;
	
	private final int round;
	private final List<PlayerFrame> playerFrames;
	
	private Frame nextFrame;

	public Frame(int round) throws FrameException {
		if (round < MIN_FRAME_ROUND || round > MAX_FRAME_ROUND) {
			throw new FrameException("There must be exactly 10 frames per game");
		}

		this.round = round;
		this.playerFrames = new ArrayList<PlayerFrame>();
	}

	public int getRound() {
		return round;
	}

	public Frame getNextFrame() {
		return nextFrame;
	}

	public void setNextFrame(Frame nextFrame) {
		this.nextFrame = nextFrame;
	}

	public boolean isLastFrame() {
		return round == MAX_FRAME_ROUND;
	}

	public void addPlayerFrame(PlayerFrame playerFrame) throws FrameException {
		if (hasPlayerFrame(playerFrame)) {
			throw new FrameException("Already exists a player frame for the player " + playerFrame.getPlayer());
		}

		this.playerFrames.add(playerFrame);
	}

	public Optional<PlayerFrame> getPlayerFrameByPlayer(String player) {
		return playerFrames.stream().filter(playerFrame -> playerFrame.getPlayer().equals(player)).findFirst();
	}

	public PlayerScore calculatePlayerScore(String player) {
		Optional<PlayerFrame> playerFrameopt = this.getPlayerFrameByPlayer(player);

		if (!playerFrameopt.isPresent()) {
			return PlayerScore.createAbsentPlayer(player);
		}

		PlayerFrame playerFrame = playerFrameopt.get();

		List<String> pinfalls = playerFrame.getPinsAsList();

		int chancesRemaining = ORDNARY_PLAYER_CHANCES;

		if (playerFrame.hasSpare() || playerFrame.hasStrike()) {
			chancesRemaining = SPECIAL_PLAYER_CHANCES;
		}

		int score = this.calculateScore(player, playerFrame, chancesRemaining, 0);

		PlayerScore playerScore = new PlayerScore(player, score, pinfalls);
		if (playerFrame.hasStrike()) {
			playerScore.makeStrike();
		}
		
		if (playerFrame.hasSpare()) {
			playerScore.makeSpare();
		}

		return playerScore;
	}

	private int calculateScore(String player, PlayerFrame playerFrame, int chances, int pinfalls) {
		if (playerFrame.getChances().size() < chances && Objects.nonNull(this.nextFrame)) {
			int totalPinfalls = pinfalls + playerFrame.getTotalPins();
			int remainingChances = chances - playerFrame.getChances().size();

			return this.nextFrame.calculateScore(player, this.nextFrame.getPlayerFrameByPlayer(player).orElse(null),
					remainingChances, totalPinfalls);
		}

		if (playerFrame.getChances().size() < chances) {
			return pinfalls + playerFrame.getTotalPins();
		}

		int totalPinfalls = pinfalls;
		for (int i = 0; i < chances; i++) {
			totalPinfalls += playerFrame.getChances().get(i).getPinsAsInt();
		}

		return totalPinfalls;
	}

	private boolean hasPlayerFrame(PlayerFrame playerFrame) {
		return getPlayerFrameByPlayer(playerFrame.getPlayer()).isPresent();
	}
}
