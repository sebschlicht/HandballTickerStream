package de.jablab.HandballTickerStream.exceptions;

public class TeamFormatException extends StreamFormatException {

	private static final String FAILURE_MSG = "failed to parse team";

	public TeamFormatException(final String reason) {
		super(reason, null);
	}

	@Override
	public String getMessage() {
		return FAILURE_MSG + ": " + this.reason + "!";
	}

}