package cz.sinko.reservations.repository;

import cz.sinko.reservations.repository.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing Room entities.
 *
 * @author Radovan Šinko
 */
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    /**
     * Find rooms that are available for the given time slot.
     * A room is available if it has no overlapping reservations.
     *
     * @param startTime the start time of the reservation
     * @param endTime the end time of the reservation
     * @return list of available rooms
     */
    @Query("""
        SELECT DISTINCT r FROM Room r
        WHERE r.id NOT IN (
            SELECT DISTINCT res.room.id FROM Reservation res
            WHERE (res.startTime < :endTime AND res.endTime > :startTime)
        )
        ORDER BY r.name
        """)
    List<Room> findAvailableRooms(@Param("startTime") LocalDateTime startTime,
                                  @Param("endTime") LocalDateTime endTime);

    /**
     * Find room by UUID.
     *
     * @param uuid the UUID of the room to find
     * @return Optional containing the room if found
     */
    Optional<Room> findByUuid(UUID uuid);

    /**
     * Check if a specific room is available for the given time slot by UUID.
     *
     * @param roomUuid the UUID of the room to check
     * @param startTime the start time of the reservation
     * @param endTime the end time of the reservation
     * @return true if the room is available, false otherwise
     */
    @Query("""
        SELECT COUNT(res) = 0 FROM Reservation res
        WHERE res.room.uuid = :roomUuid
        AND (res.startTime < :endTime AND res.endTime > :startTime)
        """)
    boolean isRoomAvailableByUuid(@Param("roomUuid") UUID roomUuid,
                                 @Param("startTime") LocalDateTime startTime,
                                 @Param("endTime") LocalDateTime endTime);
}
