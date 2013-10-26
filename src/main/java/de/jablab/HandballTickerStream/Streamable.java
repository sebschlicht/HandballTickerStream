package de.jablab.HandballTickerStream;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * basic Java objects the handball ticker stream contains
 * 
 * @author sebschlicht
 * 
 */
public abstract class Streamable {

	/**
	 * JSON parser
	 */
	protected static final JSONParser JSON_PARSER = new JSONParser();

	/**
	 * parse a JSON String to a JSON object
	 * 
	 * @param jsonString
	 *            JSON String to be parsed
	 * @return JSON object represented by the String passed<br>
	 *         <b>null</b> if the String passed is not a valid JSON String
	 */
	protected static JSONObject parseJSONObject(final String jsonString) {
		try {
			return (JSONObject) JSON_PARSER.parse(jsonString);
		} catch (final org.json.simple.parser.ParseException e) {
			return null;
		}
	}

	/**
	 * @return JSON object
	 */
	public abstract JSONObject toJSON();

	/**
	 * @return JSON String representing the object
	 */
	public String toJSONString() {
		return this.toJSON().toJSONString();
	}

}