package com.marketplace.booking.controller;

import com.marketplace.booking.dto.CreateBookingRequest;
import com.marketplace.booking.entity.Booking;
import com.marketplace.booking.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService service;
    public BookingController(BookingService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<Booking> create(@RequestBody CreateBookingRequest req) {
        return ResponseEntity.ok(service.create(req));
    }

    @GetMapping
    public ResponseEntity<List<Booking>> all() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> byUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.findByUser(userId));
    }

    @GetMapping("/service/{serviceId}")
    public ResponseEntity<List<Booking>> byService(@PathVariable Long serviceId) {
        return ResponseEntity.ok(service.findByService(serviceId));
    }
    
    @PostMapping("/{id}/confirm")
    public ResponseEntity<Booking> confirm(@PathVariable Long id){
    	Booking updated = service.confirm(id);
    	return ResponseEntity.ok(updated);
    }
    
    @PostMapping("/{id}/cancel")
    public ResponseEntity<Booking> cancel(@PathVariable Long id) {
        Booking updated = service.cancel(id);
        return ResponseEntity.ok(updated);
    }
}
