package de.jablab.HandballTickerStream.items;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.junit.BeforeClass;
import org.junit.Test;

import de.jablab.HandballTickerStream.HandballTickerStream;
import de.jablab.HandballTickerStream.Player;
import de.jablab.HandballTickerStream.StreamItem;
import de.jablab.HandballTickerStream.StreamItemTest;
import de.jablab.HandballTickerStream.TeamRole;
import de.jablab.HandballTickerStream.exceptions.StreamItemFormatException;

public class FoulItemTest extends StreamItemTest {

	private static final Player VALID_PLAYER = new Player(12, 1, "Mr. T",
			TeamRole.HOME);
	private static final List<Discipline> VALID_DISCIPLINES = new LinkedList<Discipline>();
	private static final Player VALID_VICTIM = new Player(154, 13, "Mr. P",
			TeamRole.GUEST);

	private static final JSONArray EMPTY_DISCIPLINES = new JSONArray();

	private static FoulItem SOURCE_FOUL_ITEM;

	private FoulItem item;

	@BeforeClass
	public static void beforeClass() {
		StreamItemTest.beforeClass();

		VALID_DISCIPLINES.add(Discipline.TIME);
		VALID_DISCIPLINES.add(Discipline.YELLOW);
		VALID_DISCIPLINES.add(Discipline.RED);

		SOURCE_FOUL_ITEM = new FoulItem(VALID_PUBLISHED, VALID_TIME,
				VALID_MESSAGE, VALID_PLAYER, VALID_DISCIPLINES, VALID_VICTIM);
		SOURCE_ITEM = SOURCE_FOUL_ITEM;
	}

	@Override
	protected void loadItem(final boolean error) {
		try {
			this.item = (FoulItem) StreamItem.parseJSON(this.sourceObject
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

		assertEquals(SOURCE_FOUL_ITEM.getPlayer(), this.item.getPlayer());
		assertEquals(SOURCE_FOUL_ITEM.getDisciplines().size(), this.item
				.getDisciplines().size());
		for (int i = 0; i < SOURCE_FOUL_ITEM.getDisciplines().size(); i++) {
			assertEquals(SOURCE_FOUL_ITEM.getDisciplines().get(i), this.item
					.getDisciplines().get(i));
		}
		assertEquals(SOURCE_FOUL_ITEM.getVictim(), this.item.getVictim());
	}

	@Test
	public void testPlayerMissing() {
		this.putToItemObject(
				HandballTickerStream.StreamItem.FoulItem.KEY_PLAYER, null);
		this.loadItem(true);
		this.checkForMissingField(HandballTickerStream.StreamItem.FoulItem.KEY_PLAYER);
	}

	@Test
	public void testPlayerMalformed() {
		this.putToItemObject(
				HandballTickerStream.StreamItem.FoulItem.KEY_PLAYER,
				MALFORMED_JSON_OBJECT);
		this.loadItem(true);
		this.checkForMalformedField(HandballTickerStream.StreamItem.FoulItem.KEY_PLAYER);
	}

	@Test
	public void testDisciplinesMissing() {
		this.putToItemObject(
				HandballTickerStream.StreamItem.FoulItem.KEY_DISCIPLINES, null);
		this.loadItem(false);
		assertNull(this.item.getDisciplines());
	}

	@Test
	public void testDisciplinesEmpty() {
		this.putToItemObject(
				HandballTickerStream.StreamItem.FoulItem.KEY_DISCIPLINES,
				EMPTY_DISCIPLINES);
		this.loadItem(false);
		assertNull(this.item.getDisciplines());
	}

	@Test
	public void testDisciplinesMalformed() {
		this.putToItemObject(
				HandballTickerStream.StreamItem.FoulItem.KEY_DISCIPLINES,
				MALFORMED_JSON_ARRAY);
		this.loadItem(true);
		this.checkForMalformedField(HandballTickerStream.StreamItem.FoulItem.KEY_DISCIPLINES);
	}

	@Test
	public void testVictimMissing() {
		this.putToItemObject(
				HandballTickerStream.StreamItem.FoulItem.KEY_VICTIM, null);
		this.loadItem(false);
	}

	@Test
	public void testVictimMalformed() {
		this.putToItemObject(
				HandballTickerStream.StreamItem.FoulItem.KEY_VICTIM,
				MALFORMED_JSON_OBJECT);
		this.loadItem(true);
		this.checkForMalformedField(HandballTickerStream.StreamItem.FoulItem.KEY_VICTIM);
	}

}