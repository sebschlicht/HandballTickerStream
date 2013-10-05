package de.jablab.HandballTickerStream;


/**
 * types a stream item can have
 * 
 * @author sebschlicht
 * 
 */
public enum StreamItemType {

	/**
	 * match phase ended
	 */
	PHASE_END("phase-end"),

	/**
	 * pure text message
	 */
	TEXT("text"),

	/**
	 * a player scored
	 */
	SCORE("score"),

	/**
	 * a player fouled
	 */
	FOUL("foul");

	/**
	 * stream item type String value
	 */
	private final String value;

	/**
	 * add a new stream item type
	 * 
	 * @param value
	 *            stream item type String value
	 */
	private StreamItemType(final String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}

	/**
	 * parse String value to stream item type
	 * 
	 * @param value
	 *            String value of a stream item type
	 * @return stream item type having the String value passed<br>
	 *         <b>null</b> if there is no stream item type with such String
	 *         value
	 */
	public static StreamItemType parseString(final String value) {
		if (PHASE_END.toString().equals(value)) {
			return PHASE_END;
		} else if (TEXT.toString().equals(value)) {
			return TEXT;
		} else if (SCORE.toString().equals(value)) {
			return SCORE;
		} else if (FOUL.toString().equals(value)) {
			return FOUL;
		}

		// there is no stream item type with such String value
		return null;
	}

}