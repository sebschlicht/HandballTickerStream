package de.jablab.HandballTickerStream;

import org.json.simple.JSONObject;

import de.jablab.HandballTickerStream.exceptions.TeamFormatException;

/**
 * team involved in the match being streamed
 * 
 * @author sebschlicht
 * 
 */
public class Team {

	/**
	 * unique identifier
	 */
	private final String identifier;

	public Team(final JSONObject team) throws TeamFormatException {
		this.identifier = (String) team.get(Stream.Keys.Team.IDENTIFIER);
		if (this.identifier == null) {
			throw new TeamFormatException("\"" + Stream.Keys.Team.IDENTIFIER
					+ "\" is missing");
		}
	}

}