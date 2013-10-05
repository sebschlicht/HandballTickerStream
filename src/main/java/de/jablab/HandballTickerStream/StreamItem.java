package de.jablab.HandballTickerStream;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import de.jablab.HandballTickerStream.exceptions.MatchTimeFormatException;
import de.jablab.HandballTickerStream.exceptions.StreamItemFormatException;
import de.jablab.HandballTickerStream.items.PhaseEndItem;

/**
 * basic item for the handball ticker stream
 * 
 * @author sebschlicht
 * 
 */
public class StreamItem implements Streamable {

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
	private Date published;

	/**
	 * match time when published<br>
	 * considered to be the time of the happening
	 */
	private MatchTime time;

	/**
	 * type of the item
	 */
	private StreamItemType type;

	/**
	 * message displayed instead of a generated value
	 */
	private String message;

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
	 * @return exact time when published
	 */
	public Date getPublished() {
		return this.published;
	}

	public void setPublished(Date published) {
		this.published = published;
	}

	/**
	 * @return match time when published<br>
	 *         considered to be the time of the happening
	 */
	public MatchTime getTime() {
		return this.time;
	}

	public void setTime(MatchTime time) {
		this.time = time;
	}

	/**
	 * @return type of the item
	 */
	public StreamItemType getType() {
		return this.type;
	}

	public void setType(StreamItemType type) {
		this.type = type;
	}

	/**
	 * @return message displayed instead of a generated value
	 */
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		final JSONObject object = new JSONObject();
		object.put(HandballTickerStream.StreamItem.KEY_PUBLISHED,
				DATE_FORMATTER.format(this.published));
		object.put(HandballTickerStream.StreamItem.KEY_TIME, this.time.toJSON());
		object.put(HandballTickerStream.StreamItem.KEY_TYPE,
				this.type.toString());
		return object;
	}

	@Override
	public String toJSONString() {
		return this.toJSON().toJSONString();
	}

	/**
	 * load any stream item from JSON
	 * 
	 * @param jsonString
	 *            stream item JSON
	 * @throws StreamItemFormatException
	 *             if the JSON is not a valid stream item object
	 */
	public static StreamItem parseJSON(final String jsonString)
			throws StreamItemFormatException {
		JSONObject streamItem;
		try {
			streamItem = (JSONObject) JSON_PARSER.parse(jsonString);
		} catch (org.json.simple.parser.ParseException e) {
			throw new StreamItemFormatException("\"" + jsonString
					+ "\" is not a JSON String");
		}

		final Date published = parsePublished(streamItem);
		final MatchTime time = parseTime(streamItem);
		final StreamItemType type = parseStreamItemType(streamItem);
		final String message = parseMessage(streamItem);

		final StreamItem basicStreamItem = new StreamItem(published, time,
				type, message);

		switch (type) {
			case PHASE_END:
				return PhaseEndItem.parseJSON(basicStreamItem, streamItem);

			default:
				return null;
		}
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
		final Object time = streamItem
				.get(HandballTickerStream.StreamItem.KEY_TIME);
		if (time != null) {
			if (time instanceof JSONObject) {
				try {
					return MatchTime.parseJSON((JSONObject) time);
				} catch (final MatchTimeFormatException e) {
					throw new StreamItemFormatException("field \""
							+ HandballTickerStream.StreamItem.KEY_TIME
							+ "\" is not a match time object", e);
				}
			} else {
				throw new StreamItemFormatException("field \""
						+ HandballTickerStream.StreamItem.KEY_TIME
						+ "\" is malformed: \"" + time
						+ "\" is not a JSON object");
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
	 * @return message displayed instead of a generated value<br>
	 *         <b>null</b> if field missing
	 */
	protected static String parseMessage(final JSONObject streamItem) {
		return (String) streamItem
				.get(HandballTickerStream.StreamItem.KEY_MESSAGE);
	}

}