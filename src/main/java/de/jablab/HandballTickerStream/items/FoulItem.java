package de.jablab.HandballTickerStream.items;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import de.jablab.HandballTickerStream.HandballTickerStream;
import de.jablab.HandballTickerStream.MatchTime;
import de.jablab.HandballTickerStream.Player;
import de.jablab.HandballTickerStream.StreamItem;
import de.jablab.HandballTickerStream.StreamItemType;
import de.jablab.HandballTickerStream.exceptions.PlayerFormatException;
import de.jablab.HandballTickerStream.exceptions.StreamItemFormatException;
import de.jablab.HandballTickerStream.exceptions.items.FoulItemFormatException;
import de.jablab.HandballTickerStream.exceptions.items.ScoreItemFormatException;

/**
 * player fouled stream item
 * 
 * @author sebschlicht
 * 
 */
public class FoulItem extends StreamItem {

	/**
	 * player who fouled
	 */
	private Player player;

	/**
	 * list of disciplines for the player who fouled
	 */
	private List<Discipline> disciplines;

	/**
	 * player who has been fouled
	 */
	private Player victim;

	/**
	 * create a new player fouled stream item
	 * 
	 * @param published
	 *            exact time when published
	 * @param time
	 *            match time when published<br>
	 *            considered to be the time of the happening
	 * @param message
	 *            message displayed instead of a generated value
	 * @param player
	 *            player who fouled
	 * @param disciplines
	 *            list of disciplines for the player who fouled
	 * @param victim
	 *            player who has been fouled
	 */
	public FoulItem(final Date published, final MatchTime time,
			final String message, final Player player,
			final List<Discipline> disciplines, final Player victim) {
		super(published, time, StreamItemType.FOUL, message);
		this.player = player;
		this.disciplines = disciplines;
		this.victim = victim;
	}

	/**
	 * @return player who fouled
	 */
	public Player getPlayer() {
		return this.player;
	}

	public void setPlayer(final Player player) {
		this.player = player;
	}

	/**
	 * @return list of disciplines for the player who fouled
	 */
	public List<Discipline> getDisciplines() {
		return this.disciplines;
	}

	public void setDisciplines(final List<Discipline> disciplines) {
		this.disciplines = disciplines;
	}

	/**
	 * @return player who has been fouled
	 */
	public Player getVictim() {
		return this.victim;
	}

	public void setVictim(final Player victim) {
		this.victim = victim;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected JSONObject getObjectJSON() {
		final JSONObject object = new JSONObject();
		object.put(HandballTickerStream.StreamItem.FoulItem.KEY_PLAYER,
				this.player.toJSON());
		if ((this.disciplines != null) && !this.disciplines.isEmpty()) {
			final JSONArray disciplines = new JSONArray();
			for (Discipline discipline : this.disciplines) {
				disciplines.add(discipline.toString());
			}
			object.put(
					HandballTickerStream.StreamItem.FoulItem.KEY_DISCIPLINES,
					disciplines);
		}
		if (this.victim != null) {
			object.put(HandballTickerStream.StreamItem.FoulItem.KEY_VICTIM,
					this.victim.toJSON());
		}
		return object;
	}

	/**
	 * load a foul stream item from JSON object
	 * 
	 * @param jsonString
	 *            foul stream item JSON
	 * @throws FoulItemFormatException
	 *             if the JSON is not a valid foul stream item object
	 * @throws StreamItemFormatException
	 *             if the JSON is not a valid stream item object
	 */
	public static FoulItem parseJSON(final String jsonString)
			throws StreamItemFormatException {
		JSONObject foulStreamItem;
		try {
			foulStreamItem = (JSONObject) JSON_PARSER.parse(jsonString);
		} catch (org.json.simple.parser.ParseException e) {
			throw new StreamItemFormatException("\"" + jsonString
					+ "\" is not a JSON String");
		}

		final StreamItemInformation streamItemInformation = StreamItem
				.parseStreamItemJSON(foulStreamItem);
		final JSONObject object = streamItemInformation.getObject();

		if (streamItemInformation.getType() == StreamItemType.FOUL) {
			final Player player = parsePlayer(object);
			final List<Discipline> disciplines = parseDisciplines(object);
			final Player victim = parseVictim(object);

			return new FoulItem(streamItemInformation.getPublished(),
					streamItemInformation.getTime(),
					streamItemInformation.getMessage(), player, disciplines,
					victim);
		} else {
			throw new ScoreItemFormatException("field \""
					+ HandballTickerStream.StreamItem.KEY_TYPE
					+ "\" is invalid: must be \"" + StreamItemType.FOUL + "\"");
		}
	}

	/**
	 * parse the player field
	 * 
	 * @param object
	 *            foul stream item object JSON object
	 * @return player who fouled
	 * @throws FoulItemFormatException
	 *             if field missing, malformed or not a player object
	 */
	private static Player parsePlayer(final JSONObject object)
			throws FoulItemFormatException {
		final Object player = object
				.get(HandballTickerStream.StreamItem.FoulItem.KEY_PLAYER);
		if (player != null) {
			if (player instanceof JSONObject) {
				try {
					return Player.parseJSON((JSONObject) player);
				} catch (final PlayerFormatException e) {
					throw new FoulItemFormatException(
							"field \""
									+ HandballTickerStream.StreamItem.FoulItem.KEY_PLAYER
									+ "\" is not a player object", e);
				}
			} else {
				throw new FoulItemFormatException("field \""
						+ HandballTickerStream.StreamItem.FoulItem.KEY_PLAYER
						+ "\" is malformed: \"" + player
						+ "\" is not a JSON object");
			}
		} else {
			throw new FoulItemFormatException("field \""
					+ HandballTickerStream.StreamItem.FoulItem.KEY_PLAYER
					+ "\" is missing");
		}
	}

	/**
	 * parse the disciplines field
	 * 
	 * @param object
	 *            foul stream item object JSON object
	 * @return list of disciplines for the player who fouled<br>
	 *         <b>null</b> if field missing or array empty
	 * @throws FoulItemFormatException
	 *             if field malformed or not a JSON array
	 */
	private static List<Discipline> parseDisciplines(final JSONObject object)
			throws FoulItemFormatException {
		final Object oDisciplines = object
				.get(HandballTickerStream.StreamItem.FoulItem.KEY_DISCIPLINES);
		if (oDisciplines != null) {
			if (oDisciplines instanceof JSONArray) {
				final JSONArray aDisciplines = (JSONArray) oDisciplines;

				if (!aDisciplines.isEmpty()) {
					final List<Discipline> disciplines = new LinkedList<Discipline>();
					Discipline discipline;

					String sDiscipline;
					for (Object oDiscipline : aDisciplines) {
						sDiscipline = (String) oDiscipline;
						discipline = Discipline.parseString(sDiscipline);
						if (discipline != null) {
							disciplines.add(discipline);
						} else {
							throw new FoulItemFormatException(
									"field \""
											+ HandballTickerStream.StreamItem.FoulItem.KEY_DISCIPLINES
											+ "\" is malformed: \""
											+ sDiscipline
											+ "\" is not a discipline");
						}
					}

					return disciplines;
				}
			} else {
				throw new FoulItemFormatException(
						"field \""
								+ HandballTickerStream.StreamItem.FoulItem.KEY_DISCIPLINES
								+ "\" is malformed: \"" + oDisciplines
								+ "\" is not a JSON array");
			}
		}

		return null;
	}

	/**
	 * parse the victim field
	 * 
	 * @param object
	 *            foul stream item object JSON object
	 * @return player who has been fouled<br>
	 *         <b>null</b> if field missing
	 * @throws FoulItemFormatException
	 *             if field malformed or not a player object
	 */
	private static Player parseVictim(final JSONObject object)
			throws FoulItemFormatException {
		final Object victim = object
				.get(HandballTickerStream.StreamItem.FoulItem.KEY_VICTIM);
		if (victim != null) {
			if (victim instanceof JSONObject) {
				try {
					return Player.parseJSON((JSONObject) victim);
				} catch (final PlayerFormatException e) {
					throw new FoulItemFormatException(
							"field \""
									+ HandballTickerStream.StreamItem.FoulItem.KEY_VICTIM
									+ "\" is not a player object", e);
				}
			} else {
				throw new FoulItemFormatException("field \""
						+ HandballTickerStream.StreamItem.FoulItem.KEY_VICTIM
						+ "\" is malformed: \"" + victim
						+ "\" is not a JSON object");
			}
		}

		return null;
	}

}