package de.jablab.HandballTickerStream.items;

import java.util.Date;

import org.json.simple.JSONObject;

import de.jablab.HandballTickerStream.HandballTickerStream;
import de.jablab.HandballTickerStream.MatchPhase;
import de.jablab.HandballTickerStream.MatchTime;
import de.jablab.HandballTickerStream.StreamItem;
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
	 * new phase that begun
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
	 *            new phase that begun
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
	 * @return new phase that begun
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
		object.put(HandballTickerStream.StreamItem.PhaseEndItem.KEY_BEFORE,
				this.before.toString());
		object.put(HandballTickerStream.StreamItem.PhaseEndItem.KEY_AFTER,
				this.after.toString());
		if (this.subType != null) {
			object.put(
					HandballTickerStream.StreamItem.PhaseEndItem.KEY_SUB_TYPE,
					this.subType.toString());
		}
		return object;
	}

	/**
	 * load any phase end stream item from JSON object
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
		} catch (org.json.simple.parser.ParseException e) {
			throw new StreamItemFormatException("\"" + jsonString
					+ "\" is not a JSON String");
		}

		final StreamItemInformation streamItemInformation = StreamItem
				.parseStreamItemJSON(phaseEndStreamItem);
		final JSONObject object = streamItemInformation.getObject();

		if (streamItemInformation.getType() == StreamItemType.PHASE_END) {
			final MatchPhase before = parseBefore(object);
			final MatchPhase after = parseAfter(object);

			// TODO: check if combination allowed?

			final PhaseEndItem basicPhaseEndStreamItem = new PhaseEndItem(
					streamItemInformation.getPublished(),
					streamItemInformation.getTime(),
					streamItemInformation.getMessage(), before, after);

			final PhaseEndSubType subType = parseSubType(object);

			if (subType == null) {
				return basicPhaseEndStreamItem;
			} else {
				// TODO: call sub type parsing methods
				return null;
			}
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
	protected static MatchPhase parseBefore(final JSONObject object)
			throws PhaseEndItemFormatException {
		final String sBefore = (String) object
				.get(HandballTickerStream.StreamItem.PhaseEndItem.KEY_BEFORE);
		if (sBefore != null) {
			final MatchPhase before = MatchPhase.parseString(sBefore);
			if (before != null) {
				return before;
			} else {
				throw new PhaseEndItemFormatException(
						"field \""
								+ HandballTickerStream.StreamItem.PhaseEndItem.KEY_BEFORE
								+ "\" is malformed: \"" + sBefore
								+ "\" is not a match phase");
			}
		} else {
			throw new PhaseEndItemFormatException("field \""
					+ HandballTickerStream.StreamItem.PhaseEndItem.KEY_BEFORE
					+ "\" is missing");
		}
	}

	/**
	 * parse the after field
	 * 
	 * @param object
	 *            phase end stream item object JSON object
	 * @return new phase that begun
	 * @throws PhaseEndItemFormatException
	 *             if field missing or malformed
	 */
	protected static MatchPhase parseAfter(final JSONObject object)
			throws PhaseEndItemFormatException {
		final String sAfter = (String) object
				.get(HandballTickerStream.StreamItem.PhaseEndItem.KEY_AFTER);
		if (sAfter != null) {
			final MatchPhase after = MatchPhase.parseString(sAfter);
			if (after != null) {
				return after;
			} else {
				throw new PhaseEndItemFormatException(
						"field \""
								+ HandballTickerStream.StreamItem.PhaseEndItem.KEY_AFTER
								+ "\" is malformed: \"" + sAfter
								+ "\" is not a match phase");
			}
		} else {
			throw new PhaseEndItemFormatException("field \""
					+ HandballTickerStream.StreamItem.PhaseEndItem.KEY_AFTER
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
	protected static PhaseEndSubType parseSubType(final JSONObject object)
			throws PhaseEndItemFormatException {
		final String sSubType = (String) object
				.get(HandballTickerStream.StreamItem.PhaseEndItem.KEY_SUB_TYPE);
		if (sSubType != null) {
			final PhaseEndSubType subType = PhaseEndSubType
					.parseString(sSubType);
			if (subType != null) {
				return subType;
			} else {
				throw new PhaseEndItemFormatException(
						"field \""
								+ HandballTickerStream.StreamItem.PhaseEndItem.KEY_SUB_TYPE
								+ "\" is malformed: \"" + sSubType
								+ "\" is not a phase end sub type");
			}
		}

		return null;
	}

}