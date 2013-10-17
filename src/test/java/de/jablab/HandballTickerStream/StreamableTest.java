package de.jablab.HandballTickerStream;

import static org.junit.Assert.fail;

import org.json.simple.JSONObject;

public class StreamableTest {

	protected static final String MALFORMED_JSON_OBJECT = "nojsonobject";
	protected static final String MALFORMED_JSON_ARRAY = "nojsonarray";

	protected JSONObject sourceObject;

	private String errorMessage;
	protected String[] errTrace;

	protected void setErrTrace(final String errorMessage) {
		this.errorMessage = errorMessage;
		this.errTrace = errorMessage.split("\n");
	}

	@SuppressWarnings("unchecked")
	protected void putToObject(final String key, final Object value) {
		this.sourceObject.put(key, value);
	}

	protected void checkForMissingField(final String fieldName) {
		if (!this.errTrace[0]
				.contains("field \"" + fieldName + "\" is missing")) {
			System.err.println(this.errorMessage);
			fail("field \"" + fieldName + "\" is not missing!");
		}
	}

	protected void checkForMalformedField(final String fieldName) {
		if (!this.errTrace[0].contains("field \"" + fieldName
				+ "\" is malformed")) {
			System.err.println(this.errorMessage);
			fail("field \"" + fieldName + "\" is not malformed!");
		}
	}
}