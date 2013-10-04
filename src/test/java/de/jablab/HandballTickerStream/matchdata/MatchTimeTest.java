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

	private static final String INVALID_MINUTE = "nominute";
	private static final String INVALID_MATCH_PHASE = "nophase";

	private MatchTime sourceMatchTime;
	private JSONObject matchTimeObject;

	private MatchTime matchTime;

	@Before
	public void setUp() {
		this.sourceMatchTime = new MatchTime(36, MatchPhase.SECOND);
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
	public void testMatchTime() {
		this.loadMatchTime(false);

		assertEquals(this.sourceMatchTime.getMinute(),
				this.matchTime.getMinute());
		assertEquals(this.sourceMatchTime.getPhase(), this.matchTime.getPhase());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testMatchTimeMinuteMalformed() {
		this.matchTimeObject.put(HandballTickerStream.MatchTime.KEY_MINUTE,
				INVALID_MINUTE);
		this.loadMatchTime(true);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testMatchTimePhaseMalformed() {
		this.matchTimeObject.put(HandballTickerStream.MatchTime.KEY_PHASE,
				INVALID_MATCH_PHASE);
		this.loadMatchTime(true);
	}

}