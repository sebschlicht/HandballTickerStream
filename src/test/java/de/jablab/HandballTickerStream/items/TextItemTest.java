package de.jablab.HandballTickerStream.items;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.BeforeClass;
import org.junit.Test;

import de.jablab.HandballTickerStream.HandballTickerStream;
import de.jablab.HandballTickerStream.StreamItem;
import de.jablab.HandballTickerStream.StreamItemTest;
import de.jablab.HandballTickerStream.exceptions.StreamItemFormatException;

public class TextItemTest extends StreamItemTest {

	private static final String VALID_TEXT_ITEM_MESSAGE = "Hello folks, this match is amazing!";

	private static TextItem SOURCE_TEXT_ITEM;

	private TextItem item;

	@BeforeClass
	public static void beforeClass() {
		StreamItemTest.beforeClass();

		SOURCE_TEXT_ITEM = new TextItem(VALID_PUBLISHED, VALID_TIME,
				VALID_MESSAGE, VALID_TEXT_ITEM_MESSAGE);
		SOURCE_ITEM = SOURCE_TEXT_ITEM;
	}

	@Override
	protected void loadItem(final boolean error) {
		try {
			this.item = (TextItem) StreamItem.parseJSON(this.sourceObject
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

	@Test
	public void testValidItem() {
		this.loadItem(false);
		this.checkStreamItem(this.item);

		assertEquals(SOURCE_TEXT_ITEM.getTextItemMessage(),
				this.item.getTextItemMessage());
	}

	@Test
	public void testTextItemMessageMissing() {
		this.putToItemObject(
				HandballTickerStream.StreamItem.Text.KEY_MESSAGE, null);
		this.loadItem(true);
		this.checkForMissingField(HandballTickerStream.StreamItem.Text.KEY_MESSAGE);
	}

}