package de.jablab.HandballTickerStream;

/**
 * match phase
 * 
 * @author sebschlicht
 * 
 */
public enum MatchPhase {

	/**
	 * first match half
	 */
	FIRST("1"),

	/**
	 * second match half
	 */
	SECOND("2");

	/**
	 * handball ticker stream value for the match phase
	 */
	private final String value;

	/**
	 * create a new match phase
	 * 
	 * @param value
	 *            handball ticker stream value for the match phase
	 */
	private MatchPhase(final String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}

	/**
	 * parse a String to a match phase
	 * 
	 * @param value
	 *            match phase stream value
	 * @return match phase having the stream value passed<br>
	 *         <b>null</b> if there is no match phase having such stream value
	 */
	public static MatchPhase parseString(final String value) {
		if (FIRST.value.equals(value)) {
			return FIRST;
		} else if (SECOND.value.equals(value)) {
			return SECOND;
		}

		return null;
	}

}