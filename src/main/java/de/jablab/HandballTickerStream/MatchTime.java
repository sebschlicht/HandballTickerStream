package de.jablab.HandballTickerStream;

import org.json.simple.JSONObject;

import de.jablab.HandballTickerStream.exceptions.MatchTimeFormatException;

/**
 * time information for a match
 * 
 * @author sebschlicht
 * 
 */
public class MatchTime {

	/**
	 * minute of the match
	 */
	private final int minute;

	/**
	 * phase the match is in
	 */
	private final MatchPhase phase;

	/**
	 * create a new match time object
	 * 
	 * @param matchTime
	 *            JSON object to be parsed
	 * @throws MatchTimeFormatException
	 *             if the match time object could not be parsed
	 */
	public MatchTime(final JSONObject matchTime)
			throws MatchTimeFormatException {
		final String sMinute = (String) matchTime
				.get(Stream.Keys.MatchTime.MINUTE);
		if (sMinute == null) {
			throw new MatchTimeFormatException("\""
					+ Stream.Keys.MatchTime.MINUTE + "\" is missing");
		} else {
			try {
				this.minute = Integer.parseInt(sMinute);
			} catch (final NumberFormatException e) {
				throw new MatchTimeFormatException("\"" + sMinute
						+ "\" is not a valid match minute as is not a number");
			}
		}

		final String sHalf = (String) matchTime.get(Stream.Keys.MatchTime.PHASE);
		if (sHalf == null) {
			throw new MatchTimeFormatException("\""
					+ Stream.Keys.MatchTime.PHASE + "\" is missing");
		} else {
			this.phase = MatchPhase.parseString(sHalf);
			if (this.phase == null) {
				throw new MatchTimeFormatException("\"" + sHalf
						+ "\" is not a valid match half value");
			}
		}
	}

	/**
	 * @return minute of the match
	 */
	public int getMinute() {
		return this.minute;
	}

	/**
	 * @return phase the match is in
	 */
	public MatchPhase getPhase() {
		return this.phase;
	}

}