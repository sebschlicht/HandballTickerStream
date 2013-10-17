package de.jablab.HandballTickerStream.items;


/**
 * disciplines a player can get for a foul
 * 
 * @author sebschlicht
 * 
 */
public enum Discipline {

	/**
	 * 2 minutes
	 */
	TIME("time"),

	/**
	 * penalty throw for the victim team
	 */
	PENALTY("penalty"),

	/**
	 * yellow card shown
	 */
	YELLOW("yellow"),

	/**
	 * red card shown
	 */
	RED("red");

	/**
	 * discipline String value
	 */
	private final String value;

	/**
	 * add a new discipline
	 * 
	 * @param value
	 *            discipline String value
	 */
	private Discipline(final String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}

	/**
	 * parse String value to discipline
	 * 
	 * @param value
	 *            String value of a discipline
	 * @return discipline having the String value passed<br>
	 *         <b>null</b> if there is no discipline with such String value
	 */
	public static Discipline parseString(final String value) {
		if (TIME.toString().equals(value)) {
			return TIME;
		} else if (PENALTY.toString().equals(value)) {
			return PENALTY;
		} else if (YELLOW.toString().equals(value)) {
			return YELLOW;
		} else if (RED.toString().equals(value)) {
			return RED;
		}

		// there is no discipline with such String value
		return null;
	}

}