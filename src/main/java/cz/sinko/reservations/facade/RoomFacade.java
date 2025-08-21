package cz.sinko.reservations.facade;

import cz.sinko.reservations.api.dto.RoomDto;
import cz.sinko.reservations.api.dto.CreateRoomRequest;
import cz.sinko.reservations.api.dto.FindAvailableRoomsRequest;

import java.util.List;

/**
 * Facade for managing rooms.
 *
 * @author Radovan Šinko
 */
public interface RoomFacade {

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
}
