package de.jablab.HandballTickerStream.items;

import java.util.Date;

import org.json.simple.JSONObject;

import de.jablab.HandballTickerStream.HandballTickerStream;
import de.jablab.HandballTickerStream.MatchTime;
import de.jablab.HandballTickerStream.Player;
import de.jablab.HandballTickerStream.Score;
import de.jablab.HandballTickerStream.StreamItem;
import de.jablab.HandballTickerStream.StreamItemType;
import de.jablab.HandballTickerStream.TeamRole;
import de.jablab.HandballTickerStream.exceptions.PlayerFormatException;
import de.jablab.HandballTickerStream.exceptions.ScoreFormatException;
import de.jablab.HandballTickerStream.exceptions.StreamItemFormatException;
import de.jablab.HandballTickerStream.exceptions.items.ScoreItemFormatException;

/**
 * player scored stream item
 * 
 * @author sebschlicht
 * 
 */
public class ScoreItem extends StreamItem {

	/**
	 * new score
	 */
	private Score score;

	/**
	 * role of the team scored
	 */
	private TeamRole team;

	/**
	 * type of the scoring
	 */
	private ScoringType type;

	/**
	 * player who scored
	 */
	private Player player;

	/**
	 * create a new player scored stream item
	 * 
	 * @param published
	 *            exact time when published
	 * @param time
	 *            match time when published<br>
	 *            considered to be the time of the happening
	 * @param message
	 *            message displayed instead of a generated value
	 * @param score
	 *            new score
	 * @param team
	 *            role of the team scored
	 * @param type
	 *            type of the scoring
	 * @param player
	 *            player who scored
	 */
	public ScoreItem(final Date published, final MatchTime time,
			final String message, final Score score, final TeamRole team,
			final ScoringType type, final Player player) {
		super(published, time, StreamItemType.SCORE, message);
		this.score = score;
		this.team = team;
		this.type = type;
		this.player = player;
	}

	/**
	 * @return new score
	 */
	public Score getScore() {
		return this.score;
	}

	public void setScore(final Score score) {
		this.score = score;
	}

	/**
	 * @return role of the team scored
	 */
	public TeamRole getTeam() {
		return this.team;
	}

	public void setTeam(final TeamRole team) {
		this.team = team;
	}

	/**
	 * @return type of the scoring
	 */
	public ScoringType getScoringType() {
		return this.type;
	}

	public void setScoringType(final ScoringType type) {
		this.type = type;
	}

	/**
	 * 
	 * @return player who scored
	 */
	public Player getPlayer() {
		return this.player;
	}

	public void setPlayer(final Player player) {
		this.player = player;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected JSONObject getObjectJSON() {
		final JSONObject object = new JSONObject();
		object.put(HandballTickerStream.StreamItem.ScoreItem.KEY_SCORE,
				this.score.toJSON());
		object.put(HandballTickerStream.StreamItem.ScoreItem.KEY_TEAM_ROLE,
				this.team.toString());
		if (this.type != null) {
			object.put(HandballTickerStream.StreamItem.ScoreItem.KEY_TYPE,
					this.type.toString());
		}
		if (this.player != null) {
			object.put(HandballTickerStream.StreamItem.ScoreItem.KEY_PLAYER,
					this.player.toJSON());
		}
		return object;
	}

	/**
	 * load a score stream item from JSON object
	 * 
	 * @param jsonString
	 *            score stream item JSON
	 * @throws ScoreItemFormatException
	 *             if the JSON is not a valid score stream item object
	 * @throws StreamItemFormatException
	 *             if the JSON is not a valid stream item object
	 */
	public static ScoreItem parseJSON(final String jsonString)
			throws StreamItemFormatException {
		JSONObject scoreStreamItem;
		try {
			scoreStreamItem = (JSONObject) JSON_PARSER.parse(jsonString);
		} catch (org.json.simple.parser.ParseException e) {
			throw new StreamItemFormatException("\"" + jsonString
					+ "\" is not a JSON String");
		}

		final StreamItemInformation streamItemInformation = StreamItem
				.parseStreamItemJSON(scoreStreamItem);
		final JSONObject object = streamItemInformation.getObject();

		if (streamItemInformation.getType() == StreamItemType.SCORE) {
			final Score score = parseScore(object);
			final TeamRole team = parseTeamRole(object);
			final ScoringType type = parseScoringType(object);
			final Player player = parsePlayer(object);

			return new ScoreItem(streamItemInformation.getPublished(),
					streamItemInformation.getTime(),
					streamItemInformation.getMessage(), score, team, type,
					player);
		} else {
			throw new ScoreItemFormatException("field \""
					+ HandballTickerStream.StreamItem.KEY_TYPE
					+ "\" is invalid: must be \"" + StreamItemType.SCORE + "\"");
		}
	}

	/**
	 * parse the score field
	 * 
	 * @param object
	 *            score stream item object JSON object
	 * @return new score
	 * @throws ScoreItemFormatException
	 *             if field missing, malformed or not a score object
	 */
	private static Score parseScore(final JSONObject object)
			throws ScoreItemFormatException {
		final Object score = object
				.get(HandballTickerStream.StreamItem.ScoreItem.KEY_SCORE);
		if (score != null) {
			if (score instanceof JSONObject) {
				try {
					return Score.parseJSON((JSONObject) score);
				} catch (final ScoreFormatException e) {
					throw new ScoreItemFormatException(
							"field \""
									+ HandballTickerStream.StreamItem.ScoreItem.KEY_SCORE
									+ "\" is not a score object", e);
				}
			} else {
				throw new ScoreItemFormatException("field \""
						+ HandballTickerStream.StreamItem.ScoreItem.KEY_SCORE
						+ "\" is malformed: \"" + score
						+ "\" is not a JSON object");
			}
		} else {
			throw new ScoreItemFormatException("field \""
					+ HandballTickerStream.StreamItem.ScoreItem.KEY_SCORE
					+ "\" is missing");
		}
	}

	/**
	 * parse the team field
	 * 
	 * @param object
	 *            score stream item object JSON object
	 * @return role of the team scored
	 * @throws ScoreItemFormatException
	 *             if field missing or malformed
	 */
	private static TeamRole parseTeamRole(final JSONObject object)
			throws ScoreItemFormatException {
		final String sTeamRole = (String) object
				.get(HandballTickerStream.StreamItem.ScoreItem.KEY_TEAM_ROLE);
		if (sTeamRole != null) {
			final TeamRole team = TeamRole.parseString(sTeamRole);
			if (team != null) {
				return team;
			} else {
				throw new ScoreItemFormatException(
						"field \""
								+ HandballTickerStream.StreamItem.ScoreItem.KEY_TEAM_ROLE
								+ "\" is malformed: \"" + sTeamRole
								+ "\" is not a team role");
			}
		} else {
			throw new ScoreItemFormatException("field \""
					+ HandballTickerStream.StreamItem.ScoreItem.KEY_TEAM_ROLE
					+ "\" is missing");
		}
	}

	/**
	 * parse the type field
	 * 
	 * @param object
	 *            score stream item object JSON object
	 * @return type of the scoring<br>
	 *         <b>null</b> if field missing
	 * @throws ScoreItemFormatException
	 *             if field malformed
	 */
	private static ScoringType parseScoringType(final JSONObject object)
			throws ScoreItemFormatException {
		final String sScoringType = (String) object
				.get(HandballTickerStream.StreamItem.ScoreItem.KEY_TYPE);
		if (sScoringType != null) {
			final ScoringType scoringType = ScoringType
					.parseString(sScoringType);
			if (scoringType != null) {
				return scoringType;
			} else {
				throw new ScoreItemFormatException("field \""
						+ HandballTickerStream.StreamItem.ScoreItem.KEY_TYPE
						+ "\" is malformed: \"" + sScoringType
						+ "\" is not a scoring type");
			}
		}

		return null;
	}

	/**
	 * parse the player field
	 * 
	 * @param object
	 *            score stream item object JSON object
	 * @return player who scored<br>
	 *         <b>null</b> if field missing
	 * @throws ScoreItemFormatException
	 *             if field malformed or not a player object
	 */
	private static Player parsePlayer(final JSONObject object)
			throws ScoreItemFormatException {
		final Object player = object
				.get(HandballTickerStream.StreamItem.ScoreItem.KEY_PLAYER);
		if (player != null) {
			if (player instanceof JSONObject) {
				try {
					return Player.parseJSON((JSONObject) player);
				} catch (final PlayerFormatException e) {
					throw new ScoreItemFormatException(
							"field \""
									+ HandballTickerStream.StreamItem.ScoreItem.KEY_PLAYER
									+ "\" is not a player object", e);
				}
			} else {
				throw new ScoreItemFormatException("field \""
						+ HandballTickerStream.StreamItem.ScoreItem.KEY_PLAYER
						+ "\" is malformed: \"" + player
						+ "\" is not a JSON object");
			}
		}

		return null;
	}

}