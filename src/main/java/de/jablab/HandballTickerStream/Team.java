package de.jablab.HandballTickerStream;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONObject;

import de.jablab.HandballTickerStream.exceptions.TeamFormatException;

/**
 * team playing in the match
 * 
 * @author sebschlicht
 * 
 */
public class Team extends Streamable {

	/**
	 * unique team identifier
	 */
	private int id;

	/**
	 * team name displayed
	 */
	private String name;

	/**
	 * URL to the team logo
	 */
	private URL logoUrl;

	/**
	 * create a new team playing in the match
	 * 
	 * @param id
	 *            unique team identifier
	 * @param name
	 *            team name displayed
	 * @param logoUrl
	 *            URL to the team logo
	 */
	public Team(final int id, final String name, final URL logoUrl) {
		this.id = id;
		this.name = name;
		this.logoUrl = logoUrl;
	}

	/**
	 * @return unique team identifier
	 */
	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	/**
	 * @return team name displayed
	 */
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return URL to the team logo
	 */
	public URL getLogoUrl() {
		return this.logoUrl;
	}

	public void setLogoUrl(final URL logoUrl) {
		this.logoUrl = logoUrl;
	}

	@Override
	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		final JSONObject object = new JSONObject();
		object.put(HandballTickerStream.Team.KEY_IDENTIFIER,
				String.valueOf(this.id));
		if (this.name != null) {
			object.put(HandballTickerStream.Team.KEY_NAME, this.name);
		}
		if (this.logoUrl != null) {
			object.put(HandballTickerStream.Team.KEY_LOGO_URL,
					this.logoUrl.toString());
		}
		return object;
	}

	@Override
	public String toJSONString() {
		return this.toJSON().toJSONString();
	}

	/**
	 * load team from JSON
	 * 
	 * @param jsonString
	 *            team JSON
	 * @throws TeamFormatException
	 *             if the JSON is not a valid team object
	 */
	public static Team parseJSON(final String jsonString)
			throws TeamFormatException {
		JSONObject team;
		try {
			team = (JSONObject) JSON_PARSER.parse(jsonString);
		} catch (org.json.simple.parser.ParseException e) {
			throw new TeamFormatException("\"" + jsonString
					+ "\" is not a JSON String");
		}

		return parseJSON(team);
	}

	/**
	 * load team from JSON object
	 * 
	 * @param team
	 *            team JSON object
	 * @throws TeamFormatException
	 *             if the JSON object is not a valid team object
	 */
	static Team parseJSON(final JSONObject team) throws TeamFormatException {
		final int identifier = parseIdentifier(team);
		final String name = parseName(team);
		final URL logoUrl = parseLogoUrl(team);
		return new Team(identifier, name, logoUrl);
	}

	/**
	 * parse the id field
	 * 
	 * @param team
	 *            team JSON object
	 * @return unique team identifier
	 * @throws TeamFormatException
	 *             if field missing or malformed
	 */
	private static int parseIdentifier(final JSONObject team)
			throws TeamFormatException {
		final String sId = (String) team
				.get(HandballTickerStream.Team.KEY_IDENTIFIER);
		if (sId != null) {
			try {
				return Integer.valueOf(sId);
			} catch (final NumberFormatException e) {
				throw new TeamFormatException("field \""
						+ HandballTickerStream.Team.KEY_IDENTIFIER
						+ "\" is malformed: \"" + sId + "\" is not a number");
			}
		} else {
			throw new TeamFormatException("field \""
					+ HandballTickerStream.Team.KEY_IDENTIFIER
					+ "\" is missing");
		}
	}

	/**
	 * parse the name field
	 * 
	 * @param team
	 *            team JSON object
	 * @return team name displayed
	 * @throws TeamFormatException
	 *             if field missing
	 */
	private static String parseName(final JSONObject team)
			throws TeamFormatException {
		final String name = (String) team
				.get(HandballTickerStream.Team.KEY_NAME);
		if (name != null) {
			return name;
		} else {
			throw new TeamFormatException("field \""
					+ HandballTickerStream.Team.KEY_NAME + "\" is missing");
		}
	}

	/**
	 * parse the logo URL field
	 * 
	 * @param team
	 *            team JSON object
	 * @return URL to the team logo<br>
	 *         <b>null</b> if field missing
	 * @throws TeamFormatException
	 *             if field malformed
	 */
	private static URL parseLogoUrl(final JSONObject team)
			throws TeamFormatException {
		final String sLogoUrl = (String) team
				.get(HandballTickerStream.Team.KEY_LOGO_URL);
		if (sLogoUrl != null) {
			try {
				return new URL(sLogoUrl);
			} catch (final MalformedURLException e) {
				throw new TeamFormatException("field \""
						+ HandballTickerStream.Team.KEY_LOGO_URL
						+ "\" is malformed: \"" + sLogoUrl + "\" is not an URL");
			}
		}

		return null;
	}

}