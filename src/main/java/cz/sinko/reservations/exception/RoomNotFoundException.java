package cz.sinko.reservations.exception;

import java.util.UUID;

/**
 * Exception thrown when a room with the specified ID is not found.
 *
 * @author Radovan Šinko
 */
public class RoomNotFoundException extends RuntimeException {

    /**
     * Constructor with message.
     *
     * @param message the error message
     */
    public RoomNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor with room ID.
     *
     * @param roomId the room ID that was not found
     */
    public RoomNotFoundException(Long roomId) {
        super("Room not found with id: " + roomId);
    }

    /**
     * Constructor with room UUID.
     *
     * @param roomUuid the room UUID that was not found
     */
    public RoomNotFoundException(UUID roomUuid) {
        super("Room not found with uuid: " + roomUuid);
    }

    /**
     * Constructor with message and cause.
     *
     * @param message the error message
     * @param cause the cause of the exception
     */
    public RoomNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
