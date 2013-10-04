package de.jablab.HandballTickerStream;

import org.json.simple.JSONObject;

/**
 * interface for all Java objects the handball ticker stream contains
 * 
 * @author sebschlicht
 * 
 */
public interface Streamable {

	/**
	 * @return JSON object
	 */
	JSONObject toJSON();

	/**
	 * @return JSON String representing the object
	 */
	String toJSONString();

}