package cz.sinko.reservations.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO representing a reservation.
 *
 * @author Radovan Šinko
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {

    private UUID uuid;

    private UUID roomUuid;

    private String roomName;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String name;

    private LocalDateTime createdAt;
}
