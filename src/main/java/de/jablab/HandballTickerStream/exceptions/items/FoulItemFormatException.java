package de.jablab.HandballTickerStream.exceptions.items;

import de.jablab.HandballTickerStream.exceptions.FormatException;
import de.jablab.HandballTickerStream.exceptions.StreamItemFormatException;

public class FoulItemFormatException extends StreamItemFormatException {

	private static final String TITLE = "failed to parse foul stream item";

	public FoulItemFormatException(final String reason) {
		super(TITLE, reason, null);
	}

	public FoulItemFormatException(final String reason,
			final FormatException previous) {
		super(TITLE, reason, previous);
	}

}