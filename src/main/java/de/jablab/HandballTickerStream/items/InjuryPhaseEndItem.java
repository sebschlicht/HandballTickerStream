package de.jablab.HandballTickerStream.items;

import java.util.Date;

import org.json.simple.JSONObject;

import de.jablab.HandballTickerStream.HandballTickerStream;
import de.jablab.HandballTickerStream.MatchPhase;
import de.jablab.HandballTickerStream.MatchTime;
import de.jablab.HandballTickerStream.Player;
import de.jablab.HandballTickerStream.exceptions.PlayerFormatException;
import de.jablab.HandballTickerStream.exceptions.StreamItemFormatException;
import de.jablab.HandballTickerStream.exceptions.items.InjuryPhaseEndItemFormatException;
import de.jablab.HandballTickerStream.exceptions.items.PhaseEndItemFormatException;

/**
 * match phase end due to an injury stream item
 * 
 * @author sebschlicht
 * 
 */
public class InjuryPhaseEndItem extends PhaseEndItem {

	/**
	 * player that was injured
	 */
	private Player player;

	/**
	 * create a new match phase end due to an injury stream item
	 * 
	 * @param published
	 *            exact time when published
	 * @param time
	 *            match time when published<br>
	 *            considered to be the time of the happening
	 * @param message
	 *            message displayed instead of a generated value
	 * @param before
	 *            phase that ended
	 * @param after
	 *            new phase that begun
	 * @param player
	 *            player that was injured
	 */
	public InjuryPhaseEndItem(final Date published, final MatchTime time,
			final String message, final MatchPhase before,
			final MatchPhase after, final Player player) {
		super(published, time, message, before, after);
		this.setSubType(PhaseEndSubType.INJURY);
		this.player = player;
	}

	/**
	 * @return player that was injured
	 */
	public Player getPlayer() {
		return this.player;
	}

	public void setPlayer(final Player player) {
		this.player = player;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected JSONObject getPhaseEndObjectJSON() {
		final JSONObject object = super.getPhaseEndObjectJSON();
		object.put(HandballTickerStream.StreamItem.PhaseEnd.Injury.KEY_PLAYER,
				this.player.toJSON());
		return object;
	}

	/**
	 * load an injury phase end stream item from JSON
	 * 
	 * @param jsonString
	 *            injury phase end stream item JSON
	 * @throws InjuryPhaseEndItemFormatException
	 *             if the JSON is not a valid injury phase end stream item
	 *             object
	 * @throws PhaseEndItemFormatException
	 *             if the JSON is not a valid phase end stream item object
	 * @throws StreamItemFormatException
	 *             if the JSON is not a valid stream item object
	 */
	public static InjuryPhaseEndItem parseJSON(final String jsonString)
			throws StreamItemFormatException {
		JSONObject injuryPhaseEndStreamItem;
		try {
			injuryPhaseEndStreamItem = (JSONObject) JSON_PARSER
					.parse(jsonString);
		} catch (final org.json.simple.parser.ParseException e) {
			throw new StreamItemFormatException("\"" + jsonString
					+ "\" is not a JSON String");
		}

		final PhaseEndItemInformation phaseEndItemInformation = PhaseEndItem
				.parsePhaseEndItemJSON(injuryPhaseEndStreamItem);
		final JSONObject object = phaseEndItemInformation.getObject();

		if (phaseEndItemInformation.getSubType() == PhaseEndSubType.INJURY) {
			final Player player = parsePlayer(object);

			return new InjuryPhaseEndItem(
					phaseEndItemInformation.getPublished(),
					phaseEndItemInformation.getTime(),
					phaseEndItemInformation.getMessage(),
					phaseEndItemInformation.getBefore(),
					phaseEndItemInformation.getAfter(), player);
		} else {
			throw new InjuryPhaseEndItemFormatException("field \""
					+ HandballTickerStream.StreamItem.PhaseEnd.KEY_SUB_TYPE
					+ "\" is invalid: must be \"" + PhaseEndSubType.INJURY
					+ "\" but was \"" + phaseEndItemInformation.getSubType()
					+ "\"");
		}
	}

	/**
	 * parse the player field
	 * 
	 * @param object
	 *            injury phase end stream item object JSON object
	 * @return player that was injured
	 * @throws InjuryPhaseEndItemFormatException
	 *             if field missing, malformed or not a player object
	 */
	private static Player parsePlayer(final JSONObject object)
			throws InjuryPhaseEndItemFormatException {
		final Object player = object
				.get(HandballTickerStream.StreamItem.PhaseEnd.Injury.KEY_PLAYER);
		if (player != null) {
			if (player instanceof JSONObject) {
				try {
					return Player.parseJSON((JSONObject) player);
				} catch (final PlayerFormatException e) {
					throw new InjuryPhaseEndItemFormatException(
							"field \""
									+ HandballTickerStream.StreamItem.PhaseEnd.Injury.KEY_PLAYER
									+ "\" is not a player object", e);
				}
			} else {
				throw new InjuryPhaseEndItemFormatException(
						"field \""
								+ HandballTickerStream.StreamItem.PhaseEnd.Injury.KEY_PLAYER
								+ "\" is malformed: \"" + player
								+ "\" is not a JSON object");
			}
		} else {
			throw new InjuryPhaseEndItemFormatException(
					"field \""
							+ HandballTickerStream.StreamItem.PhaseEnd.Injury.KEY_PLAYER
							+ "\" is missing");
		}
	}

}