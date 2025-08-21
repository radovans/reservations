package cz.sinko.reservations.exception;

/**
 * Exception thrown when the provided time range is invalid.
 *
 * @author Radovan Šinko
 */
public class InvalidTimeRangeException extends RuntimeException {

    /**
     * Constructor with message.
     *
     * @param message the error message
     */
    public InvalidTimeRangeException(String message) {
        super(message);
    }

    /**
     * Constructor with start and end times.
     *
     * @param startTime the start time
     * @param endTime the end time
     */
    public InvalidTimeRangeException(String startTime, String endTime) {
        super("Invalid time range: start time (" + startTime + ") must be before end time (" + endTime + ")");
    }

    /**
     * Constructor with message and cause.
     *
     * @param message the error message
     * @param cause the cause of the exception
     */
    public InvalidTimeRangeException(String message, Throwable cause) {
        super(message, cause);
    }
}
