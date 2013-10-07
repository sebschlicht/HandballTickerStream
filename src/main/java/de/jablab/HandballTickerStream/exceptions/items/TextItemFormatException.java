package de.jablab.HandballTickerStream.exceptions.items;

import de.jablab.HandballTickerStream.exceptions.StreamItemFormatException;

public class TextItemFormatException extends StreamItemFormatException {

	public TextItemFormatException(final String reason) {
		super("failed to parse text stream item", reason, null);
	}

}