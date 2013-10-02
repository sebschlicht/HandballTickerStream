package de.jablab.HandballTickerStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import de.jablab.HandballTickerStream.exceptions.StreamFormatException;

public class StreamTest {

	private static final String MATCH_TIME_VALID_MINUTE = "3";
	private static final MatchPhase MATCH_TIME_VALID_PHASE = MatchPhase.FIRST;

	private static final String TEAM_VALID_ID = "1";

	private static JSONObject STREAM_OBJECT;
	private static JSONObject TIME_OBJECT;
	private static JSONArray TEAMS_LIST;
	private static JSONObject TEAM;

	private Stream stream;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		TIME_OBJECT = new JSONObject();
		STREAM_OBJECT = new JSONObject();
		TEAMS_LIST = new JSONArray();
		TEAM = new JSONObject();

		TIME_OBJECT.put(Stream.Keys.MatchTime.MINUTE, MATCH_TIME_VALID_MINUTE);
		TIME_OBJECT.put(Stream.Keys.MatchTime.PHASE,
				MATCH_TIME_VALID_PHASE.toString());

		TEAM.put(Stream.Keys.Team.IDENTIFIER, TEAM_VALID_ID);
	}

	@SuppressWarnings("unchecked")
	private void parseStream(final boolean expected) {
		STREAM_OBJECT.put(Stream.Keys.TIME, TIME_OBJECT);
		if ((TEAMS_LIST != null) && (TEAM != null)) {
			TEAMS_LIST.add(TEAM);
		}
		STREAM_OBJECT.put(Stream.Keys.TEAMS, TEAMS_LIST);

		try {
			this.stream = new Stream(STREAM_OBJECT);
		} catch (final StreamFormatException e) {
			this.stream = null;

			if (!expected) {
				e.printStackTrace();
			}
		}

		if (expected) {
			assertNull(this.stream);
		} else {
			assertNotNull(this.stream);
		}
	}

	@Test
	public void testParseStream() {
		this.parseStream(false);

		final MatchTime time = this.stream.getTime();
		assertNotNull(time);
		assertEquals(MATCH_TIME_VALID_MINUTE, String.valueOf(time.getMinute()));
		assertEquals(MATCH_TIME_VALID_PHASE, time.getPhase());
	}

	@Test
	public void testMatchTimeMissing() {
		TIME_OBJECT = null;
		this.parseStream(true);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testMatchTimeMinuteMissing() {
		TIME_OBJECT.put(Stream.Keys.MatchTime.MINUTE, null);
		this.parseStream(true);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testMatchTimePhaseMissing() {
		TIME_OBJECT.put(Stream.Keys.MatchTime.PHASE, null);
		this.parseStream(true);
	}

	@Test
	public void testTeamsMissing() {
		TEAMS_LIST = null;
		this.parseStream(true);
	}

	@Test
	public void testTeamsEmpty() {
		TEAM = null;
		this.parseStream(true);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testTeamsIdentifierMissing() {
		TEAM.put(Stream.Keys.Team.IDENTIFIER, null);
		this.parseStream(true);
	}

}