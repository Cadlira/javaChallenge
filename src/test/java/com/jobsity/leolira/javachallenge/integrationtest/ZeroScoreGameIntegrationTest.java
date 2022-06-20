package com.jobsity.leolira.javachallenge.integrationtest;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.jobsity.leolira.javachallenge.Bowling;

@Tag("INTEGRATION_TESTS")
public class ZeroScoreGameIntegrationTest {
	private PrintStream oldSystemOut;
	private ByteArrayOutputStream currentSystemOut;

	@BeforeEach
	public void setUp() throws UnsupportedEncodingException {
		oldSystemOut = System.out;

		currentSystemOut = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(currentSystemOut, true, "UTF-8");
		System.setOut(ps);
	}

	@AfterEach
	public void tearDown() throws IOException {
		System.setOut(oldSystemOut);

		currentSystemOut.close();
	}

	@Test
	public void itPerformsAZeroScoreGame() throws UnsupportedEncodingException, IOException {
		String[] args = {"src/test/resources/positive/zero-score.txt"};
		
		Bowling.main(args);
		
		assertThat(currentSystemOut.toString("UTF-8")).isEqualTo(getZeroScoreOutput());
	}

	private String getZeroScoreOutput() throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos, true, "UTF-8");

		ps.println();
		ps.println();
		ps.println();

		ps.println("Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10");
		ps.println("Jeff");
		ps.println("Pinfalls\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0");
		ps.println("Score\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0");

		ps.println();
		ps.println();
		ps.println();

		String result = baos.toString("UTF-8");

		baos.close();

		return result;
	}
}
