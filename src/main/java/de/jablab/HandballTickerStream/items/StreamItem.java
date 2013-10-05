package de.jablab.HandballTickerStream.items;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import de.jablab.HandballTickerStream.HandballTickerStream;
import de.jablab.HandballTickerStream.exceptions.MatchTimeFormatException;
import de.jablab.HandballTickerStream.exceptions.StreamItemFormatException;
import de.jablab.HandballTickerStream.matchdata.MatchTime;

/**
 * basic item for the handball ticker stream
 * 
 * @author sebschlicht
 * 
 */
public abstract class StreamItem {

	/**
	 * date formatter
	 */
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss", Locale.GERMAN);

	/**
	 * JSON parser
	 */
	protected static final JSONParser JSON_PARSER = new JSONParser();

	/**
	 * exact time when published
	 */
	protected Date published;

	/**
	 * match time when published<br>
	 * considered to be the time of the happening
	 */
	protected MatchTime time;

	/**
	 * type of the item
	 */
	protected StreamItemType type;

	/**
	 * message displayed instead of a generated value
	 */
	protected String message;

	/**
	 * create a new basic item for the handball ticker stream
	 * 
	 * @param published
	 *            exact time when published
	 * @param time
	 *            match time when published<br>
	 *            considered to be the time of the happening
	 * @param type
	 *            type of the item
	 * @param message
	 *            message displayed instead of a generated value
	 */
	public StreamItem(final Date published, final MatchTime time,
			final StreamItemType type, final String message) {
		this.published = published;
		this.time = time;
		this.type = type;
		this.message = message;
	}

	/**
	 * parse the published field
	 * 
	 * @param streamItem
	 *            stream item JSON object
	 * @return exact time when published
	 * @throws StreamItemFormatException
	 *             if field missing or malformed
	 */
	protected static Date parsePublished(final JSONObject streamItem)
			throws StreamItemFormatException {
		final String sPublished = (String) streamItem
				.get(HandballTickerStream.StreamItem.KEY_PUBLISHED);
		if (sPublished != null) {
			try {
				return DATE_FORMATTER.parse(sPublished);
			} catch (final ParseException e) {
				throw new StreamItemFormatException("field \""
						+ HandballTickerStream.StreamItem.KEY_PUBLISHED
						+ "\" is malformed: \"" + sPublished
						+ "\" is not a valid time");
			}
		} else {
			throw new StreamItemFormatException("field \""
					+ HandballTickerStream.StreamItem.KEY_PUBLISHED
					+ "\" is missing");
		}
	}

	/**
	 * parse the time field
	 * 
	 * @param streamItem
	 *            stream item JSON object
	 * @return match time when published
	 * @throws StreamItemFormatException
	 *             if field missing, malformed or not a match time object
	 */
	protected static MatchTime parseTime(final JSONObject streamItem)
			throws StreamItemFormatException {
		final String sTime = (String) streamItem
				.get(HandballTickerStream.StreamItem.KEY_TIME);
		if (sTime != null) {
			try {
				final JSONObject time = (JSONObject) JSON_PARSER.parse(sTime);
				return new MatchTime(time);
			} catch (final org.json.simple.parser.ParseException e) {
				throw new StreamItemFormatException("field \""
						+ HandballTickerStream.StreamItem.KEY_TIME
						+ "\" is malformed: \"" + sTime
						+ "\" is not a JSON object");
			} catch (final MatchTimeFormatException e) {
				throw new StreamItemFormatException("field \""
						+ HandballTickerStream.StreamItem.KEY_TIME
						+ "\" is not a match time object", e);
			}
		} else {
			throw new StreamItemFormatException("field \""
					+ HandballTickerStream.StreamItem.KEY_TIME
					+ "\" is missing");
		}
	}

	/**
	 * parse the type field
	 * 
	 * @param streamItem
	 *            stream item JSON object
	 * @return type of the item
	 * @throws StreamItemFormatException
	 *             if field missing or malformed
	 */
	protected static StreamItemType parseStreamItemType(
			final JSONObject streamItem) throws StreamItemFormatException {
		final String sType = (String) streamItem
				.get(HandballTickerStream.StreamItem.KEY_TYPE);
		if (sType != null) {
			final StreamItemType type = StreamItemType.parseString(sType);
			if (type != null) {
				return type;
			} else {
				throw new StreamItemFormatException("field \""
						+ HandballTickerStream.StreamItem.KEY_TYPE
						+ "\" is malformed: \"" + sType
						+ "\" is not a stream item type");
			}
		} else {
			throw new StreamItemFormatException("field \""
					+ HandballTickerStream.StreamItem.KEY_TYPE
					+ "\" is missing");
		}
	}

	/**
	 * parse the message field
	 * 
	 * @param streamItem
	 *            stream item JSON object
	 * @return message displayed instead of a generated value
	 */
	protected static String parseMessage(final JSONObject streamItem) {
		return (String) streamItem
				.get(HandballTickerStream.StreamItem.KEY_MESSAGE);
	}

}