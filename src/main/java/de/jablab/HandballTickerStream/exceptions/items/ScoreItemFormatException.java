package de.jablab.HandballTickerStream.exceptions.items;

import de.jablab.HandballTickerStream.exceptions.FormatException;
import de.jablab.HandballTickerStream.exceptions.StreamItemFormatException;

public class ScoreItemFormatException extends StreamItemFormatException {

	private static final String TITLE = "failed to parse score stream item";

	public ScoreItemFormatException(final String reason) {
		super(TITLE, reason, null);
	}

	public ScoreItemFormatException(final String reason,
			final FormatException previous) {
		super(TITLE, reason, previous);
	}

}