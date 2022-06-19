package com.jobsity.leolira.javachallenge.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.jobsity.leolira.javachallenge.model.Chance;

public class LambdaUtilTest {

	@Test
	public void itReturnAListWithDistinctValues() {
		List<Chance> values = Arrays.asList(new Chance("1"), new Chance("2"), new Chance("1"), new Chance("3"));

		List<Chance> result = values.stream().filter(LambdaUtil.distinctByKey(Chance::getPins))
				.collect(Collectors.toList());
		
		assertThat(result.size()).isEqualTo(3);
		assertThat(result.get(0).getPins()).isEqualTo("1");
		assertThat(result.get(1).getPins()).isEqualTo("2");
		assertThat(result.get(2).getPins()).isEqualTo("3");
	}
}
