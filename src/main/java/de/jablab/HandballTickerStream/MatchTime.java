package de.jablab.HandballTickerStream;

import org.json.simple.JSONObject;

import de.jablab.HandballTickerStream.exceptions.MatchTimeFormatException;

/**
 * point in time of a match
 * 
 * @author sebschlicht
 * 
 */
public class MatchTime extends Streamable {

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
	public boolean equals(final Object o) {
		if (o == null) {
			return false;
		}
		if (o == this) {
			return true;
		}
		if (!o.getClass().equals(this.getClass())) {
			return false;
		}

		final MatchTime matchTime = (MatchTime) o;
		return ((this.minute == matchTime.getMinute()) && (this.phase == matchTime
				.getPhase()));
	}

	public static MatchTime parseJSON(final String jsonString)
			throws MatchTimeFormatException {
		final JSONObject matchTimeObject = parseJSONObject(jsonString);
		if (matchTimeObject != null) {
			return loadFromJSON(matchTimeObject);
		} else {
			throw new MatchTimeFormatException("\"" + jsonString
					+ "\" is not a JSON String");
		}
	}

	public static MatchTime loadFromJSON(final JSONObject matchTimeObject)
			throws MatchTimeFormatException {
		final int minute = parseMinute(matchTimeObject);
		final MatchPhase phase = parsePhase(matchTimeObject);
		return new MatchTime(minute, phase);
	}

	private static int parseMinute(final JSONObject matchTimeObject)
			throws MatchTimeFormatException {
		final String sMinute = (String) matchTimeObject
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

	private static MatchPhase parsePhase(final JSONObject matchTimeObject)
			throws MatchTimeFormatException {
		final String sMatchPhase = (String) matchTimeObject
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