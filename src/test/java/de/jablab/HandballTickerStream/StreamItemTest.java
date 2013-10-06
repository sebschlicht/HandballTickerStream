package de.jablab.HandballTickerStream;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

public abstract class StreamItemTest extends StreamableTest {

	protected static Date VALID_PUBLISHED;
	protected static final MatchTime VALID_TIME = new MatchTime(35,
			MatchPhase.SECOND);
	protected static final String VALID_MESSAGE = "Half time!";

	private static final String MALFORMED_PUBLISHED = "nodatetime";
	private static final String MALFORMED_TIME = "nomatchtime";
	private static final String MALFORMED_TYPE = "notype";

	protected static StreamItem SOURCE_ITEM;

	@BeforeClass
	public static void beforeClass() {
		final Calendar calendar = Calendar.getInstance();

		// do not use milliseconds
		calendar.clear(Calendar.MILLISECOND);

		// month is zero-based -> October
		calendar.set(2013, 9, 2, 11, 52, 34);
		VALID_PUBLISHED = calendar.getTime();
	}

	protected abstract void loadItem(final boolean error);

	protected void checkStreamItem(final StreamItem item) {
		assertEquals(SOURCE_ITEM.getPublished(), item.getPublished());
		assertEquals(SOURCE_ITEM.getTime(), item.getTime());
		assertEquals(SOURCE_ITEM.getType(), item.getType());
		assertEquals(SOURCE_ITEM.getMessage(), item.getMessage());
	}

	@Test
	public void testPublishedMissing() {
		this.putToObject(HandballTickerStream.StreamItem.KEY_PUBLISHED, null);
		this.loadItem(true);
		this.checkForMissingField(HandballTickerStream.StreamItem.KEY_PUBLISHED);
	}

	@Test
	public void testPublishedMalformed() {
		this.putToObject(HandballTickerStream.StreamItem.KEY_PUBLISHED,
				MALFORMED_PUBLISHED);
		this.loadItem(true);
	}

	@Test
	public void testTimeMissing() {
		this.putToObject(HandballTickerStream.StreamItem.KEY_TIME, null);
		this.loadItem(true);
		this.checkForMissingField(HandballTickerStream.StreamItem.KEY_TIME);
	}

	@Test
	public void testTimeMalformed() {
		this.putToObject(HandballTickerStream.StreamItem.KEY_TIME,
				MALFORMED_TIME);
		this.loadItem(true);
	}

	@Test
	public void testTypeMissing() {
		this.putToObject(HandballTickerStream.StreamItem.KEY_TYPE, null);
		this.loadItem(true);
		this.checkForMissingField(HandballTickerStream.StreamItem.KEY_TYPE);
	}

	@Test
	public void testTypeMalformed() {
		this.putToObject(HandballTickerStream.StreamItem.KEY_TYPE,
				MALFORMED_TYPE);
		this.loadItem(true);
	}

	@Test
	public void testObjectMissing() {
		this.putToObject(HandballTickerStream.StreamItem.KEY_OBJECT, null);
		this.loadItem(true);
		this.checkForMissingField(HandballTickerStream.StreamItem.KEY_OBJECT);
	}

	@Test
	public void testMessageMissing() {
		this.putToObject(HandballTickerStream.StreamItem.KEY_MESSAGE, null);
		this.loadItem(false);
	}

}