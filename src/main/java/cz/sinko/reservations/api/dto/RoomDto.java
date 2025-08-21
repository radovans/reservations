package cz.sinko.reservations.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO representing a room.
 *
 * @author Radovan Šinko
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {

    private UUID uuid;

    private String name;

    private LocalDateTime createdAt;
}
