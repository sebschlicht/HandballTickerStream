package de.jablab.HandballTickerStream.matchdata;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.json.simple.JSONObject;
import org.junit.Test;

import de.jablab.HandballTickerStream.exceptions.MatchTimeFormatException;

public class MatchTimeTest {

	private MatchTime sourceMatchTime;
	private JSONObject matchTimeObject;

	private MatchTime matchTime;

	private void createMatchTime(final int minute, final MatchPhase phase) {
		this.sourceMatchTime = new MatchTime(minute, phase);
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
		this.createMatchTime(36, MatchPhase.SECOND);
		this.loadMatchTime(false);
	}

}