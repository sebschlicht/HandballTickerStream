package de.jablab.HandballTickerStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.jablab.HandballTickerStream.exceptions.TeamFormatException;

public class TeamTest extends StreamableTest {

	private static final int VALID_IDENTIFIER = 1;
	private static final String VALID_NAME = "HSV Schienbeinknacker";
	private static final String VALID_LOGO_URL_STRING = "http://hsvschienbeinknacker.de/logo.png";
	private static URL VALID_LOGO_URL;

	private static final String MALFORMED_IDENTIFIER = "noidentifier";
	private static final String MALFORMED_LOGO_URL_STRING = "test/ /de/logo.png";

	private static Team SOURCE_TEAM;

	private Team team;

	@BeforeClass
	public static void beforeClass() throws MalformedURLException {
		VALID_LOGO_URL = new URL(VALID_LOGO_URL_STRING);
		SOURCE_TEAM = new Team(VALID_IDENTIFIER, VALID_NAME, VALID_LOGO_URL);
	}

	@Before
	public void setUp() {
		this.sourceObject = SOURCE_TEAM.toJSON();
	}

	private void loadTeam(final boolean error) {
		try {
			this.team = Team.parseJSON(this.sourceObject);
		} catch (final TeamFormatException e) {
			this.setErrTrace(e.getMessage());
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
	public void testIdentifierMissing() {
		this.putToObject(HandballTickerStream.Team.KEY_IDENTIFIER, null);
		this.loadTeam(true);
		this.checkForMissingField(HandballTickerStream.Team.KEY_IDENTIFIER);
	}

	@Test
	public void testIdentifierMalformed() {
		this.putToObject(HandballTickerStream.Team.KEY_IDENTIFIER,
				MALFORMED_IDENTIFIER);
		this.loadTeam(true);
		this.checkForMalformedField(HandballTickerStream.Team.KEY_IDENTIFIER);
	}

	@Test
	public void testNameMissing() {
		this.putToObject(HandballTickerStream.Team.KEY_NAME, null);
		this.loadTeam(true);
		this.checkForMissingField(HandballTickerStream.Team.KEY_NAME);
	}

	@Test
	public void testLogoUrlMissing() {
		this.putToObject(HandballTickerStream.Team.KEY_LOGO_URL, null);
		this.loadTeam(false);
	}

	@Test
	public void testLogoUrlMalformed() {
		this.putToObject(HandballTickerStream.Team.KEY_LOGO_URL,
				MALFORMED_LOGO_URL_STRING);
		this.loadTeam(true);
		this.checkForMalformedField(HandballTickerStream.Team.KEY_LOGO_URL);
	}

}