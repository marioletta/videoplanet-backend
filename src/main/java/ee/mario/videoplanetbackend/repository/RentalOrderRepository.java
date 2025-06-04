package ee.mario.videoplanetbackend.repository;

import ee.mario.videoplanetbackend.entity.RentalOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalOrderRepository extends JpaRepository<RentalOrder, Long> {
}
