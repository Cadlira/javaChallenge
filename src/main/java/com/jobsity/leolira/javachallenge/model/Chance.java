package com.jobsity.leolira.javachallenge.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class Chance {
	public static final String FAULT_PINFALL = "F";

	private final String pins;

	public Chance(String pins) {
		this.pins = pins;
	}
	
	public String getPins() {
		return pins;
	}

	public int getPinsAsInt() {
		try {
			return Integer.parseInt(pins);
		} catch (NumberFormatException exception) {
			if (FAULT_PINFALL.equals(pins)) {
				return 0;
			}
			
			return -1;
		}
	}
	
	public boolean isEmpty() {
		return StringUtils.isEmpty(pins);
	}
	
	public boolean isNumber() {
		return NumberUtils.isCreatable(pins);
	}
	
	public boolean isOutOfRange() {
		return (isNumber() && (getPinsAsInt() < 0 || getPinsAsInt() > 10));
	}
	
	public boolean hasInvalidValue() {
		return (!isNumber() && !FAULT_PINFALL.equals(pins));
	}
}
