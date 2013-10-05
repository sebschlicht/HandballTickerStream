package de.jablab.HandballTickerStream.items;

/**
 * sub types a phase end stream item can have
 * 
 * @author sebschlicht
 * 
 */
public enum PhaseEndSubType {

	/**
	 * timeout by a team
	 */
	TIMEOUT("timeout"),

	/**
	 * injury of a player
	 */
	INJURY("injury");

	/**
	 * phase end sub type String value
	 */
	private final String value;

	/**
	 * add a new phase end sub type
	 * 
	 * @param value
	 *            phase end sub type String value
	 */
	private PhaseEndSubType(final String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}

	/**
	 * parse String value to phase end sub type
	 * 
	 * @param value
	 *            String value of a phase end sub type
	 * @return phase end sub type having the String value passed<br>
	 *         <b>null</b> if there is no phase end sub type with such String
	 *         value
	 */
	public static PhaseEndSubType parseString(final String value) {
		if (TIMEOUT.toString().equals(value)) {
			return TIMEOUT;
		} else if (INJURY.toString().equals(value)) {
			return INJURY;
		}

		// there is no phase end sub type with such String value
		return null;
	}

}