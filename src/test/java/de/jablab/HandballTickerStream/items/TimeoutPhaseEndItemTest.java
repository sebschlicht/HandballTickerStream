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
import de.jablab.HandballTickerStream.TeamRole;
import de.jablab.HandballTickerStream.exceptions.StreamItemFormatException;

public class TimeoutPhaseEndItemTest extends PhaseEndItemTest {

	private static final MatchPhase VALID_BEFORE = MatchPhase.FIRST;
	private static final MatchPhase VALID_AFTER = MatchPhase.PAUSED;
	private static final TeamRole VALID_TEAM_ROLE = TeamRole.GUEST;

	private static TimeoutPhaseEndItem SOURCE_TIMEOUT_ITEM;

	private TimeoutPhaseEndItem item;

	@BeforeClass
	public static void beforeClass() {
		StreamItemTest.beforeClass();

		SOURCE_TIMEOUT_ITEM = new TimeoutPhaseEndItem(VALID_PUBLISHED,
				VALID_TIME, VALID_MESSAGE, VALID_BEFORE, VALID_AFTER,
				VALID_TEAM_ROLE);
		SOURCE_ITEM = SOURCE_TIMEOUT_ITEM;
	}

	@Override
	protected void loadItem(final boolean error) {
		try {
			this.item = (TimeoutPhaseEndItem) StreamItem
					.parseJSON(this.sourceObject.toJSONString());
		} catch (final StreamItemFormatException e) {
			this.setErrTrace(e.getMessage());
			if (!error) {
				System.err.println(e.getMessage());
			}
		}

		if (error) {
			assertNull(this.item);
		} else {
			assertNotNull(this.item);
		}
	}

	@Override
	@Test
	public void testValidItem() {
		this.loadItem(false);
		this.checkStreamItem(this.item);

		assertEquals(SOURCE_TIMEOUT_ITEM.getBefore(), this.item.getBefore());
		assertEquals(SOURCE_TIMEOUT_ITEM.getAfter(), this.item.getAfter());
		assertEquals(SOURCE_TIMEOUT_ITEM.getSubType(), this.item.getSubType());
	}

	@Override
	@Test
	public void testBeforeMissing() {
		this.putToItemObject(
				HandballTickerStream.StreamItem.PhaseEnd.KEY_BEFORE, null);
		this.loadItem(true);
		this.checkForMissingField(HandballTickerStream.StreamItem.PhaseEnd.KEY_BEFORE);
	}

	@Override
	@Test
	public void testAfterMissing() {
		this.putToItemObject(
				HandballTickerStream.StreamItem.PhaseEnd.KEY_AFTER, null);
		this.loadItem(true);
		this.checkForMissingField(HandballTickerStream.StreamItem.PhaseEnd.KEY_AFTER);
	}

	@Override
	@Test
	public void testSubTypeMissing() {
		this.putToItemObject(
				HandballTickerStream.StreamItem.PhaseEnd.KEY_SUB_TYPE, null);
		this.loadItem(true);
	}

	@Test
	public void testSubTypeInvalid() {
		this.putToItemObject(
				HandballTickerStream.StreamItem.PhaseEnd.KEY_SUB_TYPE,
				PhaseEndSubType.INJURY.toString());
		this.loadItem(true);
	}

}