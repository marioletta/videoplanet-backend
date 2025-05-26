package ee.mario.videoplanetbackend.service;

import ee.mario.videoplanetbackend.entity.Movie;
import ee.mario.videoplanetbackend.entity.Rental;
import ee.mario.videoplanetbackend.repository.CustomerRepository;
import ee.mario.videoplanetbackend.repository.MovieRepository;
import ee.mario.videoplanetbackend.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RentalService {

    @Autowired
    RentalRepository rentalRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    CustomerRepository customerRepository;

    public Rental saveRental(Long customerId, List<Movie> movies) {
        Rental rental = new Rental();
        rental.setCustomer(customerRepository.findById(customerId).orElseThrow());
        rental.setMovies(movies);
        return rentalRepository.save(rental);
    }

}