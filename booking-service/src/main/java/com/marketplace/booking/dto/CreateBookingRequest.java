package com.marketplace.booking.dto;

public class CreateBookingRequest {
    private Long serviceId;
    private Long userId;
   
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
    
    
}
