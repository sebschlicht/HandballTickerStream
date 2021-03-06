package de.jablab.HandballTickerStream;

import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import de.jablab.HandballTickerStream.exceptions.MatchTimeFormatException;
import de.jablab.HandballTickerStream.exceptions.ScoreFormatException;
import de.jablab.HandballTickerStream.exceptions.StreamFormatException;
import de.jablab.HandballTickerStream.exceptions.StreamItemFormatException;
import de.jablab.HandballTickerStream.exceptions.TeamFormatException;

/**
 * handball ticker stream
 * 
 * @author sebschlicht
 * 
 */
public class Stream extends Streamable {

	/**
	 * current match time
	 */
	private MatchTime time;

	/**
	 * home team
	 */
	private Team home;

	/**
	 * guest team
	 */
	private Team guest;

	/**
	 * score of the teams in the first half
	 */
	private Score first;

	/**
	 * score of the teams in the second half<br>
	 * (<b>if reached yet</b>)
	 */
	private Score second;

	/**
	 * match update items<br>
	 * (<b>if any existing</b>)
	 */
	private List<StreamItem> items;

	/**
	 * create a new handball ticker stream
	 * 
	 * @param time
	 *            current match time
	 * @param home
	 *            home team
	 * @param guest
	 *            guest team
	 * @param first
	 *            score of the teams in the first half
	 * @param second
	 *            score of the teams in the second half
	 * @param items
	 *            match update items (may be null)
	 */
	public Stream(final MatchTime time, final Team home, final Team guest,
			final Score first, final Score second, final List<StreamItem> items) {
		this.time = time;
		this.home = home;
		this.guest = guest;
		this.first = first;
		this.second = second;
		this.items = items;
	}

	/**
	 * @return current match time
	 */
	public MatchTime getTime() {
		return this.time;
	}

	public void setTime(final MatchTime time) {
		this.time = time;
	}

	/**
	 * @return home team
	 */
	public Team getHome() {
		return this.home;
	}

	public void setHome(final Team home) {
		this.home = home;
	}

	/**
	 * @return guest team
	 */
	public Team getGuest() {
		return this.guest;
	}

	public void setGuest(final Team guest) {
		this.guest = guest;
	}

	/**
	 * @return score of the teams in the first half
	 */
	public Score getFirst() {
		return this.first;
	}

	public void setFirst(final Score first) {
		this.first = first;
	}

	/**
	 * @return score of the teams in the second half<br>
	 *         (<b>if reached yet</b>)
	 */
	public Score getSecond() {
		return this.second;
	}

	public void setSecond(final Score second) {
		this.second = second;
	}

	/**
	 * @return match update items<br>
	 *         (<b>if any existing</b>)
	 */
	public List<StreamItem> getItems() {
		return this.items;
	}

	public void setItems(final List<StreamItem> items) {
		this.items = items;
	}

	@Override
	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		final JSONObject stream = new JSONObject();
		stream.put(HandballTickerStream.KEY_TIME, this.time.toJSON());
		stream.put(HandballTickerStream.KEY_HOME, this.home.toJSON());
		stream.put(HandballTickerStream.KEY_GUEST, this.guest.toJSON());
		stream.put(HandballTickerStream.KEY_FIRST, this.first.toJSON());
		if (this.second != null) {
			stream.put(HandballTickerStream.KEY_SECOND, this.second.toJSON());
		}
		if ((this.items != null) && (this.items.size() > 0)) {
			final JSONArray items = new JSONArray();
			for (StreamItem item : this.items) {
				items.add(item.toJSON());
			}
			stream.put(HandballTickerStream.KEY_ITEMS, items);
		}
		return stream;
	}

	public static Stream parseJSON(final String jsonString)
			throws StreamFormatException {
		final JSONObject streamObject = parseJSONObject(jsonString);
		if (streamObject != null) {
			return loadFromJSON(streamObject);
		} else {
			throw new StreamFormatException("\"" + jsonString
					+ "\" is not a JSON String");
		}
	}

	public static Stream loadFromJSON(final JSONObject streamObject)
			throws StreamFormatException {
		final MatchTime time = parseTime(streamObject);
		final Team home = parseHome(streamObject);
		final Team guest = parseGuest(streamObject);
		final Score first = parseFirst(streamObject);
		final Score second = parseSecond(streamObject);
		final List<StreamItem> items = parseItems(streamObject);
		return new Stream(time, home, guest, first, second, items);
	}

	private static MatchTime parseTime(final JSONObject streamObject)
			throws StreamFormatException {
		final Object time = streamObject.get(HandballTickerStream.KEY_TIME);
		if (time != null) {
			if (time instanceof JSONObject) {
				try {
					return MatchTime.loadFromJSON((JSONObject) time);
				} catch (final MatchTimeFormatException e) {
					throw new StreamFormatException("field \""
							+ HandballTickerStream.KEY_TIME
							+ "\" is not a match time object", e);
				}
			} else {
				throw new StreamFormatException("field \""
						+ HandballTickerStream.KEY_TIME + "\" is malformed: \""
						+ time + "\" is not a JSON object");
			}
		} else {
			throw new StreamFormatException("field \""
					+ HandballTickerStream.KEY_TIME + "\" is missing");
		}
	}

	private static Team parseHome(final JSONObject streamObject)
			throws StreamFormatException {
		final Object home = streamObject.get(HandballTickerStream.KEY_HOME);
		if (home != null) {
			if (home instanceof JSONObject) {
				try {
					return Team.loadFromJSON((JSONObject) home);
				} catch (final TeamFormatException e) {
					throw new StreamFormatException("field \""
							+ HandballTickerStream.KEY_HOME
							+ "\" is not a team object", e);
				}
			} else {
				throw new StreamFormatException("field \""
						+ HandballTickerStream.KEY_HOME + "\" is malformed: \""
						+ home + "\" is not a JSON object");
			}
		} else {
			throw new StreamFormatException("field \""
					+ HandballTickerStream.KEY_HOME + "\" is missing");
		}
	}

	private static Team parseGuest(final JSONObject streamObject)
			throws StreamFormatException {
		final Object guest = streamObject.get(HandballTickerStream.KEY_GUEST);
		if (guest != null) {
			if (guest instanceof JSONObject) {
				try {
					return Team.loadFromJSON((JSONObject) guest);
				} catch (final TeamFormatException e) {
					throw new StreamFormatException("field \""
							+ HandballTickerStream.KEY_GUEST
							+ "\" is not a team object", e);
				}
			} else {
				throw new StreamFormatException("field \""
						+ HandballTickerStream.KEY_GUEST
						+ "\" is malformed: \"" + guest
						+ "\" is not a JSON object");
			}
		} else {
			throw new StreamFormatException("field \""
					+ HandballTickerStream.KEY_GUEST + "\" is missing");
		}
	}

	private static Score parseFirst(final JSONObject streamObject)
			throws StreamFormatException {
		final Object first = streamObject.get(HandballTickerStream.KEY_FIRST);
		if (first != null) {
			if (first instanceof JSONObject) {
				try {
					return Score.loadFromJSON((JSONObject) first);
				} catch (final ScoreFormatException e) {
					throw new StreamFormatException("field \""
							+ HandballTickerStream.KEY_FIRST
							+ "\" is not a score object", e);
				}
			} else {
				throw new StreamFormatException("field \""
						+ HandballTickerStream.KEY_FIRST
						+ "\" is malformed: \"" + first
						+ "\" is not a JSON object");
			}
		} else {
			throw new StreamFormatException("field \""
					+ HandballTickerStream.KEY_FIRST + "\" is missing");
		}
	}

	private static Score parseSecond(final JSONObject streamObject)
			throws StreamFormatException {
		final Object second = streamObject.get(HandballTickerStream.KEY_SECOND);
		if (second != null) {
			if (second instanceof JSONObject) {
				try {
					return Score.loadFromJSON((JSONObject) second);
				} catch (final ScoreFormatException e) {
					throw new StreamFormatException("field \""
							+ HandballTickerStream.KEY_SECOND
							+ "\" is not a score object", e);
				}
			} else {
				throw new StreamFormatException("field \""
						+ HandballTickerStream.KEY_SECOND
						+ "\" is malformed: \"" + second
						+ "\" is not a JSON object");
			}
		}

		return null;
	}

	private static List<StreamItem> parseItems(final JSONObject streamObject)
			throws StreamFormatException {
		final Object oItems = streamObject.get(HandballTickerStream.KEY_ITEMS);
		if (oItems != null) {
			if (oItems instanceof JSONArray) {
				final JSONArray aItems = (JSONArray) oItems;

				if (!aItems.isEmpty()) {
					final List<StreamItem> items = new LinkedList<StreamItem>();
					StreamItem item;

					for (Object oItem : aItems) {
						if (oItem instanceof JSONObject) {
							try {
								item = StreamItem
										.parseAnyJSON((JSONObject) oItem);
								items.add(item);
							} catch (final StreamItemFormatException e) {
								throw new StreamFormatException("field \""
										+ HandballTickerStream.KEY_ITEMS
										+ "\" is malformed: \"" + oItem
										+ "\" is not a stream item object", e);
							}
						} else {
							throw new StreamFormatException("field \""
									+ HandballTickerStream.KEY_ITEMS
									+ "\" is malformed: \"" + oItem
									+ "\" is not a JSON object");
						}
					}
				}
			} else {
				throw new StreamFormatException("field \""
						+ HandballTickerStream.KEY_ITEMS
						+ "\" is malformed: \"" + oItems
						+ "\" is not a JSON array");
			}
		}

		return null;
	}

}