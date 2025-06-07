package com.project.masp.Repository;

import com.project.masp.Models.TouristService.HotelInTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface HotelInTripRepository extends JpaRepository<HotelInTrip, Long> {
}
