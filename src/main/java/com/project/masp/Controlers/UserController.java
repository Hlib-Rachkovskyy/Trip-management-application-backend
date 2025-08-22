package com.project.masp.Controlers;

import com.project.masp.DTOs.*;
import com.project.masp.Models.Enums.*;
import com.project.masp.Models.Company.*;

import com.project.masp.Models.Users.*;
import com.project.masp.Models.Trip.*;
import com.project.masp.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private final TripRepository tripRepository;
    private final UserInTripRepository userInTripRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final ContactFormRepository contactFormRepository;

    @Autowired
    public UserController(UserRepository userRepository, TripRepository tripRepository, UserInTripRepository userInTripRepository, CompanyRepository companyRepository, ContactFormRepository contactFormRepository) {
        this.userRepository = userRepository;
        this.tripRepository = tripRepository;
        this.userInTripRepository = userInTripRepository;
        this.companyRepository = companyRepository;
        this.contactFormRepository = contactFormRepository;
    }

    @PostMapping("/contact-form/submit")
    public void submitContactForm(@RequestBody ContactFormDTO contactFormDTO) {
        System.out.println(contactFormDTO);
        var user = userRepository.getReferenceById(1);
        var company = companyRepository.getReferenceById(contactFormDTO.getCompanyId());
        var sendDate = Instant.ofEpochMilli(contactFormDTO.getSendDate()).atZone(ZoneId.of("UTC")).toLocalDate();

        ContactForm contactForm = ContactForm.builder()
                .user(user)
                .company(company)
                .sendDate(sendDate)
                .text(contactFormDTO.getText())
                .build();
        contactFormRepository.save(contactForm);
    }

    @GetMapping("/companies")
    public List<Company> getCompaniesNames(){
        return companyRepository.findAll();
    }

    @GetMapping("/user/{id}/trips")
    public List<Trip> UserTrips(@PathVariable Integer id){
        return userRepository.findById(id).orElseThrow().getUserInTripList().stream().map(UserInTrip::getTrip).collect(Collectors.toList());
    }

    @GetMapping("/user/{id}/explore")
    public List<Trip> UserTripsExplore(@PathVariable Integer id){
        var userInTrip = userRepository.findById(id).orElseThrow().getUserInTripList();
        return tripRepository.findTripsNotInUserInTrips(userInTrip);
    }

    @PostMapping("/user-trip/apply")
    public ResponseEntity<?> applyToTrip(@RequestBody TripApplicationRequest request) {

        User user = userRepository.findById(request.getUserId());
        System.out.println(request.getUserId());
        Trip trip = tripRepository.findById(request.getTripId());


        boolean alreadyExists = userInTripRepository.existsByUserAndTrip(user, trip);
        if (alreadyExists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User is already registered to this trip.");
        }

        UserInTrip userInTrip = UserInTrip.builder()

                .trip(trip)
                .role(Role.Registered)
                .registerDate(LocalDate.now())
                .registrationOrder(trip.getUsers().size() + 1)
                .user(user)
                .build();

        userInTrip.setUser(user);


        user.getUserInTripList().add(userInTrip);
        trip.getUsers().add(userInTrip);


        userInTripRepository.save(userInTrip);
        tripRepository.save(trip);
        userRepository.save(user);

        return ResponseEntity.ok(userInTrip);
    }

    @DeleteMapping("/user-trip/{tripId}/resign/{userId}")
    public ResponseEntity<?> resignFromTrip(@PathVariable Long tripId, @PathVariable Long userId) {
        UserInTrip userInTrip = userInTripRepository.findByTripIdAndUserId(tripId, userId);
            if (userInTrip == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not registered in this trip.");
            }

            userInTripRepository.delete(userInTrip);
            return ResponseEntity.ok("User successfully resigned from trip.");
        }
/*

    @PostMapping("/submit")
    public ResponseEntity<?> submitForm(@RequestBody ContactFormDTO request) {
        Company company = companyRepository.findByName(request.getCompanyName());
        if (company == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Company not found");
        }

        User user = userRepository.findById(request.getUserId());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        ContactForm form = ContactForm.builder()
                .company(company)
                .user(user)
                .text(request.getText())
                .sendDate(LocalDate.now())
                .build();

        contactFormRepository.save(form);
        return ResponseEntity.ok("Contact form submitted");
    }*/


}
