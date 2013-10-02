package de.jablab.HandballTickerStream.exceptions;

public class MatchTimeFormatException extends StreamFormatException {

	private static final String FAILURE_MSG = "failed to parse match time";

	public MatchTimeFormatException(final String reason) {
		super(reason, null);
	}

	@Override
	public String getMessage() {
		return FAILURE_MSG + ": " + this.reason + "!";
	}

}