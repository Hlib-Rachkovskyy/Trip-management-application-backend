package com.project.masp.Repository;

import com.project.masp.Models.Trip.Trip;
import com.project.masp.Models.Users.User;
import com.project.masp.Models.Users.UserInTrip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInTripRepository extends JpaRepository<UserInTrip, Long> {
    boolean existsByUserAndTrip(User user, Trip trip);

    UserInTrip findByTripIdAndUserId(Long tripId, Long userId);
    void delete(UserInTrip userInTrip);
}
