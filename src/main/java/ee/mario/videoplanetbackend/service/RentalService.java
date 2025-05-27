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


    //PRICING
    //PREMIUM_PRICE = 4
    //BASIC_PRICE = 3
    //NEW       - PREMIUM_PRICE * daysRented
    //            IF bonusPoints >= 25 --> OFFER TO USE BONUS POINTS (YES/NO) --> ...250/
    //REGULAR   - BASIC_PRICE if daysRented <= 3 || BASIC_PRICE + (daysRented - 3) * BASIC_PRICE
    //OLD       - BASIC_PRICE if daysRented <= 5 || BASIC_PRICE + (daysRented - 5) * BASIC_PRICE

    //LATE FEES
    //NEW       - PREMIUM_PRICE * daysExtra
    //REGULAR   - BASIC_PRICE * daysExtra
    //OLD       - BASIC_PRICE * daysExtra

    //BONUS POINTS
    //NEW       - 2 points per rental
    //REGULAR   - 1 point per rental
    //OLD       - 1 point per rental

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