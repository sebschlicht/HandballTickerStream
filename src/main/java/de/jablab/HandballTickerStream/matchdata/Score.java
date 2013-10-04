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
	 * load score from JSON object
	 * 
	 * @param score
	 *            JSON score object
	 * @throws ScoreFormatException
	 *             if the JSON object is not a valid score object
	 */
	public Score(final JSONObject score) throws ScoreFormatException {
		final String sHome = (String) score
				.get(HandballTickerStream.Score.KEY_HOME);
		if (sHome != null) {
			try {
				this.home = Integer.valueOf(sHome);
			} catch (final NumberFormatException e) {
				throw new ScoreFormatException("field \""
						+ HandballTickerStream.Score.KEY_HOME
						+ "\" is malformed: \"" + sHome + "\" is not a number");
			}

			final String sGuest = (String) score
					.get(HandballTickerStream.Score.KEY_GUEST);
			if (sGuest != null) {
				try {
					this.guest = Integer.valueOf(sGuest);
				} catch (final NumberFormatException e) {
					throw new ScoreFormatException("field \""
							+ HandballTickerStream.Score.KEY_GUEST
							+ "\" is malformed: \"" + sGuest
							+ "\" is not a number");
				}
			}

			throw new ScoreFormatException("field \""
					+ HandballTickerStream.Score.KEY_GUEST + "\" is missing");
		}

		throw new ScoreFormatException("field \""
				+ HandballTickerStream.Score.KEY_HOME + "\" is missing");
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
		object.put(HandballTickerStream.Score.KEY_HOME, this.home);
		object.put(HandballTickerStream.Score.KEY_GUEST, this.guest);
		return object;
	}

	@Override
	public String toJSONString() {
		return this.toJSON().toJSONString();
	}

}