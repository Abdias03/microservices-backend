package com.marketplace.booking.dto;

import com.marketplace.booking.entity.BookingStatus;
import java.time.Instant;

public class BookingResponse {
    private Long id;
    private Long serviceId;
    private Long userId;
    private Instant bookedAt;
    private BookingStatus status;

    public BookingResponse() {}

    public BookingResponse(Long id, Long serviceId, Long userId, Instant bookedAt, BookingStatus status) {
        this.id = id;
        this.serviceId = serviceId;
        this.userId = userId;
        this.bookedAt = bookedAt;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public Long getUserId() {
        return userId;
    }

    public Instant getBookedAt() {
        return bookedAt;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setBookedAt(Instant bookedAt) {
        this.bookedAt = bookedAt;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}
