package com.marketplace.booking.service;

import com.marketplace.booking.dto.CreateBookingRequest;
import com.marketplace.booking.entity.Booking;
import java.util.List;

public interface BookingService {
    Booking create(CreateBookingRequest req);
    List<Booking> findAll();
    List<Booking> findByUser(Long userId);
    List<Booking> findByService(Long serviceId);
    Booking confirm(Long bookingId);
    Booking cancel(Long bookingId);
}
