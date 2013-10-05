package de.jablab.HandballTickerStream.items;

import org.json.simple.JSONObject;

import de.jablab.HandballTickerStream.HandballTickerStream;
import de.jablab.HandballTickerStream.matchdata.MatchPhase;

/**
 * match phase end stream item
 * 
 * @author sebschlicht
 * 
 */
public class PhaseEndItem {

	/**
	 * phase that ended
	 */
	private MatchPhase before;

	/**
	 * new phase that begun
	 */
	private MatchPhase after;

	/**
	 * sub type of the phase end
	 */
	private PhaseEndSubType subType;

	/**
	 * create a new match phase end stream item
	 * 
	 * @param before
	 *            phase that ended
	 * @param after
	 *            new phase that begun
	 */
	public PhaseEndItem(final MatchPhase before, final MatchPhase after) {
		this.before = before;
		this.after = after;
	}

	/**
	 * @return phase that ended
	 */
	public MatchPhase getBefore() {
		return this.before;
	}

	public void setBefore(MatchPhase before) {
		this.before = before;
	}

	/**
	 * @return new phase that begun
	 */
	public MatchPhase getAfter() {
		return this.after;
	}

	public void setAfter(MatchPhase after) {
		this.after = after;
	}

	/**
	 * @return sub type of the phase end
	 */
	public PhaseEndSubType getSubType() {
		return this.subType;
	}

	public void setSubType(PhaseEndSubType subType) {
		this.subType = subType;
	}

	protected MatchPhase parseBefore(final JSONObject phaseEndItem) {
		final String sBefore = (String) phaseEndItem
				.get(HandballTickerStream.StreamItem.PhaseEndItem.KEY_BEFORE);
		if (sBefore != null) {
			final MatchPhase before = MatchPhase.parseString(sBefore);
			if (before != null) {
				return before;
			} else {
				// TODO: throw new PhaseEndItemFormatException();
			}
		} else {
			// TODO: throw new PhaseEndItemFormatException();
		}

		// TODO: remove
		return null;
	}

}