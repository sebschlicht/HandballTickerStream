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

	public class StreamItem {

		public static final String KEY_PUBLISHED = "published";
		public static final String KEY_TIME = "time";
		public static final String KEY_TYPE = "type";
		public static final String KEY_OBJECT = "object";
		public static final String KEY_MESSAGE = "message";

		public class PhaseEndItem {

			public static final String KEY_BEFORE = "before";
			public static final String KEY_AFTER = "after";
			public static final String KEY_SUB_TYPE = "subType";
			public static final String KEY_OBJECT = "object";

		}

		public class TextItem {

			public static final String KEY_MESSAGE = "message";

		}

		public class ScoreItem {

			public static final String KEY_SCORE = "score";
			public static final String KEY_TEAM_ROLE = "team";
			public static final String KEY_TYPE = "type";
			public static final String KEY_PLAYER = "player";

		}

		public class FoulItem {

			public static final String KEY_PLAYER = "player";
			public static final String KEY_DISCIPLINES = "disciplines";
			public static final String KEY_VICTIM = "victim";

		}

	}

	public class Player {

		public static final String KEY_IDENTIFIER = "id";
		public static final String KEY_NUMBER = "number";
		public static final String KEY_NAME = "name";
		public static final String KEY_TEAM_ROLE = "team";

	}

}