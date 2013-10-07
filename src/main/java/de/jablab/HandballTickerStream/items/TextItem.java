package de.jablab.HandballTickerStream.items;

import java.util.Date;

import org.json.simple.JSONObject;

import de.jablab.HandballTickerStream.HandballTickerStream;
import de.jablab.HandballTickerStream.MatchTime;
import de.jablab.HandballTickerStream.StreamItem;
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
		object.put(HandballTickerStream.StreamItem.TextItem.KEY_MESSAGE,
				this.message);
		return object;
	}

	/**
	 * load any text stream item from JSON object
	 * 
	 * @param jsonString
	 *            text stream item JSON
	 * @throws TextItemFormatException
	 *             if the JSON is not a valid text stream item object
	 * @throws StreamItemFormatException
	 *             if the JSON is not a valid stream item object
	 */
	public static TextItem parseJSON(final String jsonString)
			throws StreamItemFormatException {
		JSONObject textStreamItem;
		try {
			textStreamItem = (JSONObject) JSON_PARSER.parse(jsonString);
		} catch (org.json.simple.parser.ParseException e) {
			throw new StreamItemFormatException("\"" + jsonString
					+ "\" is not a JSON String");
		}

		final StreamItemInformation streamItemInformation = StreamItem
				.parseStreamItemJSON(textStreamItem);
		final JSONObject object = streamItemInformation.getObject();

		if (streamItemInformation.getType() == StreamItemType.TEXT) {
			final String textItemMessage = parseTextItemMessage(object);

			return new TextItem(streamItemInformation.getPublished(),
					streamItemInformation.getTime(),
					streamItemInformation.getMessage(), textItemMessage);
		} else {
			throw new TextItemFormatException("field \""
					+ HandballTickerStream.StreamItem.KEY_TYPE
					+ "\" is invalid: must be \"" + StreamItemType.TEXT + "\"");
		}
	}

	/**
	 * parse the message field
	 * 
	 * @param object
	 *            text stream item object JSON object
	 * @return message displayed
	 * @throws TextItemFormatException
	 *             if field missing
	 */
	protected static String parseTextItemMessage(final JSONObject object)
			throws TextItemFormatException {
		final String message = (String) object
				.get(HandballTickerStream.StreamItem.TextItem.KEY_MESSAGE);
		if (message != null) {
			return message;
		} else {
			throw new TextItemFormatException("field \""
					+ HandballTickerStream.StreamItem.TextItem.KEY_MESSAGE
					+ "\" is missing");
		}
	}

}