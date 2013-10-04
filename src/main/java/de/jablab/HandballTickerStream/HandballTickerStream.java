package de.jablab.HandballTickerStream;

/**
 * handball ticker stream format
 * 
 * @author sebschlicht
 * 
 */
public class HandballTickerStream {

	public class MatchTime {

		public static final String KEY_MINUTE = "minute";
		public static final String KEY_PHASE = "phase";

	}

	public class Team {

		public static final String KEY_IDENTIFIER = "id";
		public static final String KEY_NAME = "name";
		public static final String KEY_LOGO_URL = "logo";

	}

	public class Score {

		public static final String KEY_HOME = "home";
		public static final String KEY_GUEST = "guest";

	}

}