package de.jablab.HandballTickerStream.items;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.BeforeClass;
import org.junit.Test;

import de.jablab.HandballTickerStream.HandballTickerStream;
import de.jablab.HandballTickerStream.MatchPhase;
import de.jablab.HandballTickerStream.StreamItem;
import de.jablab.HandballTickerStream.StreamItemTest;
import de.jablab.HandballTickerStream.exceptions.StreamItemFormatException;

public class PhaseEndItemTest extends StreamItemTest {

	private static final MatchPhase VALID_BEFORE = MatchPhase.FIRST;
	private static final MatchPhase VALID_AFTER = MatchPhase.HALF_TIME;

	private static PhaseEndItem SOURCE_PHASE_END_ITEM;

	private PhaseEndItem item;

	@BeforeClass
	public static void beforeClass() {
		StreamItemTest.beforeClass();

		SOURCE_PHASE_END_ITEM = new PhaseEndItem(VALID_PUBLISHED, VALID_TIME,
				VALID_MESSAGE, VALID_BEFORE, VALID_AFTER);
		SOURCE_ITEM = SOURCE_PHASE_END_ITEM;
	}

	@Override
	protected void loadItem(final boolean error) {
		try {
			this.item = (PhaseEndItem) StreamItem.parseJSON(this.sourceObject
					.toJSONString());
		} catch (final StreamItemFormatException e) {
			this.setErrTrace(e.getMessage());
		}

		if (error) {
			assertNull(this.item);
		} else {
			assertNotNull(this.item);
		}
	}

	@Test
	public void testValidItem() {
		this.loadItem(false);
		this.checkStreamItem(this.item);

		assertEquals(SOURCE_PHASE_END_ITEM.getBefore(), this.item.getBefore());
		assertEquals(SOURCE_PHASE_END_ITEM.getAfter(), this.item.getAfter());
		assertEquals(SOURCE_PHASE_END_ITEM.getSubType(), this.item.getSubType());
	}

	@Test
	public void testBeforeMissing() {
		this.putToItemObject(
				HandballTickerStream.StreamItem.PhaseEndItem.KEY_BEFORE, null);
		this.loadItem(true);
		this.checkForMissingField(HandballTickerStream.StreamItem.PhaseEndItem.KEY_BEFORE);
	}

	@Test
	public void testAfterMissing() {
		this.putToItemObject(
				HandballTickerStream.StreamItem.PhaseEndItem.KEY_AFTER, null);
		this.loadItem(true);
		this.checkForMissingField(HandballTickerStream.StreamItem.PhaseEndItem.KEY_AFTER);
	}

	@Test
	public void testSubTypeMissing() {
		this.putToItemObject(
				HandballTickerStream.StreamItem.PhaseEndItem.KEY_SUB_TYPE, null);
		this.loadItem(false);
	}

	@Test
	public void testItemObjectMissing() {
		if (SOURCE_PHASE_END_ITEM.getSubType() != null) {
			this.putToItemObject(
					HandballTickerStream.StreamItem.PhaseEndItem.KEY_OBJECT,
					null);
			this.loadItem(true);
			this.checkForMissingField(HandballTickerStream.StreamItem.PhaseEndItem.KEY_OBJECT);
		}
	}

}