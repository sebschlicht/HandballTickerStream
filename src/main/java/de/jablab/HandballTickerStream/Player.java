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
	public boolean equals(final Object o) {
		if (o == null) {
			return false;
		}
		if (o == this) {
			return true;
		}
		if (!o.getClass().equals(this.getClass())) {
			return false;
		}

		final Player player = (Player) o;
		if (player.getId() == null) {
			if (this.id != null) {
				return false;
			}
		}
		if (player.getName() == null) {
			if (this.name != null) {
				return false;
			}
		}

		return (((player.getId() == null) || player.getId().equals(this.id))
				&& (player.getNumber() == this.number)
				&& ((player.getName() == null) || player.getName().equals(
						this.name)) && (player.getTeam() == this.team));
	}

	public static Player parseJSON(final String jsonString)
			throws PlayerFormatException {
		final JSONObject playerObject = parseJSONObject(jsonString);
		if (playerObject != null) {
			return loadFromJSON(playerObject);
		} else {
			throw new PlayerFormatException("\"" + jsonString
					+ "\" is not a JSON String");
		}
	}

	public static Player loadFromJSON(final JSONObject playerObject)
			throws PlayerFormatException {
		final Integer identifier = parseIdentifier(playerObject);
		final int number = parseNumber(playerObject);
		final String name = parseName(playerObject);
		final TeamRole team = parseTeamRole(playerObject);
		return new Player(identifier, number, name, team);
	}

	private static Integer parseIdentifier(final JSONObject playerObject)
			throws PlayerFormatException {
		final String sId = (String) playerObject
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

	private static int parseNumber(final JSONObject playerObject)
			throws PlayerFormatException {
		final String sNumber = (String) playerObject
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

	private static String parseName(final JSONObject playerObject) {
		return (String) playerObject.get(HandballTickerStream.Player.KEY_NAME);
	}

	private static TeamRole parseTeamRole(final JSONObject playerObject)
			throws PlayerFormatException {
		final String sTeamRole = (String) playerObject
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