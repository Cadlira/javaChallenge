package com.jobsity.leolira.javachallenge.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jobsity.leolira.javachallenge.ProcessResource;
import com.jobsity.leolira.javachallenge.exception.BowlingException;
import com.jobsity.leolira.javachallenge.exception.ProcessScoreException;
import com.jobsity.leolira.javachallenge.model.FrameScore;
import com.jobsity.leolira.javachallenge.model.PlayerScore;

@ExtendWith(MockitoExtension.class)
public class PrintGameResultTest {

	private PrintStream oldSystemOut;
	private ByteArrayOutputStream currentSystemOut;

	@BeforeEach
	public void setUp() {
		oldSystemOut = System.out;

		currentSystemOut = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(currentSystemOut, true);
		System.setOut(ps);
	}

	@AfterEach
	public void tearDown() throws IOException {
		System.setOut(oldSystemOut);

		currentSystemOut.close();
	}

	@Test
	public void itPrintsAPerfectGame(@Mock ProcessResource<List<FrameScore>> gameScoreProcess)
			throws BowlingException, IOException {
		List<FrameScore> frameScores = getPerfectFrameScores();

		Mockito.when(gameScoreProcess.getProcessedResult()).thenReturn(frameScores);

		PrintGameResult printGameResult = new PrintGameResult(gameScoreProcess);

		printGameResult.print();

		assertThat(currentSystemOut.toString()).isEqualTo(getPerfectOutput());
	}
	
	@Test
	public void itPrintsASpareGame(@Mock ProcessResource<List<FrameScore>> gameScoreProcess)
			throws BowlingException, IOException {
		List<FrameScore> frameScores = getSpareFrameScores();

		Mockito.when(gameScoreProcess.getProcessedResult()).thenReturn(frameScores);

		PrintGameResult printGameResult = new PrintGameResult(gameScoreProcess);

		printGameResult.print();

		assertThat(currentSystemOut.toString()).isEqualTo(getSpareOutput());
	}
	
	@Test
	public void itPrintsAOpenFrameGame(@Mock ProcessResource<List<FrameScore>> gameScoreProcess)
			throws BowlingException, IOException {
		List<FrameScore> frameScores = getOpenFrameScores();

		Mockito.when(gameScoreProcess.getProcessedResult()).thenReturn(frameScores);

		PrintGameResult printGameResult = new PrintGameResult(gameScoreProcess);

		printGameResult.print();

		assertThat(currentSystemOut.toString()).isEqualTo(getOpenFrameOutput());
	}
	
	@Test
	public void itGetsPrintableLine(@Mock ProcessResource<List<FrameScore>> gameScoreProcess)
			throws BowlingException, IOException {
		List<FrameScore> frameScores = getPerfectFrameScores();

		Mockito.when(gameScoreProcess.getProcessedResult()).thenReturn(frameScores);

		PrintGameResult printGameResult = new PrintGameResult(gameScoreProcess);

		printGameResult.print();

		assertThat(printGameResult.getPrintableLines().size()).isEqualTo(4);
	}
	
	@Test
	public void itThrowsProcessScoreExceptionWhenPrintableLinesIsNull(@Mock ProcessResource<List<FrameScore>> gameScoreProcess)
			throws BowlingException, IOException {
		PrintGameResult printGameResult = new PrintGameResult(gameScoreProcess);
		
		ProcessScoreException exception = assertThrows(ProcessScoreException.class, () -> printGameResult.getPrintableLines());

		assertThat(exception.getMessage()).isEqualTo("The printable lines have not yet been calculated");
	}

	private List<FrameScore> getPerfectFrameScores() {
		List<FrameScore> frameScores = new ArrayList<FrameScore>();
		for (int i = 1; i <= 9; i++) {
			FrameScore frameScore = new FrameScore(i);
			PlayerScore playScore = new PlayerScore("Player", 30, Arrays.asList("10"));
			playScore.makeStrike();
			frameScore.addPlayerScore(playScore);
			frameScores.add(frameScore);
		}

		FrameScore frameScore = new FrameScore(10);
		PlayerScore playScore = new PlayerScore("Player", 30, Arrays.asList("10", "10", "10"));
		playScore.makeStrike();
		frameScore.addPlayerScore(playScore);
		frameScores.add(frameScore);

		return frameScores;
	}

	private String getPerfectOutput() throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos, true);

		ps.println();
		ps.println();
		ps.println();

		ps.println("Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10");
		ps.println("Player");
		ps.println("Pinfalls\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\tX\tX\tX");
		ps.println("Score\t\t30\t\t60\t\t90\t\t120\t\t150\t\t180\t\t210\t\t240\t\t270\t\t300");

		ps.println();
		ps.println();
		ps.println();

		String result = baos.toString();

		baos.close();

		return result;
	}
	
	private List<FrameScore> getSpareFrameScores() {
		List<FrameScore> frameScores = new ArrayList<FrameScore>();
		for (int i = 1; i <= 9; i++) {
			FrameScore frameScore = new FrameScore(i);
			PlayerScore playScore = new PlayerScore("Player", 30, Arrays.asList("10"));
			playScore.makeStrike();
			frameScore.addPlayerScore(playScore);
			frameScores.add(frameScore);
		}

		FrameScore frameScore = new FrameScore(10);
		PlayerScore playScore = new PlayerScore("Player", 20, Arrays.asList("5", "5", "10"));
		playScore.makeSpare();
		frameScore.addPlayerScore(playScore);
		frameScores.add(frameScore);

		return frameScores;
	}
	
	private String getSpareOutput() throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos, true);

		ps.println();
		ps.println();
		ps.println();

		ps.println("Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10");
		ps.println("Player");
		ps.println("Pinfalls\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t5\t/\tX");
		ps.println("Score\t\t30\t\t60\t\t90\t\t120\t\t150\t\t180\t\t210\t\t240\t\t270\t\t290");

		ps.println();
		ps.println();
		ps.println();

		String result = baos.toString();

		baos.close();

		return result;
	}
	
	private List<FrameScore> getOpenFrameScores() {
		List<FrameScore> frameScores = new ArrayList<FrameScore>();
		
		FrameScore frameScore = new FrameScore(1);
		PlayerScore playScore = new PlayerScore("Player", 9, Arrays.asList("5", "4"));		
		frameScore.addPlayerScore(playScore);
		frameScores.add(frameScore);
		
		for (int i = 2; i <= 9; i++) {
			FrameScore frameScorei = new FrameScore(i);
			PlayerScore playScorei = new PlayerScore("Player", 30, Arrays.asList("10"));
			playScorei.makeStrike();
			frameScorei.addPlayerScore(playScorei);
			frameScores.add(frameScorei);
		}
		
		FrameScore frameScorex = new FrameScore(10);
		PlayerScore playScorex = new PlayerScore("Player", 30, Arrays.asList("10", "10", "10"));
		playScorex.makeStrike();
		frameScorex.addPlayerScore(playScorex);
		frameScores.add(frameScorex);

		return frameScores;
	}
	
	private String getOpenFrameOutput() throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos, true);

		ps.println();
		ps.println();
		ps.println();

		ps.println("Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10");
		ps.println("Player");
		ps.println("Pinfalls\t5\t4\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\tX\tX\tX");
		ps.println("Score\t\t9\t\t39\t\t69\t\t99\t\t129\t\t159\t\t189\t\t219\t\t249\t\t279");

		ps.println();
		ps.println();
		ps.println();

		String result = baos.toString();

		baos.close();

		return result;
	}
}
