package ee.mario.videoplanetbackend.repository;

import ee.mario.videoplanetbackend.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByRentalOrderId(Long orderId);
}
