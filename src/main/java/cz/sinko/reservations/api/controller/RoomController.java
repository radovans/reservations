package cz.sinko.reservations.api.controller;

import cz.sinko.reservations.api.dto.RoomDto;
import cz.sinko.reservations.api.dto.CreateRoomRequest;
import cz.sinko.reservations.api.dto.FindAvailableRoomsRequest;
import cz.sinko.reservations.facade.RoomFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Controller for managing rooms.
 *
 * @author Radovan Šinko
 */
@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomFacade roomFacade;

    /**
     * Create a new room.
     *
     * @param request the room creation request
     * @return ResponseEntity with the created room
     */
    @PostMapping
    public ResponseEntity<RoomDto> createRoom(@Valid @RequestBody CreateRoomRequest request) {
        final RoomDto createdRoom = roomFacade.createRoom(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoom);
    }

    /**
     * Get all rooms.
     *
     * @return ResponseEntity with list of all rooms
     */
    @GetMapping
    public ResponseEntity<List<RoomDto>> getAllRooms() {
        final List<RoomDto> rooms = roomFacade.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    /**
     * Find available rooms for a given time slot.
     *
     * @param request the request containing time range
     * @return ResponseEntity with list of available rooms
     */
    @PostMapping("/available")
    public ResponseEntity<List<RoomDto>> findAvailableRooms(@Valid @RequestBody FindAvailableRoomsRequest request) {
        final List<RoomDto> availableRooms = roomFacade.findAvailableRooms(request);
        return ResponseEntity.ok(availableRooms);
    }
}
