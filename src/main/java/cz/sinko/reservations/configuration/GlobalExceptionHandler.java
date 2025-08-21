package cz.sinko.reservations.configuration;

import cz.sinko.reservations.api.ApiError;
import cz.sinko.reservations.exception.InvalidTimeRangeException;
import cz.sinko.reservations.exception.ReservationConflictException;
import cz.sinko.reservations.exception.RoomNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Global exception handler for the application.
 * Catches and processes various exceptions to return appropriate HTTP responses.
 *
 * @author Radovan Šinko
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles ResponseStatusException and returns appropriate error response.
     *
     * @param ex the ResponseStatusException to handle
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiError> handleResponseStatusException(ResponseStatusException ex) {
        final ApiError error = new ApiError(List.of(ex.getReason()));
        return ResponseEntity.status(ex.getStatusCode()).body(error);
    }

    /**
     * Handles RoomNotFoundException and returns 404 error response.
     *
     * @param ex the RoomNotFoundException to handle
     * @return ResponseEntity with 404 error details
     */
    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<ApiError> handleRoomNotFoundException(RoomNotFoundException ex) {
        final ApiError error = new ApiError(List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Handles ReservationConflictException and returns 409 error response.
     *
     * @param ex the ReservationConflictException to handle
     * @return ResponseEntity with 409 error details
     */
    @ExceptionHandler(ReservationConflictException.class)
    public ResponseEntity<ApiError> handleReservationConflictException(ReservationConflictException ex) {
        final ApiError error = new ApiError(List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    /**
     * Handles InvalidTimeRangeException and returns 400 error response.
     *
     * @param ex the InvalidTimeRangeException to handle
     * @return ResponseEntity with 400 error details
     */
    @ExceptionHandler(InvalidTimeRangeException.class)
    public ResponseEntity<ApiError> handleInvalidTimeRangeException(InvalidTimeRangeException ex) {
        final ApiError error = new ApiError(List.of(ex.getMessage()));
        return ResponseEntity.badRequest().body(error);
    }

    /**
     * Handles validation exceptions and returns 400 error response.
     *
     * @param ex the MethodArgumentNotValidException to handle
     * @return ResponseEntity with 400 error details
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        final Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );

        final ApiError error = new ApiError(List.of("Validation failed: " + errors.toString()));
        return ResponseEntity.badRequest().body(error);
    }

    /**
     * Handles generic exceptions and returns 500 error response.
     *
     * @param ex the Exception to handle
     * @return ResponseEntity with 500 error details
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex) {
        final ApiError error = new ApiError(List.of("An unexpected error occurred: " + ex.getMessage()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
