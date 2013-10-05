package de.jablab.HandballTickerStream.items;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Calendar;
import java.util.Date;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.jablab.HandballTickerStream.MatchPhase;
import de.jablab.HandballTickerStream.MatchTime;
import de.jablab.HandballTickerStream.StreamItem;
import de.jablab.HandballTickerStream.exceptions.StreamItemFormatException;

public class PhaseEndItemTest {

	private static Date VALID_PUBLISHED;
	private static final MatchTime VALID_TIME = new MatchTime(35,
			MatchPhase.SECOND);
	private static final String VALID_MESSAGE = "Half time!";
	private static final MatchPhase VALID_BEFORE = MatchPhase.FIRST;
	private static final MatchPhase VALID_AFTER = MatchPhase.HALF_TIME;

	private static PhaseEndItem SOURCE_ITEM;
	private JSONObject sourceItemObject;

	private PhaseEndItem item;

	@BeforeClass
	public static void beforeClass() {
		final Calendar calendar = Calendar.getInstance();
		// month is zero-based -> October
		calendar.set(2013, 9, 2, 11, 52, 34);
		VALID_PUBLISHED = calendar.getTime();

		SOURCE_ITEM = new PhaseEndItem(VALID_PUBLISHED, VALID_TIME,
				VALID_MESSAGE, VALID_BEFORE, VALID_AFTER);
	}

	@Before
	public void setUp() {
		this.sourceItemObject = SOURCE_ITEM.toJSON();
	}

	private void loadItem(final boolean error) {
		try {
			System.out.println(this.sourceItemObject.toJSONString());
			this.item = (PhaseEndItem) StreamItem
					.parseJSON(this.sourceItemObject.toJSONString());
		} catch (final StreamItemFormatException e) {
			if (!error) {
				e.printStackTrace();
			}
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

		assertEquals(SOURCE_ITEM.getPublished(), this.item.getPublished());
		assertEquals(SOURCE_ITEM.getTime(), this.item.getTime());
		assertEquals(SOURCE_ITEM.getType(), this.item.getType());
		assertEquals(SOURCE_ITEM.getMessage(), this.item.getMessage());

		assertEquals(SOURCE_ITEM.getBefore(), this.item.getBefore());
		assertEquals(SOURCE_ITEM.getAfter(), this.item.getAfter());
		assertEquals(SOURCE_ITEM.getSubType(), this.item.getSubType());
	}

}