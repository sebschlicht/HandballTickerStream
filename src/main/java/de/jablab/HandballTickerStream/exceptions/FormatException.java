package de.jablab.HandballTickerStream.exceptions;

/**
 * basic exception for format problems in the handball ticker stream
 * 
 * @author sebschlicht
 * 
 */
public abstract class FormatException extends Exception {

	private final String title;
	private final String reason;
	private final FormatException previous;

	public FormatException(final String title, final String reason,
			final FormatException previous) {
		this.title = title;
		this.reason = reason;
		this.previous = previous;
	}

	@Override
	public String getMessage() {
		final StringBuilder message = new StringBuilder();
		if (this.previous != null) {
			message.append(this.title);
			message.append("!\n");
			message.append(this.previous.getMessage());
		} else {
			message.append(this.title);
			message.append(": ");
			message.append(this.reason);
			message.append("!");
		}
		return message.toString();
	}

}