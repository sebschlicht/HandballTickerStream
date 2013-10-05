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

}