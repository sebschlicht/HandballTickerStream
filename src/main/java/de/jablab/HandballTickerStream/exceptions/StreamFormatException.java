package de.jablab.HandballTickerStream.exceptions;

public class StreamFormatException extends FormatException {

	private static final String TITLE = "failed to parse handball ticker stream";

	public StreamFormatException(final String reason) {
		super(TITLE, reason, null);
	}

	public StreamFormatException(final String reason,
			final FormatException previous) {
		super(TITLE, reason, previous);
	}

}