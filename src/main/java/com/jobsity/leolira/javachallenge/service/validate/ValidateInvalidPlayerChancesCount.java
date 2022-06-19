package com.jobsity.leolira.javachallenge.service.validate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.jobsity.leolira.javachallenge.InnerValidate;
import com.jobsity.leolira.javachallenge.exception.BowlingException;
import com.jobsity.leolira.javachallenge.exception.PlayerChanceException;
import com.jobsity.leolira.javachallenge.model.PlayerChance;

public class ValidateInvalidPlayerChancesCount implements InnerValidate<List<PlayerChance>> {
	private static final int MAX_ORD_FRAME_CHANCES_COUNT = 18;
	private static final int MIN_FINAL_FRAME_CHANCES_COUNT = 2;
	private static final int MAX_FINAL_FRAME_CHANCES_COUNT = 3;

	@Override
	public void validate(List<PlayerChance> playerChances) throws BowlingException {
		Map<String, List<PlayerChance>> chancesByPlayer = playerChances.stream()
				.collect(Collectors.groupingBy(PlayerChance::getPlayer));

		for (Map.Entry<String, List<PlayerChance>> entry : chancesByPlayer.entrySet()) {

			List<PlayerChance> chances = entry.getValue();

			int ordinaryFramesChancesCount = getOrdinaryChancesCount(chances);

			int maxPlayerChancesSize = ordinaryFramesChancesCount + MAX_FINAL_FRAME_CHANCES_COUNT;
			int minPlayerChancesSize = ordinaryFramesChancesCount + MIN_FINAL_FRAME_CHANCES_COUNT;

			if (isChancesCountOutOfEdges(chances, maxPlayerChancesSize, minPlayerChancesSize)
					|| hasInvalidChancesCountForFinalFrameStrike(chances, ordinaryFramesChancesCount,
							maxPlayerChancesSize)
					|| hasInvalidChancesCountForFinalFrameSpare(chances, ordinaryFramesChancesCount,
							maxPlayerChancesSize)) {
				throw new PlayerChanceException(
						String.format("The player chances count is invalid fo player '%s'", entry.getKey()));
			}
		}
	}

	private int getOrdinaryChancesCount(List<PlayerChance> chances) {
		int ordinaryChancesCount = MAX_ORD_FRAME_CHANCES_COUNT;
		for (int i = 0; i < ordinaryChancesCount; i++) {
			if (i < chances.size() && chances.get(i).isStrike()) {
				ordinaryChancesCount--;
			}
		}
		return ordinaryChancesCount;
	}
	
	private boolean hasInvalidChancesCountForFinalFrameSpare(List<PlayerChance> chances, int ordinaryFramesChancesCount,
			int maxPlayerChancesSize) {
		return (chances.get(ordinaryFramesChancesCount).getPinsAsInt()
				+ chances.get(ordinaryFramesChancesCount + 1).getPinsAsInt()) < 10
				&& chances.size() == maxPlayerChancesSize;
	}

	private boolean hasInvalidChancesCountForFinalFrameStrike(List<PlayerChance> chances,
			int ordinaryFramesChancesCount, int maxPlayerChancesSize) {
		return chances.get(ordinaryFramesChancesCount).isStrike() && chances.size() != maxPlayerChancesSize;
	}

	private boolean isChancesCountOutOfEdges(List<PlayerChance> chances, int maxPlayerChancesSize,
			int minPlayerChancesSize) {
		return chances.size() < minPlayerChancesSize || chances.size() > maxPlayerChancesSize;
	}
}
