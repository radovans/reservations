package cz.sinko.reservations.service;

import cz.sinko.reservations.api.dto.ReservationDto;
import cz.sinko.reservations.api.dto.CreateReservationRequest;

import java.util.List;

/**
 * Service for managing reservations.
 *
 * @author Radovan Šinko
 */
public interface ReservationService {

    /**
     * Create a new reservation with collision detection.
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
