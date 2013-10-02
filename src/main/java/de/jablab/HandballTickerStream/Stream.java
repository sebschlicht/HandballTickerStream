package de.jablab.HandballTickerStream;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import de.jablab.HandballTickerStream.exceptions.MatchTimeFormatException;
import de.jablab.HandballTickerStream.exceptions.StreamFormatException;
import de.jablab.HandballTickerStream.exceptions.TeamFormatException;

public class Stream {

	static final class Keys {

		public static final String TIME = "time";
		public static final String TEAMS = "teams";
		public static final String SCORES = "scores";
		public static final String ITEMS = "items";

		public static class MatchTime {

			public static final String MINUTE = "minute";
			public static final String PHASE = "half";

		}

		public static class Team {

			public static final String IDENTIFIER = "id";

		}

	}

	/**
	 * current match time
	 */
	private final MatchTime time;

	/**
	 * teams involved in the current match
	 */
	private final List<Team> teams;

	public Stream(final JSONObject stream) throws StreamFormatException {
		final JSONObject time = (JSONObject) stream.get(Keys.TIME);
		if (time == null) {
			throw new StreamFormatException("\"" + Keys.TIME + "\" is missing",
					null);
		} else {
			try {
				this.time = new MatchTime(time);
			} catch (final MatchTimeFormatException e) {
				throw new StreamFormatException(null, e);
			}
		}

		final JSONArray teams = (JSONArray) stream.get(Keys.TEAMS);
		if (teams == null) {
			throw new StreamFormatException(
					"\"" + Keys.TEAMS + "\" is missing", null);
		} else {
			if (teams.isEmpty()) {
				throw new StreamFormatException("\"" + Keys.TEAMS
						+ "\" is empty", null);
			}
			this.teams = new ArrayList<Team>((int) (teams.size() / 0.75));

			Team team;
			for (Object tmp : teams) {
				try {
					team = new Team((JSONObject) tmp);
					this.teams.add(team);
				} catch (final TeamFormatException e) {
					throw new StreamFormatException(null, e);
				}
			}

			// TODO:
			// benchmark try/catch against JSONObject containing stacked error
			// messages against a StringBuilder with the same concept
		}
	}

	/**
	 * @return current match time
	 */
	public MatchTime getTime() {
		return this.time;
	}

}