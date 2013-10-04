package de.jablab.HandballTickerStream.exceptions;

public class MatchTimeFormatException extends FormatException {

	public MatchTimeFormatException(final String reason) {
		super("failed to parse match time", reason, null);
	}

}