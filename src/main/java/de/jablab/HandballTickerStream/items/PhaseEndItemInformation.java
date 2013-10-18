package de.jablab.HandballTickerStream.items;

import java.util.Date;

import org.json.simple.JSONObject;

import de.jablab.HandballTickerStream.MatchPhase;
import de.jablab.HandballTickerStream.MatchTime;
import de.jablab.HandballTickerStream.StreamItemInformation;
import de.jablab.HandballTickerStream.StreamItemType;

/**
 * wrapper for data to create any phase end stream item
 * 
 * @author sebschlicht
 * 
 */
public class PhaseEndItemInformation extends StreamItemInformation {

	/**
	 * phase that ended
	 */
	private final MatchPhase before;

	/**
	 * new phase that began
	 */
	private final MatchPhase after;

	/**
	 * sub type of the phase end item
	 */
	private final PhaseEndSubType subType;

	/**
	 * create a new wrapper for data to create any phase end stream item
	 * 
	 * @param published
	 *            exact time when published
	 * @param time
	 *            match time when published<br>
	 *            considered to be the time of the happening
	 * @param type
	 *            type of the item
	 * 
	 * @param message
	 *            message displayed instead of a generated value
	 * @param before
	 *            phase that ended
	 * @param after
	 *            new phase that began
	 * @param subType
	 *            sub type of the phase end item
	 * @param object
	 *            phase end stream item sub type specific data
	 */
	public PhaseEndItemInformation(final Date published, final MatchTime time,
			final String message, final MatchPhase before,
			final MatchPhase after, final PhaseEndSubType subType,
			final JSONObject object) {
		super(published, time, StreamItemType.PHASE_END, object, message);
		this.before = before;
		this.after = after;
		this.subType = subType;
	}

	/**
	 * @return phase that ended
	 */
	public MatchPhase getBefore() {
		return this.before;
	}

	/**
	 * @return new phase that began
	 */
	public MatchPhase getAfter() {
		return this.after;
	}

	/**
	 * @return sub type of the phase end item
	 */
	public PhaseEndSubType getSubType() {
		return this.subType;
	}

}