package de.jablab.HandballTickerStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.jablab.HandballTickerStream.exceptions.ScoreFormatException;

public class ScoreTest extends StreamableTest {

	private static final int VALID_SCORE_HOME = 22;
	private static final int VALID_SCORE_GUEST = 21;

	private static final String MALFORMED_SCORE = "noscore";

	private static Score SOURCE_SCORE;

	private Score score;

	@BeforeClass
	public static void beforeClass() {
		SOURCE_SCORE = new Score(VALID_SCORE_HOME, VALID_SCORE_GUEST);
	}

	@Before
	public void setUp() {
		this.sourceObject = SOURCE_SCORE.toJSON();
	}

	private void loadScore(final boolean error) {
		try {
			this.score = Score.parseJSON(this.sourceObject);
		} catch (final ScoreFormatException e) {
			this.setErrTrace(e.getMessage());
		}

		if (error) {
			assertNull(this.score);
		} else {
			assertNotNull(this.score);
		}
	}

	@Test
	public void testValidScore() {
		this.loadScore(false);

		assertEquals(SOURCE_SCORE.getHome(), this.score.getHome());
		assertEquals(SOURCE_SCORE.getGuest(), this.score.getGuest());
	}

	@Test
	public void testHomeMissing() {
		this.putToObject(HandballTickerStream.Score.KEY_HOME, null);
		this.loadScore(true);
		this.checkForMissingField(HandballTickerStream.Score.KEY_HOME);
	}

	@Test
	public void testHomeMalformed() {
		this.putToObject(HandballTickerStream.Score.KEY_HOME, MALFORMED_SCORE);
		this.loadScore(true);
		this.checkForMalformedField(HandballTickerStream.Score.KEY_HOME);
	}

	@Test
	public void testGuestMissing() {
		this.putToObject(HandballTickerStream.Score.KEY_GUEST, null);
		this.loadScore(true);
		this.checkForMissingField(HandballTickerStream.Score.KEY_GUEST);
	}

	@Test
	public void testGuestMalformed() {
		this.putToObject(HandballTickerStream.Score.KEY_GUEST, MALFORMED_SCORE);
		this.loadScore(true);
		this.checkForMalformedField(HandballTickerStream.Score.KEY_GUEST);
	}

}