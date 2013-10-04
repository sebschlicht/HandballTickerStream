package de.jablab.HandballTickerStream.exceptions;

public class ScoreFormatException extends FormatException {

	public ScoreFormatException(final String reason) {
		super("failed to parse score", reason, null);
	}

}