package ee.mario.videoplanetbackend.repository;

import ee.mario.videoplanetbackend.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
