package cz.sinko.reservations.facade;

import cz.sinko.reservations.api.dto.ReservationDto;
import cz.sinko.reservations.api.dto.CreateReservationRequest;

import java.util.List;

/**
 * Facade for managing reservations.
 *
 * @author Radovan Šinko
 */
public interface ReservationFacade {

    /**
     * Create a new reservation with business logic validation.
     *
     * @param request the reservation creation request
     * @return the created reservation DTO
     */
    ReservationDto createReservation(CreateReservationRequest request);

    /**
     * Get all reservations.
     *
     * @return list of all reservation DTOs
     */
    List<ReservationDto> getAllReservations();
}
