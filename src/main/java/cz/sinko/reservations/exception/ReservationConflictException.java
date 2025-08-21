package cz.sinko.reservations.exception;

/**
 * Exception thrown when there is a conflict with existing reservations.
 *
 * @author Radovan Šinko
 */
public class ReservationConflictException extends RuntimeException {

    /**
     * Constructor with message.
     *
     * @param message the error message
     */
    public ReservationConflictException(String message) {
        super(message);
    }

    /**
     * Constructor with room ID and time range.
     *
     * @param roomId the room ID
     * @param startTime the start time
     * @param endTime the end time
     */
    public ReservationConflictException(Long roomId, String startTime, String endTime) {
        super("Room with id " + roomId + " is not available for the specified time slot (" +
              startTime + " to " + endTime + "). There is a conflicting reservation.");
    }

    /**
     * Constructor with message and cause.
     *
     * @param message the error message
     * @param cause the cause of the exception
     */
    public ReservationConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
