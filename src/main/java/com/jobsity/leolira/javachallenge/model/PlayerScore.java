package com.jobsity.leolira.javachallenge.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlayerScore {
	private String player;
	private int score;
	private List<String> pinfalls;
	private boolean strike;
	private boolean spare;
	private boolean openFrame;

	public static PlayerScore createAbsentPlayer(String player) {
		return new PlayerScore(player, 0,
				Stream.of(Chance.FAULT_PINFALL, Chance.FAULT_PINFALL).collect(Collectors.toList()));
	}

	public PlayerScore(String player, int score) {
		this(player, score, new ArrayList<String>());
	}

	public PlayerScore(String player, int score, List<String> pinFalls) {
		this.player = player;
		this.score = score;
		this.pinfalls = pinFalls;
		this.openFrame = true;
	}

	public void addPinFall(String pinFall) {
		pinfalls.add(pinFall);
	}

	public String getPlayer() {
		return player;
	}

	public int getScore() {
		return score;
	}

	public List<String> getPinfalls() {
		return pinfalls;
	}
	
	public void makeStrike() {
		this.strike = true;
		this.openFrame = false;
	}
	
	public void makeSpare() {
		this.spare = true;
		this.openFrame = false;
	}

	public boolean isStrike() {
		return strike;
	}

	public boolean isSpare() {
		return spare;
	}

	public boolean isOpenFrame() {
		return openFrame;
	}
}
