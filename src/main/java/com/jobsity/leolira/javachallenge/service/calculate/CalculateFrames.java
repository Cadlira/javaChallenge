package com.jobsity.leolira.javachallenge.service.calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.jobsity.leolira.javachallenge.CalculateResource;
import com.jobsity.leolira.javachallenge.exception.BowlingException;
import com.jobsity.leolira.javachallenge.exception.FrameException;
import com.jobsity.leolira.javachallenge.model.Frame;
import com.jobsity.leolira.javachallenge.model.PlayerChance;
import com.jobsity.leolira.javachallenge.model.PlayerFrame;

public class CalculateFrames implements CalculateResource<List<Frame>, List<PlayerChance>> {

	@Override
	public List<Frame> calculate(List<PlayerChance> playerChances) throws BowlingException {

		List<Frame> frames = new ArrayList<Frame>();
		frames.add(new Frame(1));

		for (PlayerChance playerChance : playerChances) {
			this.configurePlayerChance(playerChance, frames);
		}

		return frames;
	}

	private void configurePlayerChance(PlayerChance playerChance, List<Frame> frames) throws FrameException {
		Frame frame = frames.get(frames.size() - 1);
		
		Optional<PlayerFrame> playerFrameOptional = frame.getPlayerFrameByPlayer(playerChance.getPlayer());

		PlayerFrame playerFrame = null;

		if (!playerFrameOptional.isPresent()) {
			playerFrame = addNewPlayerFrame(playerChance.getPlayer(), frame);
		} else {
			playerFrame = playerFrameOptional.get();

			if (playerFrame.isCompleted()) {
				Frame newFrame = addNewFrame(frame, frames);
				playerFrame = addNewPlayerFrame(playerChance.getPlayer(), newFrame);
			}
		}

		playerFrame.addChance(playerChance.getPins());		
	}

	private Frame addNewFrame(Frame frame, List<Frame> frames) throws FrameException {
		Frame newFrame = new Frame(frame.getRound() + 1);

		frame.setNextFrame(newFrame);

		frames.add(newFrame);
		return newFrame;
	}

	private PlayerFrame addNewPlayerFrame(String player, Frame frame) throws FrameException {
		PlayerFrame playerFrame;
		playerFrame = new PlayerFrame(player, frame.getRound());
		
		frame.addPlayerFrame(playerFrame);
		
		return playerFrame;
	}
}
