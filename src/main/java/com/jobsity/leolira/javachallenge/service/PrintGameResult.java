package com.jobsity.leolira.javachallenge.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

import com.jobsity.leolira.javachallenge.PrintResourceResult;
import com.jobsity.leolira.javachallenge.ProcessResource;
import com.jobsity.leolira.javachallenge.exception.BowlingException;
import com.jobsity.leolira.javachallenge.exception.ProcessScoreException;
import com.jobsity.leolira.javachallenge.model.FrameScore;
import com.jobsity.leolira.javachallenge.model.PlayerScore;

public class PrintGameResult implements PrintResourceResult<List<String>> {
	private static final String MAX_PINFALLS_STR = "10";
	private static final int FINAL_FRAME_ROUND = 10;
	private static final int MIN_SCORE = 0;
	private static final String HEADER_LINE = "Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10";
	private static final String SCORE = "Score";
	private static final String TAB = "\t";
	private static final String DOUBLE_TAB = "\t\t";
	private static final String STRIKE = "X";
	private static final String SPARE = "/";

	private final ProcessResource<List<FrameScore>> gameScoreProcess;

	private List<String> printableLines;

	public PrintGameResult(ProcessResource<List<FrameScore>> gameScoreProcess) {
		this.gameScoreProcess = gameScoreProcess;
	}

	@Override
	public List<String> getPrintableLines() throws BowlingException {
		if (Objects.isNull(printableLines)) {
			throw new ProcessScoreException("The printable lines have not yet been calculated");
		}

		return this.printableLines;
	}

	@Override
	public void print() throws BowlingException {
		this.calculatePrintableLines(gameScoreProcess.getProcessedResult());

		printEmptyLines(3);

		printableLines.forEach(System.out::println);

		printEmptyLines(3);
	}

	private void calculatePrintableLines(List<FrameScore> processedResult) {
		this.printableLines = new ArrayList<String>();
		this.printableLines.add(HEADER_LINE);

		Map<String, StringBuilder> playerLineMap = new HashMap<String, StringBuilder>();
		Map<String, StringBuilder> pinfallsLineMap = new HashMap<String, StringBuilder>();
		Map<String, StringBuilder> scoreLineMap = new HashMap<String, StringBuilder>();
		Map<String, Integer> scoreMap = new HashMap<String, Integer>();

		for (FrameScore frameScore : processedResult) {
			for (PlayerScore playerScore : frameScore.getPlayerScores()) {
				calculatePlayers(playerLineMap, playerScore);

				calculatePinfalls(pinfallsLineMap, playerScore, frameScore.getFrameRound());

				calculateScores(scoreLineMap, scoreMap, playerScore);

				if (frameScore.getFrameRound() == FINAL_FRAME_ROUND) {
					this.printableLines.add(playerLineMap.get(playerScore.getPlayer()).toString());
					this.printableLines.add(pinfallsLineMap.get(playerScore.getPlayer()).toString());
					this.printableLines.add(scoreLineMap.get(playerScore.getPlayer()).toString());
				}
			}
		}

	}

	private void calculatePlayers(Map<String, StringBuilder> playerLineMap, PlayerScore playerScore) {
		if (Objects.isNull(playerLineMap.get(playerScore.getPlayer()))) {
			playerLineMap.put(playerScore.getPlayer(), new StringBuilder(playerScore.getPlayer()));
		}
	}

	private void calculatePinfalls(Map<String, StringBuilder> pinfallsLineMap, PlayerScore playerScore,
			int frameRound) {
		if (Objects.isNull(pinfallsLineMap.get(playerScore.getPlayer()))) {
			pinfallsLineMap.put(playerScore.getPlayer(), new StringBuilder("Pinfalls"));
		}

		if (playerScore.isStrike()) {
			strikePinfalls(pinfallsLineMap, playerScore, frameRound);
		}

		if (playerScore.isSpare()) {
			sparePinfalls(pinfallsLineMap, playerScore, frameRound);
		}

		if (playerScore.isOpenFrame()) {
			openFramePinfalls(pinfallsLineMap, playerScore);
		}
	}

	private void openFramePinfalls(Map<String, StringBuilder> pinfallsLineMap, PlayerScore playerScore) {
		pinfallsLineMap.get(playerScore.getPlayer()).append(TAB).append(playerScore.getPinfalls().get(0));
		pinfallsLineMap.get(playerScore.getPlayer()).append(TAB).append(playerScore.getPinfalls().get(1));
	}

	private void sparePinfalls(Map<String, StringBuilder> pinfallsLineMap, PlayerScore playerScore, int frameRound) {
		pinfallsLineMap.get(playerScore.getPlayer()).append(TAB).append(playerScore.getPinfalls().get(0));
		pinfallsLineMap.get(playerScore.getPlayer()).append(TAB).append(SPARE);

		if (frameRound == FINAL_FRAME_ROUND) {
			pinfallsLineMap.get(playerScore.getPlayer()).append(TAB)
					.append(getStrikeSymbol(playerScore.getPinfalls().get(2)));
		}
	}

	private void strikePinfalls(Map<String, StringBuilder> pinfallsLineMap, PlayerScore playerScore, int frameRound) {
		if (frameRound == FINAL_FRAME_ROUND) {
			pinfallsLineMap.get(playerScore.getPlayer()).append(TAB)
					.append(getStrikeSymbol(playerScore.getPinfalls().get(0)));
			pinfallsLineMap.get(playerScore.getPlayer()).append(TAB)
					.append(getStrikeSymbol(playerScore.getPinfalls().get(1)));
			pinfallsLineMap.get(playerScore.getPlayer()).append(TAB)
					.append(getStrikeSymbol(playerScore.getPinfalls().get(2)));
		} else {
			pinfallsLineMap.get(playerScore.getPlayer()).append(DOUBLE_TAB).append(STRIKE);
		}
	}

	private String getStrikeSymbol(String pinfall) {
		if (MAX_PINFALLS_STR.equals(pinfall)) {
			return STRIKE;
		}

		return pinfall;
	}

	private void calculateScores(Map<String, StringBuilder> scoreLineMap, Map<String, Integer> scoreMap,
			PlayerScore playerScore) {
		if (Objects.isNull(scoreLineMap.get(playerScore.getPlayer()))) {
			scoreLineMap.put(playerScore.getPlayer(), new StringBuilder(SCORE));
		}

		if (Objects.isNull(scoreMap.get(playerScore.getPlayer()))) {
			scoreMap.put(playerScore.getPlayer(), MIN_SCORE);
		}

		int currentScore = scoreMap.get(playerScore.getPlayer()) + playerScore.getScore();
		scoreMap.put(playerScore.getPlayer(), currentScore);
		scoreLineMap.get(playerScore.getPlayer()).append(DOUBLE_TAB).append(currentScore);
	}

	private void printEmptyLines(int lines) {
		IntStream.range(0, lines).forEach(n -> System.out.println());
	}
}
