package de.jablab.HandballTickerStream.items;


/**
 * types a scoring can be of
 * 
 * @author sebschlicht
 * 
 */
public enum ScoringType {

	/**
	 * no special kind of scoring
	 */
	NORMAL("normal"),

	/**
	 * scoring due to a rush
	 */
	RUSH("rush"),

	/**
	 * scoring due to a penalty throw
	 */
	PENALTY("penalty");

	/**
	 * scoring type String value
	 */
	private final String value;

	/**
	 * add a new scoring type
	 * 
	 * @param value
	 *            scoring type String value
	 */
	private ScoringType(final String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}

	/**
	 * parse String value to scoring type
	 * 
	 * @param value
	 *            String value of a scoring type
	 * @return scoring type having the String value passed<br>
	 *         <b>null</b> if there is no scoring type with such String value
	 */
	public static ScoringType parseString(final String value) {
		if (NORMAL.toString().equals(value)) {
			return NORMAL;
		} else if (RUSH.toString().equals(value)) {
			return RUSH;
		} else if (PENALTY.toString().equals(value)) {
			return PENALTY;
		}

		// there is no scoring type with such String value
		return null;
	}

}