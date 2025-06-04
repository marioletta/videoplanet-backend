package ee.mario.videoplanetbackend.controller;

import ee.mario.videoplanetbackend.entity.Customer;
import ee.mario.videoplanetbackend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("customers")
    public ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.ok().body(customerRepository.findAll());
    }

    @PostMapping("customers")
    public Customer addCustomer(@RequestBody Customer customer) {
        if (customer.getId() != null) {
            throw new RuntimeException("Cannot add with ID!");
        }
        return customerRepository.save(customer);
    }

    @DeleteMapping("customers/{id}")
    public ResponseEntity<List<Customer>> deleteCustomer(@PathVariable Long id) {
        customerRepository.deleteById(id);
        return ResponseEntity.ok().body(customerRepository.findAll());
    }

    @PutMapping("customers")
    public ResponseEntity<Customer> editCustomer(@RequestBody Customer customer) {
        if (customer.getId() == null) {
            throw new RuntimeException("Cannot edit without ID!");
        }
        customerRepository.save(customer);
        return ResponseEntity.ok().body(customerRepository.findById(customer.getId()).orElseThrow());
    }

}
