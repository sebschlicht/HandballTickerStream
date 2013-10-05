package de.jablab.HandballTickerStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.jablab.HandballTickerStream.exceptions.MatchTimeFormatException;

public class MatchTimeTest {

	private static final int VALID_MINUTE = 36;
	private static final MatchPhase VALID_PHASE = MatchPhase.SECOND;

	private static final String MALFORMED_MINUTE = "nominute";

	private static MatchTime SOURCE_MATCH_TIME;
	private JSONObject sourceMatchTimeObject;

	private MatchTime matchTime;

	@BeforeClass
	public static void beforeClass() {
		SOURCE_MATCH_TIME = new MatchTime(VALID_MINUTE, VALID_PHASE);
	}

	@Before
	public void setUp() {
		this.sourceMatchTimeObject = SOURCE_MATCH_TIME.toJSON();
	}

	private void loadMatchTime(final boolean error) {
		try {
			this.matchTime = MatchTime.parseJSON(this.sourceMatchTimeObject);
		} catch (final MatchTimeFormatException e) {
			if (!error) {
				e.printStackTrace();
			}
		}

		if (error) {
			assertNull(this.matchTime);
		} else {
			assertNotNull(this.matchTime);
		}
	}

	@Test
	public void testValidMatchTime() {
		this.loadMatchTime(false);

		assertEquals(SOURCE_MATCH_TIME.getMinute(), this.matchTime.getMinute());
		assertEquals(SOURCE_MATCH_TIME.getPhase(), this.matchTime.getPhase());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testMatchTimeMinuteMissing() {
		this.sourceMatchTimeObject.put(HandballTickerStream.MatchTime.KEY_MINUTE,
				null);
		this.loadMatchTime(true);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testMatchTimeMinuteMalformed() {
		this.sourceMatchTimeObject.put(HandballTickerStream.MatchTime.KEY_MINUTE,
				MALFORMED_MINUTE);
		this.loadMatchTime(true);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testMatchTimePhaseMissing() {
		this.sourceMatchTimeObject
				.put(HandballTickerStream.MatchTime.KEY_PHASE, null);
		this.loadMatchTime(true);
	}

}