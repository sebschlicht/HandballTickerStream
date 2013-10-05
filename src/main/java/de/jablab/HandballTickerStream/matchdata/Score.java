package de.jablab.HandballTickerStream.matchdata;

import org.json.simple.JSONObject;

import de.jablab.HandballTickerStream.HandballTickerStream;
import de.jablab.HandballTickerStream.Streamable;
import de.jablab.HandballTickerStream.exceptions.ScoreFormatException;

/**
 * score for both teams
 * 
 * @author sebschlicht
 * 
 */
public class Score implements Streamable {

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
	public String toJSONString() {
		return this.toJSON().toJSONString();
	}

	/**
	 * load score from JSON object
	 * 
	 * @param score
	 *            score JSON object
	 * @throws ScoreFormatException
	 *             if the JSON object is not a valid score object
	 */
	public static Score parseJSON(final JSONObject score)
			throws ScoreFormatException {
		final int home = parseHome(score);
		final int guest = parseGuest(score);
		return new Score(home, guest);
	}

	/**
	 * parse the home field
	 * 
	 * @param score
	 *            score JSON object
	 * @return score of the home team
	 * @throws ScoreFormatException
	 *             if field missing or malformed
	 */
	private static int parseHome(final JSONObject score)
			throws ScoreFormatException {
		final String sHome = (String) score
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

	/**
	 * parse the guest field
	 * 
	 * @param score
	 *            score JSON object
	 * @return score of the guest team
	 * @throws ScoreFormatException
	 *             if field missing or malformed
	 */
	private static int parseGuest(final JSONObject score)
			throws ScoreFormatException {
		final String sGuest = (String) score
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