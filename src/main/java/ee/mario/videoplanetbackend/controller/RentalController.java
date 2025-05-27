package ee.mario.videoplanetbackend.controller;

import ee.mario.videoplanetbackend.entity.Customer;
import ee.mario.videoplanetbackend.entity.Movie;
import ee.mario.videoplanetbackend.entity.Rental;
import ee.mario.videoplanetbackend.repository.CustomerRepository;
import ee.mario.videoplanetbackend.repository.RentalRepository;
import ee.mario.videoplanetbackend.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RentalController {

    @Autowired
    RentalRepository rentalRepository;

    @Autowired
    RentalService rentalService;

    @GetMapping("rentals/{customerId}")
    public List<Rental> getRental(@PathVariable Long customerId) {
        return rentalRepository.findByCustomerId(customerId);
    }

    @PostMapping("rentals/{customerId}")
    public Rental postRental(@PathVariable Long customerId, @RequestBody List<Movie> movies) {
        return rentalService.saveRental(customerId, movies);
    }
}


