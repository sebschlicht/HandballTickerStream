package de.jablab.HandballTickerStream.items;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class PhaseEndSubTypeTest {

	private static final String INVALID_SUB_TYPE = "nophaseendsubtype";

	private PhaseEndSubType subType;

	private void loadSubType(final PhaseEndSubType subType) {
		this.subType = PhaseEndSubType.parseString(subType.toString());
		assertNotNull(this.subType);
		assertEquals(subType, this.subType);
	}

	@Test
	public void testSubTypeInvalid() {
		this.subType = PhaseEndSubType.parseString(INVALID_SUB_TYPE);
		assertNull(this.subType);
	}

	@Test
	public void testTimeout() {
		this.loadSubType(PhaseEndSubType.TIMEOUT);
	}

	@Test
	public void testInjury() {
		this.loadSubType(PhaseEndSubType.INJURY);
	}

}