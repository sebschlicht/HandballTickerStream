package de.jablab.HandballTickerStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class StreamItemTypeTest {

	private static final String INVALID_TYPE = "notype";

	private StreamItemType type;

	private void loadType(final StreamItemType type) {
		this.type = StreamItemType.parseString(type.toString());
		assertNotNull(this.type);
		assertEquals(type, this.type);
	}

	@Test
	public void testTypeInvalid() {
		this.type = StreamItemType.parseString(INVALID_TYPE);
		assertNull(this.type);
	}

	@Test
	public void testPhaseEnd() {
		this.loadType(StreamItemType.PHASE_END);
	}

	@Test
	public void testText() {
		this.loadType(StreamItemType.TEXT);
	}

	@Test
	public void testScore() {
		this.loadType(StreamItemType.SCORE);
	}

	@Test
	public void testFoul() {
		this.loadType(StreamItemType.FOUL);
	}

}