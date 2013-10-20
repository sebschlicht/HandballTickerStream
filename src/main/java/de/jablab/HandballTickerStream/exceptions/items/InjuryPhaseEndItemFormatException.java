package de.jablab.HandballTickerStream.exceptions.items;

import de.jablab.HandballTickerStream.exceptions.FormatException;
import de.jablab.HandballTickerStream.exceptions.StreamItemFormatException;

public class InjuryPhaseEndItemFormatException extends
		StreamItemFormatException {

	private static final String TITLE = "failed to parse injury phase end stream item";

	public InjuryPhaseEndItemFormatException(final String reason) {
		super(TITLE, reason, null);
	}

	public InjuryPhaseEndItemFormatException(final String reason,
			final FormatException previous) {
		super(TITLE, reason, previous);
	}

}