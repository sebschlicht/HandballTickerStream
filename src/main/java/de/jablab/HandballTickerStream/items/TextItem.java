package de.jablab.HandballTickerStream.items;

import java.util.Date;

import org.json.simple.JSONObject;

import de.jablab.HandballTickerStream.HandballTickerStream;
import de.jablab.HandballTickerStream.MatchTime;
import de.jablab.HandballTickerStream.StreamItem;
import de.jablab.HandballTickerStream.StreamItemInformation;
import de.jablab.HandballTickerStream.StreamItemType;
import de.jablab.HandballTickerStream.exceptions.StreamItemFormatException;
import de.jablab.HandballTickerStream.exceptions.items.TextItemFormatException;

/**
 * simple update message stream item
 * 
 * @author sebschlicht
 * 
 */
public class TextItem extends StreamItem {

	/**
	 * message displayed
	 */
	private String message;

	/**
	 * create a new simple update message stream item
	 * 
	 * @param published
	 *            exact time when published
	 * @param time
	 *            match time when published<br>
	 *            considered to be the time of the happening
	 * @param message
	 *            message displayed instead of a generated value
	 * @param textItemMessage
	 *            message displayed
	 */
	public TextItem(final Date published, final MatchTime time,
			final String message, final String textItemMessage) {
		super(published, time, StreamItemType.TEXT, message);
		this.message = textItemMessage;
	}

	/**
	 * @return message displayed
	 */
	public String getTextItemMessage() {
		return this.message;
	}

	@Override
	@SuppressWarnings("unchecked")
	public JSONObject getObjectJSON() {
		final JSONObject object = new JSONObject();
		object.put(HandballTickerStream.StreamItem.Text.KEY_MESSAGE,
				this.message);
		return object;
	}

	public static TextItem parseJSON(final String jsonString)
			throws StreamItemFormatException {
		final JSONObject textItem = parseJSONObject(jsonString);
		if (textItem != null) {
			return loadFromJSON(textItem);
		} else {
			throw new StreamItemFormatException("\"" + jsonString
					+ "\" is not a JSON String");
		}
	}

	public static TextItem loadFromJSON(final JSONObject streamItem)
			throws StreamItemFormatException {
		final StreamItemInformation streamItemInfo = loadStreamItemInformation(streamItem);

		if (streamItemInfo.getType() == StreamItemType.TEXT) {
			final JSONObject textObject = streamItemInfo.getObject();

			final String textItemMessage = parseTextItemMessage(textObject);

			return new TextItem(streamItemInfo.getPublished(),
					streamItemInfo.getTime(), streamItemInfo.getMessage(),
					textItemMessage);
		} else {
			throw new TextItemFormatException("field \""
					+ HandballTickerStream.StreamItem.KEY_TYPE
					+ "\" is invalid: must be \"" + StreamItemType.TEXT + "\"");
		}
	}

	private static String parseTextItemMessage(final JSONObject textObject)
			throws TextItemFormatException {
		final String message = (String) textObject
				.get(HandballTickerStream.StreamItem.Text.KEY_MESSAGE);
		if (message != null) {
			return message;
		} else {
			throw new TextItemFormatException("field \""
					+ HandballTickerStream.StreamItem.Text.KEY_MESSAGE
					+ "\" is missing");
		}
	}

}