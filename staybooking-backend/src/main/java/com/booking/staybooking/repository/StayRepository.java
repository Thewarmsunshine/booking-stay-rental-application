package com.booking.staybooking.repository;

import com.booking.staybooking.entity.Stay;
import com.booking.staybooking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StayRepository extends JpaRepository<Stay, Long> {
    //符合JPA的命名规范，自动实现
    List<Stay> findByHost(User user);

    //也不需要自己实现
    List<Stay> findByIdInAndGuestNumberGreaterThanEqual(List<Long> ids, int guestNumber);

}
