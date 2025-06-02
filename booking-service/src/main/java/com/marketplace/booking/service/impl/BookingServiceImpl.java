package com.marketplace.booking.service.impl;

import com.marketplace.booking.dto.CreateBookingRequest;
import com.marketplace.booking.entity.Booking;
import com.marketplace.booking.entity.BookingStatus;
import com.marketplace.booking.repository.BookingRepository;
import com.marketplace.booking.service.BookingService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository repo;
    public BookingServiceImpl(BookingRepository repo) { this.repo = repo; }

    @Override
    public Booking create(CreateBookingRequest req) {
        Booking b = new Booking();
        b.setServiceId(req.getServiceId());
        b.setUserId(req.getUserId());
        b.setBookedAt(Instant.now());
        b.setStatus(BookingStatus.PENDING);
        return repo.save(b);
    }

    @Override public List<Booking> findAll()              { return repo.findAll(); }
    @Override public List<Booking> findByUser(Long userId) { return repo.findByUserId(userId); }
    @Override public List<Booking> findByService(Long sId) { return repo.findByServiceId(sId); }

	@Override
	public Booking confirm(Long bookingId) {
		Booking b = repo.findById(bookingId)
	            .orElseThrow(() -> new EntityNotFoundException("Booking no encontrado"));
	        b.setStatus(BookingStatus.CONFIRMED);
	        return repo.save(b);
	}

	@Override
	public Booking cancel(Long bookingId) {
		Booking b = repo.findById(bookingId)
	            .orElseThrow(() -> new EntityNotFoundException("Booking no encontrado"));
	        b.setStatus(BookingStatus.CANCELLED);
	        return repo.save(b);
	}
}
