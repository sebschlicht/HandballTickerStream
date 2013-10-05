package de.jablab.HandballTickerStream.matchdata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.jablab.HandballTickerStream.HandballTickerStream;
import de.jablab.HandballTickerStream.exceptions.TeamFormatException;

public class TeamTest {

	private static final int VALID_IDENTIFIER = 1;
	private static final String VALID_NAME = "HSV Schienbeinknacker";
	private static final String VALID_LOGO_URL_STRING = "http://hsvschienbeinknacker.de/logo.png";
	private static URL VALID_LOGO_URL;

	private static final String MALFORMED_IDENTIFIER = "noidentifier";
	private static final String MALFORMED_LOGO_URL_STRING = "test/ /de/logo.png";

	private static Team SOURCE_TEAM;
	private JSONObject sourceTeamObject;

	private Team team;

	@BeforeClass
	public static void beforeClass() throws MalformedURLException {
		VALID_LOGO_URL = new URL(VALID_LOGO_URL_STRING);
		SOURCE_TEAM = new Team(VALID_IDENTIFIER, VALID_NAME, VALID_LOGO_URL);
	}

	@Before
	public void setUp() {
		this.sourceTeamObject = SOURCE_TEAM.toJSON();
	}

	private void loadTeam(final boolean error) {
		try {
			this.team = new Team(this.sourceTeamObject);
		} catch (final TeamFormatException e) {
			if (!error) {
				e.printStackTrace();
			}
		}

		if (error) {
			assertNull(this.team);
		} else {
			assertNotNull(this.team);
		}
	}

	@Test
	public void testValidTeam() {
		this.loadTeam(false);

		assertEquals(SOURCE_TEAM.getId(), this.team.getId());
		assertEquals(SOURCE_TEAM.getName(), this.team.getName());
		assertEquals(VALID_LOGO_URL_STRING, this.team.getLogoUrl().toString());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testIdentifierMissing() {
		this.sourceTeamObject.put(HandballTickerStream.Team.KEY_IDENTIFIER,
				null);
		this.loadTeam(true);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testIdentifierMalformed() {
		this.sourceTeamObject.put(HandballTickerStream.Team.KEY_IDENTIFIER,
				MALFORMED_IDENTIFIER);
		this.loadTeam(true);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testNameMissing() {
		this.sourceTeamObject.put(HandballTickerStream.Team.KEY_NAME, null);
		this.loadTeam(true);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testLogoUrlMissing() {
		this.sourceTeamObject.put(HandballTickerStream.Team.KEY_LOGO_URL, null);
		this.loadTeam(false);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testLogoUrlMalformed() {
		this.sourceTeamObject.put(HandballTickerStream.Team.KEY_LOGO_URL,
				MALFORMED_LOGO_URL_STRING);
		this.loadTeam(true);
	}

}