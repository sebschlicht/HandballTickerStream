package de.jablab.HandballTickerStream.exceptions;

public class StreamItemFormatException extends FormatException {

	public StreamItemFormatException(final String reason) {
		super("failed to parse stream item", reason, null);
	}

	public StreamItemFormatException(final String reason,
			final FormatException previous) {
		super("failed to parse stream item", reason, previous);
	}

	public StreamItemFormatException(final String title, final String reason,
			final FormatException previous) {
		super(title, reason, previous);
	}

}