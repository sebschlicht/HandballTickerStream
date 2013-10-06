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
	 * @return JSON object
	 */
	abstract JSONObject toJSON();

	/**
	 * @return JSON String representing the object
	 */
	abstract String toJSONString();

}