package ee.mario.videoplanetbackend.repository;

import ee.mario.videoplanetbackend.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
}
