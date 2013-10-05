package de.jablab.HandballTickerStream;

import org.json.simple.JSONObject;

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

	/**
	 * load match time from JSON object
	 * 
	 * @param matchTime
	 *            match time JSON object
	 * @throws MatchTimeFormatException
	 *             if the JSON object is not a valid match time object
	 */
	public static MatchTime parseJSON(final JSONObject matchTime)
			throws MatchTimeFormatException {
		final int minute = parseMinute(matchTime);
		final MatchPhase phase = parsePhase(matchTime);
		return new MatchTime(minute, phase);
	}

	/**
	 * parse the minute field
	 * 
	 * @param matchTime
	 *            match time JSON object
	 * @return minute of the match
	 * @throws MatchTimeFormatException
	 *             if field missing or malformed
	 */
	private static int parseMinute(final JSONObject matchTime)
			throws MatchTimeFormatException {
		final String sMinute = (String) matchTime
				.get(HandballTickerStream.MatchTime.KEY_MINUTE);
		if (sMinute != null) {
			try {
				return Integer.valueOf(sMinute);
			} catch (final NumberFormatException e) {
				throw new MatchTimeFormatException("field \""
						+ HandballTickerStream.MatchTime.KEY_MINUTE
						+ "\" is malformed: \"" + sMinute
						+ "\" is not a number");
			}
		} else {
			throw new MatchTimeFormatException("field \""
					+ HandballTickerStream.MatchTime.KEY_MINUTE
					+ "\" is missing");
		}
	}

	/**
	 * parse the phase field
	 * 
	 * @param matchTime
	 *            match time JSON object
	 * @return phase the match is in
	 * @throws MatchTimeFormatException
	 *             if field missing or malformed
	 */
	private static MatchPhase parsePhase(final JSONObject matchTime)
			throws MatchTimeFormatException {
		final String sMatchPhase = (String) matchTime
				.get(HandballTickerStream.MatchTime.KEY_PHASE);
		if (sMatchPhase != null) {
			final MatchPhase phase = MatchPhase.parseString(sMatchPhase);
			if (phase != null) {
				return phase;
			} else {
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
	}

}