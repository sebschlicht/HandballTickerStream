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

	public void setBefore(final MatchPhase before) {
		this.before = before;
	}

	/**
	 * @return new phase that began
	 */
	public MatchPhase getAfter() {
		return this.after;
	}

	public void setAfter(final MatchPhase after) {
		this.after = after;
	}

	/**
	 * @return sub type of the phase end item
	 */
	public PhaseEndSubType getSubType() {
		return this.subType;
	}

	public void setSubType(final PhaseEndSubType subType) {
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
		// basic phase end item does not contain an object
		return null;
	}

	public static PhaseEndItem parseJSON(final String jsonString)
			throws StreamItemFormatException {
		final JSONObject phaseEndItem = parseJSONObject(jsonString);
		if (phaseEndItem != null) {
			return loadFromJSON(phaseEndItem);
		} else {
			throw new StreamItemFormatException("\"" + jsonString
					+ "\" is not a JSON String");
		}
	}

	public static PhaseEndItem loadFromJSON(final JSONObject streamItem)
			throws StreamItemFormatException {
		final PhaseEndItemInformation phaseEndItemInfo = loadPhaseEndItemInformation(streamItem);

		if (phaseEndItemInfo.getSubType() == null) {
			return new PhaseEndItem(phaseEndItemInfo.getPublished(),
					phaseEndItemInfo.getTime(), phaseEndItemInfo.getMessage(),
					phaseEndItemInfo.getBefore(), phaseEndItemInfo.getAfter());
		} else {
			switch (phaseEndItemInfo.getSubType()) {

				case TIMEOUT:
					return TimeoutPhaseEndItem.loadFromJSON(streamItem);

				case INJURY:
					return InjuryPhaseEndItem.loadFromJSON(streamItem);

				default:
					throw new IllegalArgumentException(
							"phase end stream item sub type \""
									+ phaseEndItemInfo.getSubType()
									+ "\" not implemented!");
			}
		}
	}

	protected static PhaseEndItemInformation loadPhaseEndItemInformation(
			final JSONObject streamItem) throws StreamItemFormatException {
		final StreamItemInformation streamInfo = loadStreamItemInformation(streamItem);

		if (streamInfo.getType() == StreamItemType.PHASE_END) {
			final JSONObject phaseEndObject = streamInfo.getObject();

			final MatchPhase before = parseBefore(phaseEndObject);
			final MatchPhase after = parseAfter(phaseEndObject);
			// TODO: check if combination allowed?
			final PhaseEndSubType subType = parseSubType(phaseEndObject);
			final JSONObject subObject = parseObject(phaseEndObject);

			// check if object is provided if needed
			if ((subType != null) && (subObject == null)) {
				throw new PhaseEndItemFormatException("field \""
						+ HandballTickerStream.StreamItem.PhaseEnd.KEY_OBJECT
						+ "\" is missing");
			}

			return new PhaseEndItemInformation(streamInfo, before, after,
					subType, subObject);
		} else {
			throw new PhaseEndItemFormatException("field \""
					+ HandballTickerStream.StreamItem.KEY_TYPE
					+ "\" is invalid: must be \"" + StreamItemType.PHASE_END
					+ "\"");
		}
	}

	private static MatchPhase parseBefore(final JSONObject phaseEndObject)
			throws PhaseEndItemFormatException {
		final String sBefore = (String) phaseEndObject
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

	private static MatchPhase parseAfter(final JSONObject phaseEndObject)
			throws PhaseEndItemFormatException {
		final String sAfter = (String) phaseEndObject
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

	private static PhaseEndSubType parseSubType(final JSONObject phaseEndObject)
			throws PhaseEndItemFormatException {
		final String sSubType = (String) phaseEndObject
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

	private static JSONObject parseObject(final JSONObject phaseEndObject)
			throws PhaseEndItemFormatException {
		final Object object = phaseEndObject
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