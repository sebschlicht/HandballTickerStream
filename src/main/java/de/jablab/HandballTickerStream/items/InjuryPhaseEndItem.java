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
		final JSONObject object = new JSONObject();
		object.put(HandballTickerStream.StreamItem.PhaseEnd.Injury.KEY_PLAYER,
				this.player.toJSON());
		return object;
	}

	public static InjuryPhaseEndItem parseJSON(final String jsonString)
			throws StreamItemFormatException {
		final JSONObject injuryItem = parseJSONObject(jsonString);
		if (injuryItem != null) {
			return loadFromJSON(injuryItem);
		} else {
			throw new StreamItemFormatException("\"" + jsonString
					+ "\" is not a JSON String");
		}
	}

	public static InjuryPhaseEndItem loadFromJSON(final JSONObject streamItem)
			throws StreamItemFormatException {
		final PhaseEndItemInformation phaseEndItemInfo = loadPhaseEndItemInformation(streamItem);

		if (phaseEndItemInfo.getSubType() == PhaseEndSubType.INJURY) {
			final JSONObject injuryObject = phaseEndItemInfo
					.getSubObject();

			final Player player = parsePlayer(injuryObject);

			return new InjuryPhaseEndItem(phaseEndItemInfo.getPublished(),
					phaseEndItemInfo.getTime(), phaseEndItemInfo.getMessage(),
					phaseEndItemInfo.getBefore(), phaseEndItemInfo.getAfter(),
					player);
		} else {
			throw new InjuryPhaseEndItemFormatException("field \""
					+ HandballTickerStream.StreamItem.PhaseEnd.KEY_SUB_TYPE
					+ "\" is invalid: must be \"" + PhaseEndSubType.INJURY
					+ "\" but was \"" + phaseEndItemInfo.getSubType() + "\"");
		}
	}

	private static Player parsePlayer(final JSONObject injuryObject)
			throws InjuryPhaseEndItemFormatException {
		final Object player = injuryObject
				.get(HandballTickerStream.StreamItem.PhaseEnd.Injury.KEY_PLAYER);
		if (player != null) {
			if (player instanceof JSONObject) {
				try {
					return Player.loadFromJSON((JSONObject) player);
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