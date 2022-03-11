package com.booking.staybooking.exception;

public class InvalidReservationDateException extends RuntimeException {
    public InvalidReservationDateException(String message) {
        super(message);
    }
}

