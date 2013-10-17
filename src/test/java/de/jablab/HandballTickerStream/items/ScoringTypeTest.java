package de.jablab.HandballTickerStream.items;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class ScoringTypeTest {

	private static final String INVALID_SCORING_TYPE = "noscoringtype";

	private ScoringType type;

	private void loadScoringType(final ScoringType type) {
		this.type = ScoringType.parseString(type.toString());
		assertNotNull(this.type);
		assertEquals(type, this.type);
	}

	@Test
	public void testScoringTypeInvalid() {
		this.type = ScoringType.parseString(INVALID_SCORING_TYPE);
		assertNull(this.type);
	}

	@Test
	public void testNormal() {
		this.loadScoringType(ScoringType.NORMAL);
	}

	@Test
	public void testRush() {
		this.loadScoringType(ScoringType.RUSH);
	}

	@Test
	public void testPenalty() {
		this.loadScoringType(ScoringType.PENALTY);
	}

}