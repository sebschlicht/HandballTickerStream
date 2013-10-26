package de.jablab.HandballTickerStream;

import org.json.simple.JSONObject;

import de.jablab.HandballTickerStream.exceptions.ScoreFormatException;

/**
 * score for both teams
 * 
 * @author sebschlicht
 * 
 */
public class Score extends Streamable {

	/**
	 * score of the home team
	 */
	private int home;

	/**
	 * score of the guest team
	 */
	private int guest;

	/**
	 * create a new score for both teams
	 * 
	 * @param home
	 *            score of the home team
	 * @param guest
	 *            score of the guest team
	 */
	public Score(final int home, final int guest) {
		this.home = home;
		this.guest = guest;
	}

	/**
	 * @return score of the home team
	 */
	public int getHome() {
		return this.home;
	}

	public void setHome(final int home) {
		this.home = home;
	}

	/**
	 * @return score of the guest team
	 */
	public int getGuest() {
		return this.guest;
	}

	public void setGuest(final int guest) {
		this.guest = guest;
	}

	@Override
	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		final JSONObject object = new JSONObject();
		object.put(HandballTickerStream.Score.KEY_HOME,
				String.valueOf(this.home));
		object.put(HandballTickerStream.Score.KEY_GUEST,
				String.valueOf(this.guest));
		return object;
	}

	@Override
	public boolean equals(final Object o) {
		if (o == null) {
			return false;
		}
		if (o == this) {
			return true;
		}
		if (!o.getClass().equals(this.getClass())) {
			return false;
		}

		final Score score = (Score) o;
		return ((score.getHome() == this.home) && (score.getGuest() == this.guest));
	}

	public static Score parseJSON(final String jsonString)
			throws ScoreFormatException {
		final JSONObject scoreObject = parseJSONObject(jsonString);
		if (scoreObject != null) {
			return loadFromJSON(scoreObject);
		} else {
			throw new ScoreFormatException("\"" + jsonString
					+ "\" is not a JSON String");
		}
	}

	public static Score loadFromJSON(final JSONObject scoreObject)
			throws ScoreFormatException {
		final int home = parseHome(scoreObject);
		final int guest = parseGuest(scoreObject);
		return new Score(home, guest);
	}

	private static int parseHome(final JSONObject scoreObject)
			throws ScoreFormatException {
		final String sHome = (String) scoreObject
				.get(HandballTickerStream.Score.KEY_HOME);
		if (sHome != null) {
			try {
				return Integer.valueOf(sHome);
			} catch (final NumberFormatException e) {
				throw new ScoreFormatException("field \""
						+ HandballTickerStream.Score.KEY_HOME
						+ "\" is malformed: \"" + sHome + "\" is not a number");
			}
		} else {
			throw new ScoreFormatException("field \""
					+ HandballTickerStream.Score.KEY_HOME + "\" is missing");
		}
	}

	private static int parseGuest(final JSONObject scoreObject)
			throws ScoreFormatException {
		final String sGuest = (String) scoreObject
				.get(HandballTickerStream.Score.KEY_GUEST);
		if (sGuest != null) {
			try {
				return Integer.valueOf(sGuest);
			} catch (final NumberFormatException e) {
				throw new ScoreFormatException("field \""
						+ HandballTickerStream.Score.KEY_GUEST
						+ "\" is malformed: \"" + sGuest + "\" is not a number");
			}
		} else {
			throw new ScoreFormatException("field \""
					+ HandballTickerStream.Score.KEY_GUEST + "\" is missing");
		}
	}

}