package de.jablab.HandballTickerStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.jablab.HandballTickerStream.exceptions.PlayerFormatException;

public class PlayerTest extends StreamableTest {

	private static final int VALID_IDENTIFIER = 12;
	private static final int VALID_NUMBER = 1;
	private static final String VALID_NAME = "Mr. T";
	private static final TeamRole VALID_TEAM = TeamRole.HOME;

	private static final String MALFORMED_NUMBER = "nonumber";

	private static Player SOURCE_PLAYER;

	private Player player;

	@BeforeClass
	public static void beforeClass() {
		SOURCE_PLAYER = new Player(VALID_IDENTIFIER, VALID_NUMBER, VALID_NAME,
				VALID_TEAM);
	}

	@Before
	public void setUp() {
		this.sourceObject = SOURCE_PLAYER.toJSON();
	}

	private void loadPlayer(final boolean error) {
		try {
			this.player = Player.parseJSON(this.sourceObject);
		} catch (final PlayerFormatException e) {
			this.setErrTrace(e.getMessage());
		}

		if (error) {
			assertNull(this.player);
		} else {
			assertNotNull(this.player);
		}
	}

	@Test
	public void testValidPlayer() {
		this.loadPlayer(false);

		assertEquals(SOURCE_PLAYER.getId(), this.player.getId());
		assertEquals(SOURCE_PLAYER.getNumber(), this.player.getNumber());
		assertEquals(SOURCE_PLAYER.getName(), this.player.getName());
		assertEquals(SOURCE_PLAYER.getTeam(), this.player.getTeam());
	}

	@Test
	public void testIdentifierMissing() {
		this.putToObject(HandballTickerStream.Player.KEY_IDENTIFIER, null);
		this.loadPlayer(false);
		assertNull(this.player.getId());
	}

	@Test
	public void testIdentifierMalformed() {
		this.putToObject(HandballTickerStream.Player.KEY_IDENTIFIER,
				MALFORMED_NUMBER);
		this.loadPlayer(true);
	}

	@Test
	public void testNumberMissing() {
		this.putToObject(HandballTickerStream.Player.KEY_NUMBER, null);
		this.loadPlayer(true);
		this.checkForMissingField(HandballTickerStream.Player.KEY_NUMBER);
	}

	@Test
	public void testNumberMalformed() {
		this.putToObject(HandballTickerStream.Player.KEY_NUMBER,
				MALFORMED_NUMBER);
		this.loadPlayer(true);
	}

	@Test
	public void testNameMissing() {
		this.putToObject(HandballTickerStream.Player.KEY_NAME, null);
		this.loadPlayer(false);
		assertNull(this.player.getName());
	}

	@Test
	public void testTeamMissing() {
		this.putToObject(HandballTickerStream.Player.KEY_TEAM_ROLE, null);
		this.loadPlayer(true);
		this.checkForMissingField(HandballTickerStream.Player.KEY_TEAM_ROLE);
	}

}