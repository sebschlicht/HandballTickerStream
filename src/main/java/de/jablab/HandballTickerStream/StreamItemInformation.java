package de.jablab.HandballTickerStream;

import java.util.Date;

import org.json.simple.JSONObject;


/**
 * wrapper for data to create any stream item
 * 
 * @author sebschlicht
 * 
 */
public class StreamItemInformation {

	/**
	 * exact time when published
	 */
	private final Date published;

	/**
	 * match time when published<br>
	 * considered to be the time of the happening
	 */
	private final MatchTime time;

	/**
	 * type of the item
	 */
	private final StreamItemType type;

	/**
	 * stream item type specific data
	 */
	private final JSONObject object;

	/**
	 * message displayed instead of a generated value
	 */
	private final String message;

	/**
	 * create a new wrapper for data to create any stream item
	 * 
	 * @param published
	 *            exact time when published
	 * @param time
	 *            match time when published<br>
	 *            considered to be the time of the happening
	 * @param type
	 *            type of the item
	 * @param object
	 *            stream item type specific data
	 * @param message
	 *            message displayed instead of a generated value
	 */
	public StreamItemInformation(final Date published, final MatchTime time,
			final StreamItemType type, final JSONObject object,
			final String message) {
		this.published = published;
		this.time = time;
		this.type = type;
		this.object = object;
		this.message = message;
	}

	/**
	 * @return exact time when published
	 */
	public Date getPublished() {
		return this.published;
	}

	/**
	 * @return match time when published<br>
	 *         considered to be the time of the happening
	 */
	public MatchTime getTime() {
		return this.time;
	}

	/**
	 * @return type of the item
	 */
	public StreamItemType getType() {
		return this.type;
	}

	/**
	 * @return stream item type specific data
	 */
	public JSONObject getObject() {
		return this.object;
	}

	/**
	 * @return message displayed instead of a generated value
	 */
	public String getMessage() {
		return this.message;
	}

}