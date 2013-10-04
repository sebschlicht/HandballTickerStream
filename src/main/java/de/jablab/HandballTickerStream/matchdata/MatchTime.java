package de.jablab.HandballTickerStream.matchdata;

import org.json.simple.JSONObject;

import de.jablab.HandballTickerStream.HandballTickerStream;
import de.jablab.HandballTickerStream.Streamable;
import de.jablab.HandballTickerStream.exceptions.MatchTimeFormatException;

/**
 * point in time of a match
 * 
 * @author sebschlicht
 * 
 */
public class MatchTime implements Streamable {

	/**
	 * minute of the match
	 */
	private int minute;

	/**
	 * phase the match is in
	 */
	private MatchPhase phase;

	/**
	 * create a new point in time of a match
	 * 
	 * @param minute
	 *            minute of the match
	 * @param phase
	 *            phase the match is in (<b>must not</b> be null)
	 */
	public MatchTime(final int minute, final MatchPhase phase) {
		this.minute = minute;
		this.phase = phase;

		if (phase == null) {
			throw new IllegalArgumentException("phase must not be null!");
		}
	}

	/**
	 * load match time from JSON object
	 * 
	 * @param matchTime
	 *            JSON match time object
	 * @throws MatchTimeFormatException
	 *             if the JSON object is not a valid match time object
	 */
	public MatchTime(final JSONObject matchTime)
			throws MatchTimeFormatException {
		final String sMinute = (String) matchTime
				.get(HandballTickerStream.MatchTime.KEY_MINUTE);
		if (sMinute != null) {
			try {
				this.minute = Integer.valueOf(sMinute);
			} catch (final NumberFormatException e) {
				throw new MatchTimeFormatException("field \""
						+ HandballTickerStream.MatchTime.KEY_MINUTE
						+ "\" is malformed: \"" + sMinute
						+ "\" is not a number");
			}

			final String sMatchPhase = (String) matchTime
					.get(HandballTickerStream.MatchTime.KEY_PHASE);
			if (sMatchPhase != null) {
				this.phase = MatchPhase.parseString(sMatchPhase);
				if (this.phase == null) {
					throw new MatchTimeFormatException("field \""
							+ HandballTickerStream.MatchTime.KEY_PHASE
							+ "\" is malformed: \"" + sMatchPhase
							+ "\" is not a match phase");
				}
			} else {
				throw new MatchTimeFormatException("field \""
						+ HandballTickerStream.MatchTime.KEY_PHASE
						+ "\" is missing");
			}
		} else {
			throw new MatchTimeFormatException("field \""
					+ HandballTickerStream.MatchTime.KEY_MINUTE
					+ "\" is missing");
		}
	}

	/**
	 * @return minute of the match
	 */
	public int getMinute() {
		return this.minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	/**
	 * @return phase the match is in
	 */
	public MatchPhase getPhase() {
		return this.phase;
	}

	public void setPhase(MatchPhase phase) {
		this.phase = phase;
	}

	@Override
	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		final JSONObject object = new JSONObject();
		object.put(HandballTickerStream.MatchTime.KEY_MINUTE,
				String.valueOf(this.minute));
		object.put(HandballTickerStream.MatchTime.KEY_PHASE,
				this.phase.toString());
		return object;
	}

	@Override
	public String toJSONString() {
		return this.toJSON().toJSONString();
	}

}