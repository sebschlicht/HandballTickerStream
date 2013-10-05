package de.jablab.HandballTickerStream.exceptions.items;

import de.jablab.HandballTickerStream.exceptions.StreamItemFormatException;

public class PhaseEndItemFormatException extends StreamItemFormatException {

	public PhaseEndItemFormatException(final String reason) {
		super("failed to parse phase end stream item", reason, null);
	}

}