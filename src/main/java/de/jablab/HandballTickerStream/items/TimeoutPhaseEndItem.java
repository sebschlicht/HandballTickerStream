package de.jablab.HandballTickerStream.items;

import java.util.Date;

import org.json.simple.JSONObject;

import de.jablab.HandballTickerStream.HandballTickerStream;
import de.jablab.HandballTickerStream.MatchPhase;
import de.jablab.HandballTickerStream.MatchTime;
import de.jablab.HandballTickerStream.TeamRole;
import de.jablab.HandballTickerStream.exceptions.StreamItemFormatException;
import de.jablab.HandballTickerStream.exceptions.items.PhaseEndItemFormatException;
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
		final JSONObject object = super.getPhaseEndObjectJSON();
		object.put(
				HandballTickerStream.StreamItem.PhaseEnd.Timeout.KEY_TEAM_ROLE,
				this.team.toString());
		return object;
	}

	/**
	 * load a timeout phase end stream item from JSON
	 * 
	 * @param jsonString
	 *            timeout phase end stream item JSON
	 * @throws TimeoutPhaseEndItemFormatException
	 *             if the JSON is not a valid timeout phase end stream item
	 *             object
	 * @throws PhaseEndItemFormatException
	 *             if the JSON is not a valid phase end stream item object
	 * @throws StreamItemFormatException
	 *             if the JSON is not a valid stream item object
	 */
	public static TimeoutPhaseEndItem parseJSON(final String jsonString)
			throws StreamItemFormatException {
		JSONObject timeoutPhaseEndStreamItem;
		try {
			timeoutPhaseEndStreamItem = (JSONObject) JSON_PARSER
					.parse(jsonString);
		} catch (final org.json.simple.parser.ParseException e) {
			throw new StreamItemFormatException("\"" + jsonString
					+ "\" is not a JSON String");
		}

		final PhaseEndItemInformation phaseEndItemInformation = PhaseEndItem
				.parsePhaseEndItemJSON(timeoutPhaseEndStreamItem);
		final JSONObject object = phaseEndItemInformation.getObject();

		if (phaseEndItemInformation.getSubType() == PhaseEndSubType.TIMEOUT) {
			final TeamRole team = parseTeamRole(object);

			return new TimeoutPhaseEndItem(
					phaseEndItemInformation.getPublished(),
					phaseEndItemInformation.getTime(),
					phaseEndItemInformation.getMessage(),
					phaseEndItemInformation.getBefore(),
					phaseEndItemInformation.getAfter(), team);
		} else {
			throw new TimeoutPhaseEndItemFormatException("field \""
					+ HandballTickerStream.StreamItem.PhaseEnd.KEY_SUB_TYPE
					+ "\" is invalid: must be \"" + PhaseEndSubType.TIMEOUT
					+ "\"");
		}
	}

	/**
	 * parse the team field
	 * 
	 * @param object
	 *            timeout phase end stream item object JSON object
	 * @return role of the team that used its timeout
	 * @throws TimeoutPhaseEndItemFormatException
	 *             if field missing or malformed
	 */
	private static TeamRole parseTeamRole(final JSONObject object)
			throws TimeoutPhaseEndItemFormatException {
		final String sTeamRole = (String) object
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