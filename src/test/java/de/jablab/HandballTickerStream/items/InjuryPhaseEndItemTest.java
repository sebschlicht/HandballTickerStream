package de.jablab.HandballTickerStream.items;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.BeforeClass;
import org.junit.Test;

import de.jablab.HandballTickerStream.HandballTickerStream;
import de.jablab.HandballTickerStream.MatchPhase;
import de.jablab.HandballTickerStream.Player;
import de.jablab.HandballTickerStream.TeamRole;
import de.jablab.HandballTickerStream.exceptions.StreamItemFormatException;

public class InjuryPhaseEndItemTest extends PhaseEndItemTest {

	private static final MatchPhase VALID_BEFORE = MatchPhase.FIRST;
	private static final MatchPhase VALID_AFTER = MatchPhase.PAUSED;
	private static final Player VALID_PLAYER = new Player(12, 1, "Mr. T",
			TeamRole.HOME);

	private static InjuryPhaseEndItem SOURCE_INJURY_ITEM;

	private InjuryPhaseEndItem item;

	@BeforeClass
	public static void beforeClass() {
		PhaseEndItemTest.beforeClass();

		SOURCE_INJURY_ITEM = new InjuryPhaseEndItem(VALID_PUBLISHED,
				VALID_TIME, VALID_MESSAGE, VALID_BEFORE, VALID_AFTER,
				VALID_PLAYER);
		SOURCE_PHASE_END_ITEM = SOURCE_INJURY_ITEM;
		SOURCE_ITEM = SOURCE_INJURY_ITEM;
	}

	@Override
	protected void loadItem(final boolean error) {
		try {
			this.item = InjuryPhaseEndItem.parseJSON(this.sourceObject
					.toJSONString());
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

		assertEquals(SOURCE_INJURY_ITEM.getBefore(), this.item.getBefore());
		assertEquals(SOURCE_INJURY_ITEM.getAfter(), this.item.getAfter());
		assertEquals(SOURCE_INJURY_ITEM.getSubType(), this.item.getSubType());
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
				PhaseEndSubType.TIMEOUT.toString());
		this.loadItem(true);
	}

	@Test
	public void testPlayerMissing() {
		this.putToPhaseEndObject(
				HandballTickerStream.StreamItem.PhaseEnd.Injury.KEY_PLAYER,
				null);
		this.loadItem(true);
		this.checkForMissingField(HandballTickerStream.StreamItem.PhaseEnd.Injury.KEY_PLAYER);
	}

}