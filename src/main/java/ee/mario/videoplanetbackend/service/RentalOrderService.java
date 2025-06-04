package ee.mario.videoplanetbackend.service;

import ee.mario.videoplanetbackend.dto.RentalOrderRequest;
import ee.mario.videoplanetbackend.dto.RentalOrderResponse;
import ee.mario.videoplanetbackend.dto.RentalRequestItem;
import ee.mario.videoplanetbackend.entity.*;
import ee.mario.videoplanetbackend.repository.CustomerRepository;
import ee.mario.videoplanetbackend.repository.MovieRepository;
import ee.mario.videoplanetbackend.repository.RentalOrderRepository;
import ee.mario.videoplanetbackend.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RentalOrderService {

    @Autowired
    private RentalOrderRepository rentalOrderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RentalRepository rentalRepository;

    //FEES
    //NEW       - PREMIUM_PRICE * daysRented ... IF bonusPoints >= 25 --> USE BONUS POINTS? (TRUE/FALSE)
    //REGULAR   - BASIC_PRICE if daysRented <= 3 || BASIC_PRICE + (daysRented - 3) * BASIC_PRICE
    //OLD       - BASIC_PRICE if daysRented <= 5 || BASIC_PRICE + (daysRented - 5) * BASIC_PRICE
    //LATE FEES
    //NEW       - PREMIUM_PRICE * daysExtra
    //REGULAR   - BASIC_PRICE * daysExtra
    //OLD       - BASIC_PRICE * daysExtra

    private final BigDecimal PREMIUM_PRICE = new BigDecimal("4");
    private final BigDecimal BASIC_PRICE = new BigDecimal("3");

    public RentalOrderResponse createOrder(RentalOrderRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(() -> new RuntimeException("Customer not found"));

        RentalOrder order = new RentalOrder();
        order.setCustomer(customer);

        List<Rental> rentals = new ArrayList<>();
        int bonusPointsUsed = 0;

        for (RentalRequestItem item : request.getRentals()) {
            Movie movie = movieRepository.findById(item.getMovieId()).orElseThrow(() -> new RuntimeException("Movie not found"));

            movie.setRented(true);
            movieRepository.save(movie);

            Rental rental = new Rental();
            rental.setCustomer(customer);
            rental.setMovie(movie);
            rental.setRentalOrder(order);
            rental.setRentedOn(LocalDate.now());
            rental.setDaysRequested(item.getDaysRequested());
            rental.setStatus(RentalStatus.RENTED);

            BigDecimal fee = calculateRentalFee(movie, item.getDaysRequested());

            if (request.isUseBonusPoints() && movie.getType() == MovieType.NEW) {
                int daysRequested = item.getDaysRequested();
                int totalPointsNeeded = 25 * daysRequested;
                int customerPoints = customer.getBonusPoints();

                int daysCovered = Math.min(customerPoints / 25, daysRequested);
                int pointsUsed = daysCovered * 25;
                bonusPointsUsed += pointsUsed;

                if (daysCovered > 0) {
                    rental.setPaidWithBonusPoints(true);
                    rental.setBonusPointsUsed(pointsUsed);
                    customer.setBonusPoints(customerPoints - pointsUsed);
                } else {
                    rental.setPaidWithBonusPoints(false);
                    rental.setBonusPointsUsed(0);
                }

                BigDecimal daysToPay = BigDecimal.valueOf(daysRequested - daysCovered);
                BigDecimal adjustedFee = PREMIUM_PRICE.multiply(daysToPay);
                rental.setRentalFee(adjustedFee);
            } else {
                rental.setRentalFee(fee);
                rental.setPaidWithBonusPoints(false);
            }

            rentals.add(rental);
        }

        order.setRentals(rentals);
        customerRepository.save(customer);
        rentalOrderRepository.save(order);
        rentalRepository.saveAll(rentals);

        printConfirmation(order);

        return new RentalOrderResponse(order, bonusPointsUsed);
    }

    // PRINT RECEIPT
    public void printConfirmation(RentalOrder order) {
        BigDecimal totalFee = BigDecimal.ZERO;

        for (Rental rental : order.getRentals()) {
            Movie movie = rental.getMovie();
            String title = movie.getTitle();
            String type = movie.getType().name();
            int daysRequested = rental.getDaysRequested();
            BigDecimal fee = rental.getRentalFee();
            totalFee = totalFee.add(fee);

            String bonusPaidText = rental.isPaidWithBonusPoints() ? " (Paid with " + rental.getBonusPointsUsed() + " Bonus points)" : "";

            System.out.println(title + " (" + type + ") " + daysRequested + " days " + fee + " EUR" + bonusPaidText);
        }

        System.out.println("Total Price : " + totalFee + " EUR");
        System.out.println("Remaining bonus points: " + order.getCustomer().getBonusPoints());
    }


    public RentalOrderResponse getOrderById(Long id) {
        RentalOrder order = rentalOrderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));

        int totalBonusPointsUsed = order.getRentals().stream()
                .mapToInt(Rental::getBonusPointsUsed)
                .sum();

        return new RentalOrderResponse(order, totalBonusPointsUsed);
    }


    private BigDecimal calculateRentalFee(Movie movie, int days) {
        switch (movie.getType()) {
            case NEW:
                return PREMIUM_PRICE.multiply(BigDecimal.valueOf(days));
            case REGULAR:
                if (days <= 3) {
                    return BASIC_PRICE;
                } else {
                    return BASIC_PRICE.add(BASIC_PRICE.multiply(BigDecimal.valueOf(days - 3)));
                }
            case OLD:
                if (days <= 5) {
                    return BASIC_PRICE;
                } else {
                    return BASIC_PRICE.add(BASIC_PRICE.multiply(BigDecimal.valueOf(days - 5)));
                }
            default:
                return BigDecimal.ZERO;
        }
    }

}