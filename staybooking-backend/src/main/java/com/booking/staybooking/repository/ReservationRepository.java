package com.booking.staybooking.repository;

import com.booking.staybooking.entity.Reservation;
import com.booking.staybooking.entity.Stay;
import com.booking.staybooking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByGuest(User guest);

    List<Reservation> findByStay(Stay stay);

    //auto, delete jpa自动实现
    List<Reservation> findByStayAndCheckoutDateAfter(Stay stay, LocalDate date);
}
