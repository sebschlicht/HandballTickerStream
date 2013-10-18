package de.jablab.HandballTickerStream.items;

import java.util.Date;

import org.json.simple.JSONObject;

import de.jablab.HandballTickerStream.HandballTickerStream;
import de.jablab.HandballTickerStream.MatchPhase;
import de.jablab.HandballTickerStream.MatchTime;
import de.jablab.HandballTickerStream.StreamItem;
import de.jablab.HandballTickerStream.StreamItemInformation;
import de.jablab.HandballTickerStream.StreamItemType;
import de.jablab.HandballTickerStream.exceptions.StreamItemFormatException;
import de.jablab.HandballTickerStream.exceptions.items.PhaseEndItemFormatException;

/**
 * match phase end stream item
 * 
 * @author sebschlicht
 * 
 */
public class PhaseEndItem extends StreamItem {

	/**
	 * phase that ended
	 */
	private MatchPhase before;

	/**
	 * new phase that began
	 */
	private MatchPhase after;

	/**
	 * sub type of the phase end item
	 */
	private PhaseEndSubType subType;

	/**
	 * create a new match phase end stream item
	 * 
	 * @param published
	 *            exact time when published
	 * @param time
	 *            match time when published<br>
	 *            considered to be the time of the happening
	 * @param message
	 *            message displayed instead of a generated value
	 * @param before
	 *            phase that ended
	 * @param after
	 *            new phase that began
	 */
	public PhaseEndItem(final Date published, final MatchTime time,
			final String message, final MatchPhase before,
			final MatchPhase after) {
		super(published, time, StreamItemType.PHASE_END, message);
		this.before = before;
		this.after = after;
	}

	/**
	 * @return phase that ended
	 */
	public MatchPhase getBefore() {
		return this.before;
	}

	public void setBefore(MatchPhase before) {
		this.before = before;
	}

	/**
	 * @return new phase that began
	 */
	public MatchPhase getAfter() {
		return this.after;
	}

	public void setAfter(MatchPhase after) {
		this.after = after;
	}

	/**
	 * @return sub type of the phase end item
	 */
	public PhaseEndSubType getSubType() {
		return this.subType;
	}

	public void setSubType(PhaseEndSubType subType) {
		this.subType = subType;
	}

	@Override
	@SuppressWarnings("unchecked")
	public JSONObject getObjectJSON() {
		final JSONObject object = new JSONObject();
		object.put(HandballTickerStream.StreamItem.PhaseEnd.KEY_BEFORE,
				this.before.toString());
		object.put(HandballTickerStream.StreamItem.PhaseEnd.KEY_AFTER,
				this.after.toString());
		if (this.subType != null) {
			object.put(HandballTickerStream.StreamItem.PhaseEnd.KEY_SUB_TYPE,
					this.subType.toString());
			object.put(HandballTickerStream.StreamItem.PhaseEnd.KEY_OBJECT,
					this.getPhaseEndObjectJSON());
		}
		return object;
	}

	protected JSONObject getPhaseEndObjectJSON() {
		return new JSONObject();
	}

	/**
	 * load any phase end stream item from JSON
	 * 
	 * @param jsonString
	 *            phase end stream item JSON
	 * @throws PhaseEndItemFormatException
	 *             if the JSON is not a valid phase end stream item object
	 * @throws StreamItemFormatException
	 *             if the JSON is not a valid stream item object
	 */
	public static PhaseEndItem parseJSON(final String jsonString)
			throws StreamItemFormatException {
		JSONObject phaseEndStreamItem;
		try {
			phaseEndStreamItem = (JSONObject) JSON_PARSER.parse(jsonString);
		} catch (final org.json.simple.parser.ParseException e) {
			throw new StreamItemFormatException("\"" + jsonString
					+ "\" is not a JSON String");
		}

		final StreamItemInformation streamItemInformation = StreamItem
				.parseStreamItemJSON(phaseEndStreamItem);
		final JSONObject object = streamItemInformation.getObject();

		if (streamItemInformation.getType() == StreamItemType.PHASE_END) {
			final PhaseEndSubType subType = parseSubType(object);

			if (subType == null) {
				final MatchPhase before = parseBefore(object);
				final MatchPhase after = parseAfter(object);
				// TODO: check if combination allowed?

				return new PhaseEndItem(streamItemInformation.getPublished(),
						streamItemInformation.getTime(),
						streamItemInformation.getMessage(), before, after);
			} else {
				switch (subType) {
				// TODO: call sub type specific parsing methods

					case TIMEOUT:
						return TimeoutPhaseEndItem.parseJSON(jsonString);

					default:
						throw new IllegalArgumentException(
								"phase end stream item sub type \"" + subType
										+ "\" not implemented!");
				}
			}
		} else {
			throw new PhaseEndItemFormatException("field \""
					+ HandballTickerStream.StreamItem.KEY_TYPE
					+ "\" is invalid: must be \"" + StreamItemType.PHASE_END
					+ "\"");
		}
	}

	/**
	 * load the basic phase end stream item from JSON object
	 * 
	 * @param phaseEndStreamItem
	 *            phase end stream item JSON object
	 * @throws PhaseEndItemFormatException
	 *             if the JSON object is not a valid phase end stream item
	 *             object
	 * @throws StreamItemFormatException
	 *             if the JSON object is not a valid stream item object
	 */
	protected static PhaseEndItemInformation parsePhaseEndItemJSON(
			final JSONObject phaseEndStreamItem)
			throws StreamItemFormatException {
		final StreamItemInformation streamItemInformation = StreamItem
				.parseStreamItemJSON(phaseEndStreamItem);
		if (streamItemInformation.getType() == StreamItemType.PHASE_END) {
			final JSONObject streamItemObject = streamItemInformation
					.getObject();

			final MatchPhase before = parseBefore(streamItemObject);
			final MatchPhase after = parseAfter(streamItemObject);
			// TODO: check if combination allowed?

			final PhaseEndSubType subType = parseSubType(streamItemObject);
			final JSONObject object = parseObject(streamItemObject);
			return new PhaseEndItemInformation(
					streamItemInformation.getPublished(),
					streamItemInformation.getTime(),
					streamItemInformation.getMessage(), before, after, subType,
					object);
		} else {
			throw new PhaseEndItemFormatException("field \""
					+ HandballTickerStream.StreamItem.KEY_TYPE
					+ "\" is invalid: must be \"" + StreamItemType.PHASE_END
					+ "\"");
		}
	}

	/**
	 * parse the before field
	 * 
	 * @param object
	 *            phase end stream item object JSON object
	 * @return phase that ended
	 * @throws PhaseEndItemFormatException
	 *             if field missing or malformed
	 */
	private static MatchPhase parseBefore(final JSONObject object)
			throws PhaseEndItemFormatException {
		final String sBefore = (String) object
				.get(HandballTickerStream.StreamItem.PhaseEnd.KEY_BEFORE);
		if (sBefore != null) {
			final MatchPhase before = MatchPhase.parseString(sBefore);
			if (before != null) {
				return before;
			} else {
				throw new PhaseEndItemFormatException("field \""
						+ HandballTickerStream.StreamItem.PhaseEnd.KEY_BEFORE
						+ "\" is malformed: \"" + sBefore
						+ "\" is not a match phase");
			}
		} else {
			throw new PhaseEndItemFormatException("field \""
					+ HandballTickerStream.StreamItem.PhaseEnd.KEY_BEFORE
					+ "\" is missing");
		}
	}

	/**
	 * parse the after field
	 * 
	 * @param object
	 *            phase end stream item object JSON object
	 * @return new phase that began
	 * @throws PhaseEndItemFormatException
	 *             if field missing or malformed
	 */
	private static MatchPhase parseAfter(final JSONObject object)
			throws PhaseEndItemFormatException {
		final String sAfter = (String) object
				.get(HandballTickerStream.StreamItem.PhaseEnd.KEY_AFTER);
		if (sAfter != null) {
			final MatchPhase after = MatchPhase.parseString(sAfter);
			if (after != null) {
				return after;
			} else {
				throw new PhaseEndItemFormatException("field \""
						+ HandballTickerStream.StreamItem.PhaseEnd.KEY_AFTER
						+ "\" is malformed: \"" + sAfter
						+ "\" is not a match phase");
			}
		} else {
			throw new PhaseEndItemFormatException("field \""
					+ HandballTickerStream.StreamItem.PhaseEnd.KEY_AFTER
					+ "\" is missing");
		}
	}

	/**
	 * parse the sub type field
	 * 
	 * @param object
	 *            phase end stream item object JSON object
	 * @return sub type of the phase end item<br>
	 *         <b>null</b> if field missing
	 * @throws PhaseEndItemFormatException
	 *             if field malformed
	 */
	private static PhaseEndSubType parseSubType(final JSONObject object)
			throws PhaseEndItemFormatException {
		final String sSubType = (String) object
				.get(HandballTickerStream.StreamItem.PhaseEnd.KEY_SUB_TYPE);
		if (sSubType != null) {
			final PhaseEndSubType subType = PhaseEndSubType
					.parseString(sSubType);
			if (subType != null) {
				return subType;
			} else {
				throw new PhaseEndItemFormatException("field \""
						+ HandballTickerStream.StreamItem.PhaseEnd.KEY_SUB_TYPE
						+ "\" is malformed: \"" + sSubType
						+ "\" is not a phase end sub type");
			}
		}

		return null;
	}

	/**
	 * parse the object field
	 * 
	 * @param object
	 *            phase end stream item object JSON object
	 * @return <SubType>PhaseEndItem JSON object
	 * @throws PhaseEndItemFormatException
	 *             if field missing or malformed
	 */
	private static JSONObject parseObject(final JSONObject phaseEndItem)
			throws PhaseEndItemFormatException {
		final Object object = phaseEndItem
				.get(HandballTickerStream.StreamItem.PhaseEnd.KEY_OBJECT);
		if (object != null) {
			if (object instanceof JSONObject) {
				return (JSONObject) object;
			} else {
				throw new PhaseEndItemFormatException("field \""
						+ HandballTickerStream.StreamItem.PhaseEnd.KEY_OBJECT
						+ "\" is malformed: \"" + object
						+ "\" is not a JSON object");
			}
		} else {
			throw new PhaseEndItemFormatException("field \""
					+ HandballTickerStream.StreamItem.PhaseEnd.KEY_OBJECT
					+ "\" is missing");
		}
	}

}