package ee.mario.videoplanetbackend.controller;

import ee.mario.videoplanetbackend.entity.Movie;
import ee.mario.videoplanetbackend.entity.Rental;
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

    @PostMapping("orders")
    public Rental postRental(@RequestBody List<Movie> movies) {
        Long customerId = 1L;
        return rentalService.saveRental(customerId, movies);
    }
}


