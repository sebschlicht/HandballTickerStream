package de.jablab.HandballTickerStream.items;

import java.util.Date;

import org.json.simple.JSONObject;

import de.jablab.HandballTickerStream.HandballTickerStream;
import de.jablab.HandballTickerStream.MatchPhase;
import de.jablab.HandballTickerStream.MatchTime;
import de.jablab.HandballTickerStream.TeamRole;
import de.jablab.HandballTickerStream.exceptions.StreamItemFormatException;
import de.jablab.HandballTickerStream.exceptions.items.TimeoutPhaseEndItemFormatException;

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
		this.setSubType(PhaseEndSubType.TIMEOUT);
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

	@Override
	@SuppressWarnings("unchecked")
	protected JSONObject getPhaseEndObjectJSON() {
		final JSONObject object = new JSONObject();
		object.put(
				HandballTickerStream.StreamItem.PhaseEnd.Timeout.KEY_TEAM_ROLE,
				this.team.toString());
		return object;
	}

	public static TimeoutPhaseEndItem parseJSON(final String jsonString)
			throws StreamItemFormatException {
		final JSONObject timeoutItem = parseJSONObject(jsonString);
		if (timeoutItem != null) {
			return loadFromJSON(timeoutItem);
		} else {
			throw new StreamItemFormatException("\"" + jsonString
					+ "\" is not a JSON String");
		}
	}

	public static TimeoutPhaseEndItem loadFromJSON(final JSONObject streamItem)
			throws StreamItemFormatException {
		final PhaseEndItemInformation phaseEndItemInfo = loadPhaseEndItemInformation(streamItem);

		if (phaseEndItemInfo.getSubType() == PhaseEndSubType.TIMEOUT) {
			final JSONObject timeoutObject = phaseEndItemInfo
					.getSubObject();

			final TeamRole team = parseTeamRole(timeoutObject);

			return new TimeoutPhaseEndItem(phaseEndItemInfo.getPublished(),
					phaseEndItemInfo.getTime(), phaseEndItemInfo.getMessage(),
					phaseEndItemInfo.getBefore(), phaseEndItemInfo.getAfter(),
					team);
		} else {
			throw new TimeoutPhaseEndItemFormatException("field \""
					+ HandballTickerStream.StreamItem.PhaseEnd.KEY_SUB_TYPE
					+ "\" is invalid: must be \"" + PhaseEndSubType.TIMEOUT
					+ "\" but was \"" + phaseEndItemInfo.getSubType() + "\"");
		}
	}

	private static TeamRole parseTeamRole(final JSONObject timeoutObject)
			throws TimeoutPhaseEndItemFormatException {
		final String sTeamRole = (String) timeoutObject
				.get(HandballTickerStream.StreamItem.PhaseEnd.Timeout.KEY_TEAM_ROLE);
		if (sTeamRole != null) {
			final TeamRole team = TeamRole.parseString(sTeamRole);
			if (team != null) {
				return team;
			} else {
				throw new TimeoutPhaseEndItemFormatException(
						"field \""
								+ HandballTickerStream.StreamItem.PhaseEnd.Timeout.KEY_TEAM_ROLE
								+ "\" is malformed: \"" + sTeamRole
								+ "\" is not a team role");
			}
		} else {
			throw new TimeoutPhaseEndItemFormatException(
					"field \""
							+ HandballTickerStream.StreamItem.PhaseEnd.Timeout.KEY_TEAM_ROLE
							+ "\" is missing");
		}
	}

}