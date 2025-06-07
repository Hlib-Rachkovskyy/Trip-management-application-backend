package com.project.masp.Controlers;

import com.project.masp.Models.Enums.Role;
import com.project.masp.Models.Trip.Trip;
import com.project.masp.Models.Users.User;
import com.project.masp.Models.Users.UserInTrip;
import com.project.masp.Repository.TripRepository;
import com.project.masp.Repository.UserInTripRepository;
import com.project.masp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private final TripRepository tripRepository;
    private final UserInTripRepository userInTripRepository;
    UserRepository userRepository;
    @Autowired
    public UserController(UserRepository userRepository, TripRepository tripRepository, UserInTripRepository userInTripRepository) {
        this.userRepository = userRepository;
        this.tripRepository = tripRepository;
        this.userInTripRepository = userInTripRepository;
    }

    @GetMapping("/user/{id}/trips")
    public List<Trip> UserTrips(@PathVariable Integer id){
        return userRepository.findById(id).orElseThrow().getUserInTripList().stream().map(UserInTrip::getTrip).collect(Collectors.toList());
    }

    @PostMapping("/user-trip/{tripId}/apply/")
    public String Apply(@PathVariable Integer tripId){
        User user =null; //userRepository.findById(userId).orElseThrow();
        Trip trip = tripRepository.findById(tripId).orElseThrow();
        var userInTrip = UserInTrip.builder()
                .user(user)
                .trip(trip)
                .role(Role.Registered)
                .registerDate(LocalDate.now())
                .registrationOrder(2).
                build();
        user.getUserInTripList().add(userInTrip);
        trip.getUsers().add(userInTrip);

        tripRepository.save(trip);
        userRepository.save(user);
        userInTripRepository.save(userInTrip);
        return null;
    }

}
