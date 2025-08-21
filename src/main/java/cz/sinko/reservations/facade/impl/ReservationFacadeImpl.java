package cz.sinko.reservations.facade.impl;

import cz.sinko.reservations.api.dto.ReservationDto;
import cz.sinko.reservations.api.dto.CreateReservationRequest;
import cz.sinko.reservations.facade.ReservationFacade;
import cz.sinko.reservations.configuration.annotations.Facade;
import cz.sinko.reservations.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Implementation of {@link ReservationFacade}.
 *
 * @author Radovan Šinko
 */
@Slf4j
@Facade
@RequiredArgsConstructor
public class ReservationFacadeImpl implements ReservationFacade {

    private final ReservationService reservationService;

    @Override
    public ReservationDto createReservation(CreateReservationRequest request) {
        log.info("Creating reservation for room {} from {} to {}",
                request.getRoomUuid(), request.getStartTime(), request.getEndTime());

        final ReservationDto reservation = reservationService.createReservation(request);

        log.info("Successfully created reservation with uuid: {}", reservation.getUuid());
        return reservation;
    }

    @Override
    public List<ReservationDto> getAllReservations() {
        log.info("Getting all reservations");

        final List<ReservationDto> reservations = reservationService.getAllReservations();

        log.info("Found {} total reservations", reservations.size());
        return reservations;
    }
}
