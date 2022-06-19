package com.jobsity.leolira.javachallenge.service.validate;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jobsity.leolira.javachallenge.InputArguments;
import com.jobsity.leolira.javachallenge.ValidateResource;
import com.jobsity.leolira.javachallenge.exception.BowlingException;
import com.jobsity.leolira.javachallenge.exception.InputArgumentsException;

public class ValidateInputArguments implements ValidateResource<String>{
	private static final int MAX_ARGUMENTS = 1;

	private final InputArguments<List<String>> inputArguments;
	
	private String playerChanceFileName;
	
	public ValidateInputArguments(InputArguments<List<String>> inputArguments) {
		this.inputArguments = inputArguments;
	}
	
	@Override
	public void validate() throws BowlingException {
		validateEmptyArgs();

		validateInvalidArgsSize();
		
		this.playerChanceFileName = this.inputArguments.getArguments().get(0);
	}

	@Override
	public String getResource() throws BowlingException {
		validateEmptyFileName();
		
		return this.playerChanceFileName;
	}

	private void validateEmptyFileName() throws InputArgumentsException {
		if (StringUtils.isEmpty(playerChanceFileName)) {
			throw new InputArgumentsException("The input arguments have not yet been validated");
		}
	}
	
	private void validateInvalidArgsSize() throws InputArgumentsException {
		if (inputArguments.size() > MAX_ARGUMENTS) {
			throw new InputArgumentsException("Only one player chance file should be provided");
		}
	}

	private void validateEmptyArgs() throws InputArgumentsException {
		if (inputArguments.isEmpty()) {
			throw new InputArgumentsException("A player chance file must be provided");
		}
	}
}
