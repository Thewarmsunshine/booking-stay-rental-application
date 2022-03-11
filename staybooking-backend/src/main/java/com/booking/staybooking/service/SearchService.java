package com.booking.staybooking.service;

import com.booking.staybooking.entity.Stay;
import com.booking.staybooking.repository.LocationRepository;
import com.booking.staybooking.repository.StayAvailabilityRepository;
import com.booking.staybooking.repository.StayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Service
public class SearchService {
    private StayRepository stayRepository;
    private StayAvailabilityRepository stayAvailabilityRepository;
    private LocationRepository locationRepository;

    @Autowired
    public SearchService(StayRepository stayRepository, StayAvailabilityRepository stayAvailabilityRepository, LocationRepository locationRepository) {
        this.stayRepository = stayRepository;
        this.stayAvailabilityRepository = stayAvailabilityRepository;
        this.locationRepository = locationRepository;
    }

    public List<Stay> search(int guestNumber, LocalDate checkinDate, LocalDate checkoutDate, double lat, double lon, String distance) {
        List<Long> stayIds = locationRepository.searchByDistance(lat, lon, distance);
        long duration = Duration.between(checkinDate.atStartOfDay(), checkoutDate.atStartOfDay()).toDays();

        List<Long> filteredStayIds = stayAvailabilityRepository.findByDateBetweenAndStateIsAvailable(stayIds, checkinDate, checkoutDate.minusDays(1), duration);
        return stayRepository.findByIdInAndGuestNumberGreaterThanEqual(filteredStayIds, guestNumber);
    }

}
