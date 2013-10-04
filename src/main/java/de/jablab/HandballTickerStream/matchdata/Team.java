package de.jablab.HandballTickerStream.matchdata;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONObject;

import de.jablab.HandballTickerStream.HandballTickerStream;
import de.jablab.HandballTickerStream.Streamable;
import de.jablab.HandballTickerStream.exceptions.TeamFormatException;

/**
 * team playing in the match
 * 
 * @author sebschlicht
 * 
 */
public class Team implements Streamable {

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
	 * load team from JSON object
	 * 
	 * @param team
	 *            JSON team object
	 * @throws TeamFormatException
	 *             if the JSON object is not a valid team object
	 */
	public Team(final JSONObject team) throws TeamFormatException {
		final String sId = (String) team
				.get(HandballTickerStream.Team.KEY_IDENTIFIER);
		if (sId != null) {
			try {
				this.id = Integer.valueOf(sId);
			} catch (final NumberFormatException e) {
				throw new TeamFormatException("field \""
						+ HandballTickerStream.Team.KEY_IDENTIFIER
						+ "\" is malformed: \"" + sId + "\" is not a number");
			}

			this.name = (String) team.get(HandballTickerStream.Team.KEY_NAME);

			final String sLogoUrl = (String) team
					.get(HandballTickerStream.Team.KEY_LOGO_URL);
			if (sLogoUrl != null) {
				try {
					this.logoUrl = new URL(sLogoUrl);
				} catch (final MalformedURLException e) {
					throw new TeamFormatException("field \""
							+ HandballTickerStream.Team.KEY_LOGO_URL
							+ "\" is malformed: \"" + sLogoUrl
							+ "\" is not an URL");
				}
			}
		} else {
			throw new TeamFormatException("field \""
					+ HandballTickerStream.Team.KEY_IDENTIFIER
					+ "\" is missing");
		}
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

}