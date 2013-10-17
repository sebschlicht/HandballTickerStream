package de.jablab.HandballTickerStream;

import org.json.simple.JSONObject;

import de.jablab.HandballTickerStream.exceptions.PlayerFormatException;

/**
 * player playing for a team
 * 
 * @author sebschlicht
 * 
 */
public class Player extends Streamable {

	/**
	 * unique player identifier within the internal system
	 */
	private Integer id;

	/**
	 * player number used in the match
	 */
	private int number;

	/**
	 * real name
	 */
	private String name;

	/**
	 * role of the team the player is playing for
	 */
	private TeamRole team;

	/**
	 * create a new player playing for a team
	 * 
	 * @param id
	 *            unique player identifier within the internal system
	 * @param number
	 *            player number used in the match
	 * @param name
	 *            real name
	 * @param team
	 *            role of the team the player is playing for
	 */
	public Player(final Integer id, final int number, final String name,
			final TeamRole team) {
		this.id = id;
		this.number = number;
		this.name = name;
		this.team = team;
	}

	/**
	 * @return unique player identifier within the internal system
	 */
	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	/**
	 * @return player number used in the match
	 */
	public int getNumber() {
		return this.number;
	}

	public void setNumber(final int number) {
		this.number = number;
	}

	/**
	 * @return real name
	 */
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return role of the team the player is playing for
	 */
	public TeamRole getTeam() {
		return this.team;
	}

	public void setTeam(final TeamRole team) {
		this.team = team;
	}

	@Override
	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		final JSONObject object = new JSONObject();
		if (this.id != null) {
			object.put(HandballTickerStream.Player.KEY_IDENTIFIER,
					String.valueOf(this.id));
		}
		object.put(HandballTickerStream.Player.KEY_NUMBER,
				String.valueOf(this.number));
		if (this.name != null) {
			object.put(HandballTickerStream.Player.KEY_NAME, this.name);
		}
		object.put(HandballTickerStream.Player.KEY_TEAM_ROLE,
				this.team.toString());
		return object;
	}

	@Override
	public String toJSONString() {
		return this.toJSON().toJSONString();
	}

	/**
	 * load player from JSON
	 * 
	 * @param player
	 *            player JSON
	 * @throws PlayerFormatException
	 *             if the JSON is not a valid player object
	 */
	public static Player parseJSON(final String jsonString)
			throws PlayerFormatException {
		JSONObject player;
		try {
			player = (JSONObject) JSON_PARSER.parse(jsonString);
		} catch (org.json.simple.parser.ParseException e) {
			throw new PlayerFormatException("\"" + jsonString
					+ "\" is not a JSON String");
		}

		return parseJSON(player);
	}

	/**
	 * load player from JSON object
	 * 
	 * @param player
	 *            player JSON object
	 * @throws PlayerFormatException
	 *             if the JSON object is not a valid player object
	 */
	public static Player parseJSON(final JSONObject player)
			throws PlayerFormatException {
		final Integer identifier = parseIdentifier(player);
		final int number = parseNumber(player);
		final String name = parseName(player);
		final TeamRole team = parseTeamRole(player);
		return new Player(identifier, number, name, team);
	}

	/**
	 * parse the id field
	 * 
	 * @param player
	 *            player JSON object
	 * @return unique player identifier within the internal system<br>
	 *         <b>null</b> if field missing
	 * @throws PlayerFormatException
	 *             if field malformed
	 */
	private static Integer parseIdentifier(final JSONObject player)
			throws PlayerFormatException {
		final String sId = (String) player
				.get(HandballTickerStream.Player.KEY_IDENTIFIER);
		if (sId != null) {
			try {
				return Integer.valueOf(sId);
			} catch (final NumberFormatException e) {
				throw new PlayerFormatException("field \""
						+ HandballTickerStream.Player.KEY_IDENTIFIER
						+ "\" is malformed: \"" + sId + "\" is not a number");
			}
		}

		return null;
	}

	/**
	 * parse the number field
	 * 
	 * @param player
	 *            player JSON object
	 * @return player number used in the match
	 * @throws PlayerFormatException
	 *             if field missing or malformed
	 */
	private static int parseNumber(final JSONObject player)
			throws PlayerFormatException {
		final String sNumber = (String) player
				.get(HandballTickerStream.Player.KEY_NUMBER);
		if (sNumber != null) {
			try {
				return Integer.valueOf(sNumber);
			} catch (final NumberFormatException e) {
				throw new PlayerFormatException("field \""
						+ HandballTickerStream.Player.KEY_NUMBER
						+ "\" is malformed: \"" + sNumber
						+ "\" is not a number");
			}
		} else {
			throw new PlayerFormatException("field \""
					+ HandballTickerStream.Player.KEY_NUMBER + "\" is missing");
		}
	}

	/**
	 * parse the name field
	 * 
	 * @param player
	 *            player JSON object
	 * @return real name<br>
	 *         <b>null</b> if field missing
	 */
	private static String parseName(final JSONObject player) {
		return (String) player.get(HandballTickerStream.Player.KEY_NAME);
	}

	/**
	 * parse the team field
	 * 
	 * @param player
	 *            player JSON object
	 * @return role of the team the player is playing for
	 * @throws PlayerFormatException
	 *             if field missing or malformed
	 */
	private static TeamRole parseTeamRole(final JSONObject player)
			throws PlayerFormatException {
		final String sTeamRole = (String) player
				.get(HandballTickerStream.Player.KEY_TEAM_ROLE);
		if (sTeamRole != null) {
			final TeamRole team = TeamRole.parseString(sTeamRole);
			if (team != null) {
				return team;
			} else {
				throw new PlayerFormatException("field \""
						+ HandballTickerStream.Player.KEY_TEAM_ROLE
						+ "\" is malformed: \"" + sTeamRole
						+ "\" is not a team role");
			}
		} else {
			throw new PlayerFormatException("field \""
					+ HandballTickerStream.Player.KEY_TEAM_ROLE
					+ "\" is missing");
		}
	}

}