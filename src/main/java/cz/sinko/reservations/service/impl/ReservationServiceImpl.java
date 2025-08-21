package cz.sinko.reservations.service.impl;

import cz.sinko.reservations.api.dto.ReservationDto;
import cz.sinko.reservations.api.dto.CreateReservationRequest;
import cz.sinko.reservations.exception.InvalidTimeRangeException;
import cz.sinko.reservations.exception.ReservationConflictException;
import cz.sinko.reservations.exception.RoomNotFoundException;
import cz.sinko.reservations.repository.ReservationRepository;
import cz.sinko.reservations.repository.RoomRepository;
import cz.sinko.reservations.repository.entity.Reservation;
import cz.sinko.reservations.repository.entity.Room;
import cz.sinko.reservations.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link ReservationService}.
 *
 * @author Radovan Šinko
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    @Override
    public ReservationDto createReservation(CreateReservationRequest request) {
        // Validate that the room exists
        final Room room = roomRepository.findByUuid(request.getRoomUuid())
                .orElseThrow(() -> new RoomNotFoundException(request.getRoomUuid()));

        // Check for time order validation
        if (request.getStartTime().isAfter(request.getEndTime()) ||
            request.getStartTime().equals(request.getEndTime())) {
            throw new InvalidTimeRangeException("Start time must be before end time");
        }

        // Check for collision (overlapping reservations)
        if (!roomRepository.isRoomAvailableByUuid(request.getRoomUuid(),
                request.getStartTime(), request.getEndTime())) {
            throw new ReservationConflictException(
                    "Room is not available for the specified time slot. There is a conflicting reservation.");
        }

        // Create the reservation
        final Reservation reservation = new Reservation();
        reservation.setRoom(room);
        reservation.setStartTime(request.getStartTime());
        reservation.setEndTime(request.getEndTime());
        reservation.setName(request.getName());

        final Reservation savedReservation = reservationRepository.save(reservation);
        return convertToDto(savedReservation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationDto> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ReservationDto convertToDto(Reservation reservation) {
        return new ReservationDto(
                reservation.getUuid(),
                reservation.getRoom().getUuid(),
                reservation.getRoom().getName(),
                reservation.getStartTime(),
                reservation.getEndTime(),
                reservation.getName(),
                reservation.getCreatedAt()
        );
    }
}
