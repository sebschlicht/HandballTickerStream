package de.jablab.HandballTickerStream.exceptions.items;

import de.jablab.HandballTickerStream.exceptions.StreamItemFormatException;

public class TimeoutPhaseEndItemFormatException extends
		StreamItemFormatException {

	public TimeoutPhaseEndItemFormatException(final String reason) {
		super("failed to parse timeout phase end stream item", reason, null);
	}

}