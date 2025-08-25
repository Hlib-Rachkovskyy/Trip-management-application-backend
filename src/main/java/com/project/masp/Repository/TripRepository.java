package com.project.masp.Repository;

import com.project.masp.Models.Trip.Trip;
import com.project.masp.Models.Users.User;
import com.project.masp.Models.Users.UserInTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {

    @Query("""
    SELECT t FROM Trip t
    WHERE NOT EXISTS (
        SELECT uit FROM UserInTrip uit
        WHERE uit.trip = t AND uit IN :userInTrips
    )
""")
    List<Trip> findTripsNotInUserInTrips(@Param("userInTrips") List<UserInTrip> userInTrips);
}
