package de.jablab.HandballTickerStream.exceptions;

public class TeamFormatException extends FormatException {

	public TeamFormatException(final String reason) {
		super("failed to parse team", reason, null);
	}

}