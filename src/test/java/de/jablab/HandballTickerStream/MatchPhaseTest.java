package de.jablab.HandballTickerStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class MatchPhaseTest {

	private static final String INVALID_MATCH_PHASE = "nophase";

	private MatchPhase phase;

	private void loadMatchPhase(final MatchPhase phase) {
		this.phase = MatchPhase.parseString(phase.toString());
		assertNotNull(this.phase);
		assertEquals(phase, this.phase);
	}

	@Test
	public void testPhaseInvalid() {
		this.phase = MatchPhase.parseString(INVALID_MATCH_PHASE);
		assertNull(this.phase);
	}

	@Test
	public void testWarmup() {
		this.loadMatchPhase(MatchPhase.WARMUP);
	}

	@Test
	public void testFirst() {
		this.loadMatchPhase(MatchPhase.FIRST);
	}

	@Test
	public void testPaused() {
		this.loadMatchPhase(MatchPhase.PAUSED);
	}

	@Test
	public void testHalfTime() {
		this.loadMatchPhase(MatchPhase.HALF_TIME);
	}

	@Test
	public void testSecond() {
		this.loadMatchPhase(MatchPhase.SECOND);
	}

	@Test
	public void testFinished() {
		this.loadMatchPhase(MatchPhase.FINISHED);
	}

}