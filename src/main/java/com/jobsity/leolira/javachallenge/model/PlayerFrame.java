package com.jobsity.leolira.javachallenge.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.jobsity.leolira.javachallenge.exception.FrameException;

public class PlayerFrame {
	private static final int SPARE_CHANCES = 2;
	private static final int MAX_PINFALLS_INT = 10;
	private static final int FINAL_FRAME_ROUND = 10;
	private static final int FINAL_FRAME_MAX_CHANCES = 3;
	private static final int ORDINARY_FRAME_MAX_CHANCES = 2;

	private final int frameRound;
	private final String player;
	private final List<Chance> chances;

	public PlayerFrame(String player, int frameRound) throws FrameException {
		if (frameRound < 1 || frameRound > 10) {
			throw new FrameException("There must be exactly 10 frames per game");
		}

		this.player = player;
		this.frameRound = frameRound;
		this.chances = new ArrayList<Chance>();
	}

	public void addChance(Chance chance) throws FrameException {
		if (isCompleted()) {
			throw new FrameException(String.format("The frame is already completed for player '%s'", player));
		}

		this.chances.add(chance);
	}

	public boolean isCompleted() {
		return isOrdnaryFrameCompleted() || isFinalFrameCompleted();
	}

	public boolean hasStrike() {
		if (frameRound < FINAL_FRAME_ROUND) {
			return chances.stream().anyMatch(pins -> pins.getPinsAsInt() == MAX_PINFALLS_INT);
		}

		return hasStrikeOnFinalFrame();
	}

	public boolean hasSpare() {
		return (chances.size() >= SPARE_CHANCES
				&& (chances.get(0).getPinsAsInt() + chances.get(1).getPinsAsInt()) == MAX_PINFALLS_INT);
	}

	public List<String> getPinsAsList() {
		return this.chances.stream().map(chance -> chance.getPins()).collect(Collectors.toList());
	}

	public int getTotalPins() {
		return this.chances.stream().mapToInt(chance -> chance.getPinsAsInt()).sum();
	}

	public String getPlayer() {
		return player;
	}

	public List<Chance> getChances() {
		return this.chances;
	}

	private boolean isOrdnaryFrameCompleted() {
		if (frameRound == FINAL_FRAME_ROUND) {
			return false;
		}

		return hasStrike() || chances.size() == ORDINARY_FRAME_MAX_CHANCES;
	}

	private boolean isFinalFrameCompleted() {
		if (frameRound != FINAL_FRAME_ROUND) {
			return false;
		}

		if ((hasStrike() || hasSpare()) && chances.size() < FINAL_FRAME_MAX_CHANCES) {
			return false;
		}

		return chances.size() == FINAL_FRAME_MAX_CHANCES;
	}

	private boolean hasStrikeOnFinalFrame() {

		if (chances.size() < FINAL_FRAME_MAX_CHANCES) {
			return false;
		}

		return chances.get(0).getPinsAsInt() == MAX_PINFALLS_INT || chances.get(2).getPinsAsInt() == MAX_PINFALLS_INT;
	}
}
