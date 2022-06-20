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
public class OrdinaryGameIntegrationTest {
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
		String[] args = {"src/test/resources/positive/scores.txt"};
		
		Bowling.main(args);
		
		assertThat(currentSystemOut.toString("UTF-8")).isEqualTo(getOrdinaryOutput());
	}

	private String getOrdinaryOutput() throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos, true, "UTF-8");

		ps.println();
		ps.println();
		ps.println();

		ps.println("Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10");
		ps.println("Jeff");
		ps.println("Pinfalls\t\tX\t7\t/\t9\t0\t\tX\t0\t8\t8\t/\tF\t6\t\tX\t\tX\tX\t8\t1");
		ps.println("Score\t\t20\t\t39\t\t48\t\t66\t\t74\t\t84\t\t90\t\t120\t\t148\t\t167");
		
		ps.println("John");
		ps.println("Pinfalls\t3\t/\t6\t3\t\tX\t8\t1\t\tX\t\tX\t9\t0\t7\t/\t4\t4\tX\t9\t0");
		ps.println("Score\t\t16\t\t25\t\t44\t\t53\t\t82\t\t101\t\t110\t\t124\t\t132\t\t151");

		ps.println();
		ps.println();
		ps.println();

		String result = baos.toString("UTF-8");

		baos.close();

		return result;
	}
}
