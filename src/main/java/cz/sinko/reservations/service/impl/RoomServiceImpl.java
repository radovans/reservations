package cz.sinko.reservations.service.impl;

import cz.sinko.reservations.api.dto.RoomDto;
import cz.sinko.reservations.api.dto.CreateRoomRequest;
import cz.sinko.reservations.api.dto.FindAvailableRoomsRequest;
import cz.sinko.reservations.repository.RoomRepository;
import cz.sinko.reservations.repository.entity.Room;
import cz.sinko.reservations.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of {@link RoomService}.
 *
 * @author Radovan Šinko
 */
@Service
@RequiredArgsConstructor
@Transactional
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public RoomDto createRoom(CreateRoomRequest request) {
        final Room room = new Room();
        room.setName(request.getName());

        final Room savedRoom = roomRepository.save(room);
        return convertToDto(savedRoom);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomDto> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomDto> findAvailableRooms(FindAvailableRoomsRequest request) {
        return findAvailableRooms(request.getStartTime(), request.getEndTime());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomDto> findAvailableRooms(LocalDateTime startTime, LocalDateTime endTime) {
        return roomRepository.findAvailableRooms(startTime, endTime).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isRoomAvailable(UUID roomUuid, LocalDateTime startTime, LocalDateTime endTime) {
        return roomRepository.isRoomAvailableByUuid(roomUuid, startTime, endTime);
    }

    private RoomDto convertToDto(Room room) {
        return new RoomDto(
                room.getUuid(),
                room.getName(),
                room.getCreatedAt()
        );
    }
}
