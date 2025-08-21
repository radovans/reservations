package cz.sinko.reservations.facade.impl;

import cz.sinko.reservations.api.dto.RoomDto;
import cz.sinko.reservations.api.dto.CreateRoomRequest;
import cz.sinko.reservations.api.dto.FindAvailableRoomsRequest;
import cz.sinko.reservations.facade.RoomFacade;
import cz.sinko.reservations.configuration.annotations.Facade;
import cz.sinko.reservations.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Implementation of {@link RoomFacade}.
 *
 * @author Radovan Šinko
 */
@Slf4j
@Facade
@RequiredArgsConstructor
public class RoomFacadeImpl implements RoomFacade {

    private final RoomService roomService;

    @Override
    public RoomDto createRoom(CreateRoomRequest request) {
        log.info("Creating room with name: {}", request.getName());

        final RoomDto createdRoom = roomService.createRoom(request);

        log.info("Successfully created room with uuid: {}", createdRoom.getUuid());
        return createdRoom;
    }

    @Override
    public List<RoomDto> getAllRooms() {
        log.info("Getting all rooms");

        final List<RoomDto> rooms = roomService.getAllRooms();

        log.info("Found {} total rooms", rooms.size());
        return rooms;
    }

    @Override
    public List<RoomDto> findAvailableRooms(FindAvailableRoomsRequest request) {
        log.info("Finding available rooms from {} to {}",
                request.getStartTime(), request.getEndTime());

        final List<RoomDto> availableRooms = roomService.findAvailableRooms(request);

        log.info("Found {} available rooms", availableRooms.size());
        return availableRooms;
    }
}
