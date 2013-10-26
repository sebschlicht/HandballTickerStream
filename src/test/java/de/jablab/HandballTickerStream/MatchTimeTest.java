package de.jablab.HandballTickerStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.jablab.HandballTickerStream.exceptions.MatchTimeFormatException;

public class MatchTimeTest extends StreamableTest {

	private static final int VALID_MINUTE = 36;
	private static final MatchPhase VALID_PHASE = MatchPhase.SECOND;

	private static final String MALFORMED_MINUTE = "nominute";

	private static MatchTime SOURCE_MATCH_TIME;

	private MatchTime matchTime;

	@BeforeClass
	public static void beforeClass() {
		SOURCE_MATCH_TIME = new MatchTime(VALID_MINUTE, VALID_PHASE);
	}

	@Before
	public void setUp() {
		this.sourceObject = SOURCE_MATCH_TIME.toJSON();
	}

	private void loadMatchTime(final boolean error) {
		try {
			this.matchTime = MatchTime.loadFromJSON(this.sourceObject);
		} catch (final MatchTimeFormatException e) {
			this.setErrTrace(e.getMessage());
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
	public void testMatchTimeMinuteMissing() {
		this.putToObject(HandballTickerStream.MatchTime.KEY_MINUTE, null);
		this.loadMatchTime(true);
		this.checkForMissingField(HandballTickerStream.MatchTime.KEY_MINUTE);
	}

	@Test
	public void testMatchTimeMinuteMalformed() {
		this.putToObject(HandballTickerStream.MatchTime.KEY_MINUTE,
				MALFORMED_MINUTE);
		this.loadMatchTime(true);
	}

	@Test
	public void testMatchTimePhaseMissing() {
		this.putToObject(HandballTickerStream.MatchTime.KEY_PHASE, null);
		this.loadMatchTime(true);
		this.checkForMissingField(HandballTickerStream.MatchTime.KEY_PHASE);
	}

}