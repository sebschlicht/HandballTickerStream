package de.jablab.HandballTickerStream.exceptions.items;

import de.jablab.HandballTickerStream.exceptions.FormatException;

public class PhaseEndItemFormatException extends FormatException {

	public PhaseEndItemFormatException(final String reason) {
		super("failed to parse phase end stream item", reason, null);
	}

}