package de.jablab.HandballTickerStream.items;

import java.util.Date;

import de.jablab.HandballTickerStream.MatchPhase;
import de.jablab.HandballTickerStream.MatchTime;
import de.jablab.HandballTickerStream.TeamRole;

/**
 * match phase end due to a timeout stream item
 * 
 * @author sebschlicht
 * 
 */
public class TimeoutPhaseEndItem extends PhaseEndItem {

	/**
	 * role of the team that used its timeout
	 */
	private TeamRole team;

	/**
	 * create a new match phase end due to a timeout stream item
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
	 * @param team
	 *            role of the team that used its timeout
	 */
	public TimeoutPhaseEndItem(final Date published, final MatchTime time,
			final String message, final MatchPhase before,
			final MatchPhase after, final TeamRole team) {
		super(published, time, message, before, after);
		this.team = team;
	}

	/**
	 * @return role of the team that used its timeout
	 */
	public TeamRole getTeam() {
		return this.team;
	}

	public void setTeam(final TeamRole team) {
		this.team = team;
	}

}