package de.jablab.HandballTickerStream.exceptions;

public class PlayerFormatException extends FormatException {

	public PlayerFormatException(final String reason) {
		super("failed to parse player", reason, null);
	}

}