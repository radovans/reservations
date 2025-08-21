package cz.sinko.reservations.repository;

import cz.sinko.reservations.repository.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Reservation entities.
 *
 * @author Radovan Šinko
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
