package de.jablab.HandballTickerStream.matchdata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import de.jablab.HandballTickerStream.HandballTickerStream;
import de.jablab.HandballTickerStream.exceptions.MatchTimeFormatException;

public class MatchTimeTest {

	private static final int VALID_MINUTE = 36;
	private static final MatchPhase VALID_PHASE = MatchPhase.SECOND;

	private static final String MALFORMED_MINUTE = "nominute";

	private MatchTime sourceMatchTime;
	private JSONObject matchTimeObject;

	private MatchTime matchTime;

	@Before
	public void setUp() {
		this.sourceMatchTime = new MatchTime(VALID_MINUTE, VALID_PHASE);
		this.matchTimeObject = this.sourceMatchTime.toJSON();
	}

	private void loadMatchTime(final boolean error) {
		try {
			this.matchTime = new MatchTime(this.matchTimeObject);
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

		assertEquals(this.sourceMatchTime.getMinute(),
				this.matchTime.getMinute());
		assertEquals(this.sourceMatchTime.getPhase(), this.matchTime.getPhase());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testMatchTimeMinuteMissing() {
		this.matchTimeObject.put(HandballTickerStream.MatchTime.KEY_MINUTE,
				null);
		this.loadMatchTime(true);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testMatchTimeMinuteMalformed() {
		this.matchTimeObject.put(HandballTickerStream.MatchTime.KEY_MINUTE,
				MALFORMED_MINUTE);
		this.loadMatchTime(true);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testMatchTimePhaseMissing() {
		this.matchTimeObject
				.put(HandballTickerStream.MatchTime.KEY_PHASE, null);
		this.loadMatchTime(true);
	}

}