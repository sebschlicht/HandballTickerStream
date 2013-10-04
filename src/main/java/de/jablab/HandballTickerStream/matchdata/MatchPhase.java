package de.jablab.HandballTickerStream.matchdata;

/**
 * phases a match can be in
 * 
 * @author sebschlicht
 * 
 */
public enum MatchPhase {

	/**
	 * not started yet
	 */
	WARMUP("warmup"),

	/**
	 * first half
	 */
	FIRST("first"),

	/**
	 * paused
	 */
	PAUSED("paused"),

	/**
	 * paused due to half time
	 */
	HALF_TIME("half-time"),

	/**
	 * second half
	 */
	SECOND("second"),

	/**
	 * finished
	 */
	FINISHED("finished");

	/**
	 * match phase String value
	 */
	private final String value;

	/**
	 * add a new match phase
	 * 
	 * @param value
	 *            match phase String value
	 */
	private MatchPhase(final String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}

	/**
	 * parse String value to match phase
	 * 
	 * @param value
	 *            String value of a match phase
	 * @return match phase having the String value passed<br>
	 *         <b>null</b> if there is no match phase with such String value
	 */
	public static MatchPhase parseString(final String value) {
		if (WARMUP.toString().equals(value)) {
			return WARMUP;
		} else if (FIRST.toString().equals(value)) {
			return FIRST;
		} else if (PAUSED.toString().equals(value)) {
			return PAUSED;
		} else if (HALF_TIME.toString().equals(value)) {
			return HALF_TIME;
		} else if (SECOND.toString().equals(value)) {
			return SECOND;
		} else if (FINISHED.toString().equals(value)) {
			return FINISHED;
		}

		// there is no match phase with such String value
		return null;
	}

}