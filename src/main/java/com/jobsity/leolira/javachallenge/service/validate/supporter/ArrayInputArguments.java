package com.jobsity.leolira.javachallenge.service.validate.supporter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.jobsity.leolira.javachallenge.InputArguments;

public class ArrayInputArguments implements InputArguments<List<String>> {

	private List<String> inputArguments;
	
	public ArrayInputArguments(String[] inputArguments) {
		if (Objects.nonNull(inputArguments)) {
			this.inputArguments = Arrays.asList(inputArguments);
		}
	}
	
	@Override
	public List<String> getArguments() {
		return this.inputArguments;
	}

	@Override
	public boolean isEmpty() {
		return (Objects.isNull(inputArguments) || inputArguments.isEmpty());
	}

	@Override
	public int size() {
		if (this.isEmpty()) {
			return 0;
		}
		
		return inputArguments.size();
	}

}
