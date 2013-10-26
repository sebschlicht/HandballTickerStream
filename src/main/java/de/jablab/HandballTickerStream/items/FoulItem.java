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
import de.jablab.HandballTickerStream.StreamItemInformation;
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
		object.put(HandballTickerStream.StreamItem.Foul.KEY_PLAYER,
				this.player.toJSON());
		if ((this.disciplines != null) && !this.disciplines.isEmpty()) {
			final JSONArray disciplines = new JSONArray();
			for (Discipline discipline : this.disciplines) {
				disciplines.add(discipline.toString());
			}
			object.put(HandballTickerStream.StreamItem.Foul.KEY_DISCIPLINES,
					disciplines);
		}
		if (this.victim != null) {
			object.put(HandballTickerStream.StreamItem.Foul.KEY_VICTIM,
					this.victim.toJSON());
		}
		return object;
	}

	public static FoulItem parseJSON(final String jsonString)
			throws StreamItemFormatException {
		final JSONObject foulItem = parseJSONObject(jsonString);
		if (foulItem != null) {
			return loadFromJSON(foulItem);
		} else {
			throw new StreamItemFormatException("\"" + jsonString
					+ "\" is not a JSON String");
		}
	}

	public static FoulItem loadFromJSON(final JSONObject streamItem)
			throws StreamItemFormatException {
		final StreamItemInformation streamItemInfo = loadStreamItemInformation(streamItem);
		final JSONObject foulObject = streamItemInfo.getObject();

		if (streamItemInfo.getType() == StreamItemType.FOUL) {
			final Player player = parsePlayer(foulObject);
			final List<Discipline> disciplines = parseDisciplines(foulObject);
			final Player victim = parseVictim(foulObject);

			return new FoulItem(streamItemInfo.getPublished(),
					streamItemInfo.getTime(), streamItemInfo.getMessage(),
					player, disciplines, victim);
		} else {
			throw new ScoreItemFormatException("field \""
					+ HandballTickerStream.StreamItem.KEY_TYPE
					+ "\" is invalid: must be \"" + StreamItemType.FOUL + "\"");
		}
	}

	private static Player parsePlayer(final JSONObject foulObject)
			throws FoulItemFormatException {
		final Object player = foulObject
				.get(HandballTickerStream.StreamItem.Foul.KEY_PLAYER);
		if (player != null) {
			if (player instanceof JSONObject) {
				try {
					return Player.loadFromJSON((JSONObject) player);
				} catch (final PlayerFormatException e) {
					throw new FoulItemFormatException("field \""
							+ HandballTickerStream.StreamItem.Foul.KEY_PLAYER
							+ "\" is not a player object", e);
				}
			} else {
				throw new FoulItemFormatException("field \""
						+ HandballTickerStream.StreamItem.Foul.KEY_PLAYER
						+ "\" is malformed: \"" + player
						+ "\" is not a JSON object");
			}
		} else {
			throw new FoulItemFormatException("field \""
					+ HandballTickerStream.StreamItem.Foul.KEY_PLAYER
					+ "\" is missing");
		}
	}

	private static List<Discipline> parseDisciplines(final JSONObject foulObject)
			throws FoulItemFormatException {
		final Object oDisciplines = foulObject
				.get(HandballTickerStream.StreamItem.Foul.KEY_DISCIPLINES);
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
											+ HandballTickerStream.StreamItem.Foul.KEY_DISCIPLINES
											+ "\" is malformed: \""
											+ sDiscipline
											+ "\" is not a discipline");
						}
					}

					return disciplines;
				}
			} else {
				throw new FoulItemFormatException("field \""
						+ HandballTickerStream.StreamItem.Foul.KEY_DISCIPLINES
						+ "\" is malformed: \"" + oDisciplines
						+ "\" is not a JSON array");
			}
		}

		return null;
	}

	private static Player parseVictim(final JSONObject foulObject)
			throws FoulItemFormatException {
		final Object victim = foulObject
				.get(HandballTickerStream.StreamItem.Foul.KEY_VICTIM);
		if (victim != null) {
			if (victim instanceof JSONObject) {
				try {
					return Player.loadFromJSON((JSONObject) victim);
				} catch (final PlayerFormatException e) {
					throw new FoulItemFormatException("field \""
							+ HandballTickerStream.StreamItem.Foul.KEY_VICTIM
							+ "\" is not a player object", e);
				}
			} else {
				throw new FoulItemFormatException("field \""
						+ HandballTickerStream.StreamItem.Foul.KEY_VICTIM
						+ "\" is malformed: \"" + victim
						+ "\" is not a JSON object");
			}
		}

		return null;
	}

}