package de.jablab.HandballTickerStream;

/**
 * roles a team can have
 * 
 * @author sebschlicht
 * 
 */
public enum TeamRole {

	/**
	 * home team
	 */
	HOME("home"),

	/**
	 * guest team
	 */
	GUEST("guest");

	/**
	 * team role String value
	 */
	private final String value;

	/**
	 * add a new team role
	 * 
	 * @param value
	 *            team role String value
	 */
	private TeamRole(final String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}

	/**
	 * parse String value to team role
	 * 
	 * @param value
	 *            String value of a team role
	 * @return team role having the String value passed<br>
	 *         <b>null</b> if there is no team role with such String value
	 */
	public static TeamRole parseString(final String value) {
		if (HOME.toString().equals(value)) {
			return HOME;
		} else if (GUEST.toString().equals(value)) {
			return GUEST;
		}

		// there is no team role with such String value
		return null;
	}

}