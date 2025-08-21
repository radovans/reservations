package cz.sinko.reservations.api.controller;

import cz.sinko.reservations.api.dto.ReservationDto;
import cz.sinko.reservations.api.dto.CreateReservationRequest;
import cz.sinko.reservations.facade.ReservationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Controller for managing reservations.
 *
 * @author Radovan Šinko
 */
@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationFacade reservationFacade;

    /**
     * Create a new reservation.
     *
     * @param request the reservation creation request
     * @return ResponseEntity with the created reservation
     */
    @PostMapping
    public ResponseEntity<ReservationDto> createReservation(@Valid @RequestBody CreateReservationRequest request) {
        final ReservationDto createdReservation = reservationFacade.createReservation(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReservation);
    }

    /**
     * Get all reservations.
     *
     * @return ResponseEntity with list of all reservations
     */
    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        final List<ReservationDto> reservations = reservationFacade.getAllReservations();
        return ResponseEntity.ok(reservations);
    }
}
