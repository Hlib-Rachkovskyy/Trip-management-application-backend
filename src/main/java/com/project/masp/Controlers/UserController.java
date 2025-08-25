package com.project.masp.Controlers;

import com.fasterxml.jackson.annotation.JsonView;
import com.project.masp.DTOs.*;
import com.project.masp.Models.Enums.*;
import com.project.masp.Models.Company.*;

import com.project.masp.Models.Users.*;
import com.project.masp.Models.Trip.*;
import com.project.masp.Repository.*;
import com.project.masp.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private final TripRepository tripRepository;
    private final UserInTripRepository userInTripRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final ContactFormRepository contactFormRepository;
    private final AnnouncementRepository announcementRepository;

    @Autowired
    public UserController(UserRepository userRepository, TripRepository tripRepository, UserInTripRepository userInTripRepository, CompanyRepository companyRepository, ContactFormRepository contactFormRepository, AnnouncementRepository announcementRepository) {
        this.userRepository = userRepository;
        this.tripRepository = tripRepository;
        this.userInTripRepository = userInTripRepository;
        this.companyRepository = companyRepository;
        this.contactFormRepository = contactFormRepository;
        this.announcementRepository = announcementRepository;
    }

    @PostMapping("/contact-form/submit")
    @JsonView({Views.UserContactFormsView.class})
    public void submitContactForm(@RequestBody ContactFormDTO contactFormDTO) {
        System.out.println(contactFormDTO);
        var user = userRepository.getReferenceById(1L);
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

    @GetMapping("/user/test")
    @JsonView({Views.UserView.class})
    public Optional<User> getAllUsers() { return userRepository.findById(1L); }

    @GetMapping("/companies")
    @JsonView({Views.UserView.class})
    public List<Company> getCompaniesNames(){
        return companyRepository.findAll();
    }

    // Using associations: User -> UserInTrip -> Trip
    @GetMapping("/user/{id}/trips")
    @JsonView({Views.UserTripsView.class})
    public User getUserWithTrips(@PathVariable Long id){
        return userRepository.findById(id).orElseThrow();
    }

    // Using associations: Get all trips and let frontend filter
    @GetMapping("/trips")
    @JsonView({Views.UserView.class})
    public List<Trip> getAllTrips(){
        return tripRepository.findAll();
    }

    // Using associations: Trip -> Announcement
    @GetMapping("/trip/{tripId}/announcements")
    @JsonView({Views.TripAnnouncementsView.class})
    public Trip getTripWithAnnouncements(@PathVariable Long tripId){
        return tripRepository.findById(tripId).orElseThrow();
    }

    // Using associations: Trip -> TripManager
    @GetMapping("/trip/{tripId}/manager")
    @JsonView({Views.UserView.class})
    public Trip getTripWithManager(@PathVariable Long tripId){
        return tripRepository.findById(tripId).orElseThrow();
    }

    @PostMapping("/user-trip/apply")
    @JsonView({Views.UserTripsView.class})
    public ResponseEntity<?> applyToTrip(@RequestBody TripApplicationRequest request) {

        User user = userRepository.findById(request.getUserId()).orElseThrow();
        System.out.println(request.getUserId());
        Trip trip = tripRepository.findById(request.getTripId()).orElseThrow();

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

    // Create announcement endpoint
    @PostMapping("/announcement/create")
    @JsonView({Views.TripAnnouncementsView.class})
    public ResponseEntity<?> createAnnouncement(@RequestBody AnnouncementCreateRequest request) {
        Trip trip = tripRepository.findById(request.getTripId()).orElseThrow();
        TripManager manager = trip.getTripManager().get(0); // Assuming one manager per trip
        
        Announcement announcement = Announcement.builder()
                .content(request.getContent())
                .announcementDate(LocalDate.now())
                .trip(trip)
                .tripManager(manager)
                .build();
        
        announcementRepository.save(announcement);
        return ResponseEntity.ok(announcement);
    }
}
