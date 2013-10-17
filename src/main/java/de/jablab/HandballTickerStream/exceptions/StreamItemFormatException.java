package de.jablab.HandballTickerStream.exceptions;

public class StreamItemFormatException extends FormatException {

	private static final String TITLE = "failed to parse stream item";

	public StreamItemFormatException(final String reason) {
		super(TITLE, reason, null);
	}

	public StreamItemFormatException(final String reason,
			final FormatException previous) {
		super(TITLE, reason, previous);
	}

	public StreamItemFormatException(final String title, final String reason,
			final FormatException previous) {
		super(title, reason, previous);
	}

}