package ee.mario.videoplanetbackend.controller;

import ee.mario.videoplanetbackend.dto.RentalOrderRequest;
import ee.mario.videoplanetbackend.dto.RentalOrderResponse;
import ee.mario.videoplanetbackend.dto.RentalReturnResponse;
import ee.mario.videoplanetbackend.service.RentalOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RentalOrderController {

    @Autowired
    RentalOrderService rentalOrderService;

    @PostMapping("rental-orders")
    public ResponseEntity<RentalOrderResponse> createRentalOrder(@RequestBody RentalOrderRequest request) {
        return ResponseEntity.ok(rentalOrderService.createOrder(request));
    }

    @GetMapping("rental-orders/{id}")
    public ResponseEntity<RentalOrderResponse> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(rentalOrderService.getOrderById(id));
    }

    @PutMapping("/return/{orderId}")
    public ResponseEntity<List<RentalReturnResponse>> returnRental(@PathVariable Long orderId) {
        return ResponseEntity.ok(rentalOrderService.handleReturn(orderId));
    }


}
