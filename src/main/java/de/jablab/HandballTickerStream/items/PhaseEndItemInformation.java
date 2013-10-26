package de.jablab.HandballTickerStream.items;

import org.json.simple.JSONObject;

import de.jablab.HandballTickerStream.MatchPhase;
import de.jablab.HandballTickerStream.StreamItemInformation;

public class PhaseEndItemInformation extends StreamItemInformation {

	private final MatchPhase before;

	private final MatchPhase after;

	private final PhaseEndSubType subType;

	private final JSONObject subObject;

	public PhaseEndItemInformation(final StreamItemInformation streamItemInfo,
			final MatchPhase before, final MatchPhase after,
			final PhaseEndSubType subType, final JSONObject subObject) {
		super(streamItemInfo.getPublished(), streamItemInfo.getTime(),
				streamItemInfo.getType(), streamItemInfo.getObject(),
				streamItemInfo.getMessage());
		this.before = before;
		this.after = after;
		this.subType = subType;
		this.subObject = subObject;
	}

	public MatchPhase getBefore() {
		return this.before;
	}

	public MatchPhase getAfter() {
		return this.after;
	}

	public PhaseEndSubType getSubType() {
		return this.subType;
	}

	public JSONObject getSubObject() {
		return this.subObject;
	}

}