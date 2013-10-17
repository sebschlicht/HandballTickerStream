package de.jablab.HandballTickerStream.items;

import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import de.jablab.HandballTickerStream.HandballTickerStream;
import de.jablab.HandballTickerStream.MatchTime;
import de.jablab.HandballTickerStream.Player;
import de.jablab.HandballTickerStream.StreamItem;
import de.jablab.HandballTickerStream.StreamItemType;

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
		if (this.disciplines != null) {
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

}