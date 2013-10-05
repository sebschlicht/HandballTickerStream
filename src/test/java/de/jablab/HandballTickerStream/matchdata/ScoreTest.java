package de.jablab.HandballTickerStream.matchdata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.jablab.HandballTickerStream.HandballTickerStream;
import de.jablab.HandballTickerStream.exceptions.ScoreFormatException;

public class ScoreTest {

	private static final int VALID_SCORE_HOME = 22;
	private static final int VALID_SCORE_GUEST = 21;

	private static final String MALFORMED_SCORE = "noscore";

	private static Score SOURCE_SCORE;
	private JSONObject sourceScoreObject;

	private Score score;

	@BeforeClass
	public static void beforeClass() {
		SOURCE_SCORE = new Score(VALID_SCORE_HOME, VALID_SCORE_GUEST);
	}

	@Before
	public void setUp() {
		this.sourceScoreObject = SOURCE_SCORE.toJSON();
	}

	private void loadScore(final boolean error) {
		try {
			this.score = new Score(this.sourceScoreObject);
		} catch (final ScoreFormatException e) {
			if (!error) {
				e.printStackTrace();
			}
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
	@SuppressWarnings("unchecked")
	public void testHomeMissing() {
		this.sourceScoreObject.put(HandballTickerStream.Score.KEY_HOME, null);
		this.loadScore(true);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testHomeMalformed() {
		this.sourceScoreObject.put(HandballTickerStream.Score.KEY_HOME,
				MALFORMED_SCORE);
		this.loadScore(true);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testGuestMissing() {
		this.sourceScoreObject.put(HandballTickerStream.Score.KEY_GUEST, null);
		this.loadScore(true);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testGuestMalformed() {
		this.sourceScoreObject.put(HandballTickerStream.Score.KEY_GUEST,
				MALFORMED_SCORE);
		this.loadScore(true);
	}

}