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
public class PerfectGameIntegrationTest {
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
	public void itPerformsAPerfectGame() throws UnsupportedEncodingException, IOException {
		String[] args = {"src/test/resources/positive/perfect.txt"};
		
		Bowling.main(args);
		
		assertThat(currentSystemOut.toString("UTF-8")).isEqualTo(getPerfectOutput());
	}

	private String getPerfectOutput() throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos, true, "UTF-8");

		ps.println();
		ps.println();
		ps.println();

		ps.println("Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10");
		ps.println("Carl");
		ps.println("Pinfalls\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\tX\tX\tX");
		ps.println("Score\t\t30\t\t60\t\t90\t\t120\t\t150\t\t180\t\t210\t\t240\t\t270\t\t300");

		ps.println();
		ps.println();
		ps.println();

		String result = baos.toString("UTF-8");

		baos.close();

		return result;
	}
}
