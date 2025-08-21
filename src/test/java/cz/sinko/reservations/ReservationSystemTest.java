package cz.sinko.reservations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import cz.sinko.reservations.api.dto.CreateReservationRequest;
import cz.sinko.reservations.api.dto.CreateRoomRequest;
import cz.sinko.reservations.api.dto.FindAvailableRoomsRequest;
import cz.sinko.reservations.api.dto.RoomDto;
import cz.sinko.reservations.exception.ReservationConflictException;
import cz.sinko.reservations.service.ReservationService;
import cz.sinko.reservations.service.RoomService;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Testcontainers
class ReservationSystemTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private RoomService roomService;

    @Autowired
    private ReservationService reservationService;

    @Test
    void testCollisionDetection() {
        // Given
        CreateRoomRequest roomRequest = new CreateRoomRequest("Test Room");
        RoomDto room = roomService.createRoom(roomRequest);
        assertNotNull(room.getUuid());

        LocalDateTime start1 = LocalDateTime.parse("2025-07-01T06:00:00");
        LocalDateTime end1 = LocalDateTime.parse("2025-07-01T08:00:00");

        CreateReservationRequest reservation1 = new CreateReservationRequest(
                room.getUuid(), start1, end1, "First Meeting"
        );

        var firstReservation = reservationService.createReservation(reservation1);
        assertNotNull(firstReservation.getUuid());

        // When
        LocalDateTime start2 = LocalDateTime.parse("2025-07-01T07:00:00");
        LocalDateTime end2 = LocalDateTime.parse("2025-07-01T09:00:00");

        CreateReservationRequest reservation2 = new CreateReservationRequest(
                room.getUuid(), start2, end2, "Second Meeting"
        );

        // Then
        ReservationConflictException exception = assertThrows(ReservationConflictException.class, () -> {
            reservationService.createReservation(reservation2);
        });

        assertTrue(exception.getMessage().contains("conflicting reservation"));
    }

    @Test
    void testFindAvailableRooms() {
        // Given
        CreateRoomRequest roomRequest = new CreateRoomRequest("Test Room");
        RoomDto room = roomService.createRoom(roomRequest);
        assertNotNull(room.getUuid());

        LocalDateTime start = LocalDateTime.parse("2025-07-01T06:00:00");
        LocalDateTime end = LocalDateTime.parse("2025-07-01T08:00:00");

        CreateReservationRequest reservation = new CreateReservationRequest(
                room.getUuid(), start, end, "Test Meeting"
        );

        reservationService.createReservation(reservation);

        // When
        LocalDateTime searchStart = LocalDateTime.parse("2025-07-01T06:00:00");
        LocalDateTime searchEnd = LocalDateTime.parse("2025-07-01T07:00:00");

        FindAvailableRoomsRequest availableRequest = new FindAvailableRoomsRequest(searchStart, searchEnd);
        var availableRooms = roomService.findAvailableRooms(availableRequest);

        // Then
        assertTrue(availableRooms.isEmpty());

        // When
        LocalDateTime searchStart2 = LocalDateTime.parse("2025-07-01T08:00:00");
        LocalDateTime searchEnd2 = LocalDateTime.parse("2025-07-01T09:00:00");

        FindAvailableRoomsRequest availableRequest2 = new FindAvailableRoomsRequest(searchStart2, searchEnd2);
        var availableRooms2 = roomService.findAvailableRooms(availableRequest2);

        // Then
        assertEquals(1, availableRooms2.size());
        assertEquals(room.getUuid(), availableRooms2.get(0).getUuid());
    }

    @Test
    void testRoomAvailability() {
        // Given
        CreateRoomRequest roomRequest = new CreateRoomRequest("Test Room");
        RoomDto room = roomService.createRoom(roomRequest);
        assertNotNull(room.getUuid());

        // When
        LocalDateTime start = LocalDateTime.parse("2025-07-01T06:00:00");
        LocalDateTime end = LocalDateTime.parse("2025-07-01T08:00:00");

        // Then
        assertTrue(roomService.isRoomAvailable(room.getUuid(), start, end));

        // When
        CreateReservationRequest reservation = new CreateReservationRequest(
                room.getUuid(), start, end, "Test Meeting"
        );

        reservationService.createReservation(reservation);

        // Then
        assertFalse(roomService.isRoomAvailable(room.getUuid(), start, end));

        // When
        LocalDateTime afterStart = LocalDateTime.parse("2025-07-01T08:00:00");
        LocalDateTime afterEnd = LocalDateTime.parse("2025-07-01T10:00:00");

        // Then
        assertTrue(roomService.isRoomAvailable(room.getUuid(), afterStart, afterEnd));
    }
}
