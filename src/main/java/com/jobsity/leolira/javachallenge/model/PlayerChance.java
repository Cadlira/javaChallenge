package com.jobsity.leolira.javachallenge.model;

import static com.jobsity.leolira.javachallenge.util.CommonContants.MAX_PINFALLS_INT;
import org.apache.commons.lang3.StringUtils;

public class PlayerChance {
	private final String player;
	private final Chance pins;
	
	private String invalidCause;

	public PlayerChance(String player, String pins) {
		this.player = player;
		this.pins = new Chance(pins);
	}

	public String getPlayer() {
		return player;
	}

	public Chance getPins() {
		return pins;
	}

	public String getInvalidCause() {
		return invalidCause;
	}

	public boolean isValid() {
		return !isPlayerInvalid() && !isPinsInvalid();
	}
	
	public int getPinsAsInt() {
		return pins.getPinsAsInt();
	}

	public boolean isStrike() {
		return (getPinsAsInt() == MAX_PINFALLS_INT);
	}

	private boolean isPlayerInvalid() {
		if (StringUtils.isEmpty(player)) {
			this.invalidCause = "The player is empty";
			return true;
		}

		return false;
	}

	private boolean isPinsInvalid() {
		if (pins.isEmpty()) {
			this.invalidCause = "The pins attribute is empty";
			return true;
		}

		if (pins.hasInvalidValue()) {
			this.invalidCause = "The pins attribute has a invalid value";
			return true;
		}

		if (pins.isOutOfRange()) {
			this.invalidCause = "The pins attribute is less than 0 or greater than 10";
			return true;
		}

		return false;
	}
}
