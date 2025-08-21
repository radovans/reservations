package cz.sinko.reservations.service;

import cz.sinko.reservations.api.dto.RoomDto;
import cz.sinko.reservations.api.dto.CreateRoomRequest;
import cz.sinko.reservations.api.dto.FindAvailableRoomsRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Service for managing rooms.
 *
 * @author Radovan Šinko
 */
public interface RoomService {

    /**
     * Create a new room.
     *
     * @param request the room creation request
     * @return the created room DTO
     */
    RoomDto createRoom(CreateRoomRequest request);

    /**
     * Get all rooms.
     *
     * @return list of all room DTOs
     */
    List<RoomDto> getAllRooms();

    /**
     * Find available rooms for a given time slot.
     *
     * @param request the request containing time range
     * @return list of available room DTOs
     */
    List<RoomDto> findAvailableRooms(FindAvailableRoomsRequest request);

    /**
     * Find available rooms for a given time slot (direct method).
     *
     * @param startTime the start time of the reservation
     * @param endTime the end time of the reservation
     * @return list of available room DTOs
     */
    List<RoomDto> findAvailableRooms(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * Check if a room is available for a given time slot by UUID.
     *
     * @param roomUuid the UUID of the room to check
     * @param startTime the start time of the reservation
     * @param endTime the end time of the reservation
     * @return true if the room is available, false otherwise
     */
    boolean isRoomAvailable(UUID roomUuid, LocalDateTime startTime, LocalDateTime endTime);
}
