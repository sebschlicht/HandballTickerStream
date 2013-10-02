package de.jablab.HandballTickerStream.exceptions;

/**
 * the stream passed could not be loaded as it does not follow the handball
 * ticker stream format
 * 
 * @author sebschlicht
 * 
 */
public class StreamFormatException extends Exception {

	/**
	 * reason for the stream's invalidity
	 */
	protected final String reason;

	/**
	 * previous stream format exception caused the problem
	 */
	protected final StreamFormatException previous;

	/**
	 * create a new stream format exception
	 * 
	 * @param reason
	 *            reason for the stream's invalidity
	 * @param previous
	 *            previous stream format exception caused the problem
	 */
	public StreamFormatException(final String reason,
			final StreamFormatException previous) {
		this.reason = reason;
		this.previous = previous;
	}

	@Override
	public String getMessage() {
		final StringBuilder message = new StringBuilder(
				"failed to parse handball ticker stream");
		if (this.reason != null) {
			message.append(": ");
			message.append(this.reason);
		}
		message.append("!");
		if (this.previous != null) {
			message.append("\n");
			message.append(this.previous.getMessage());
		}
		return message.toString();
	}

}