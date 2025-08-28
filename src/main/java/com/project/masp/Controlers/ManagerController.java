package com.project.masp.Controlers;

import com.fasterxml.jackson.annotation.JsonView;
import com.project.masp.DTOs.AnnouncementCreateRequest;
import com.project.masp.Models.Trip.Announcement;
import com.project.masp.Models.Trip.Trip;
import com.project.masp.Models.Users.TripManager;
import com.project.masp.Repository.AnnouncementRepository;
import com.project.masp.Repository.TripManagerRepository;
import com.project.masp.Repository.TripRepository;
import com.project.masp.Views;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
public class ManagerController {
    private final TripManagerRepository tripManagerRepository;
    private final TripRepository tripRepository;
    private final AnnouncementRepository announcementRepository;


    public ManagerController(TripManagerRepository tripManagerRepository, TripRepository tripRepository, AnnouncementRepository announcementRepository) {
        this.tripManagerRepository = tripManagerRepository;
        this.tripRepository = tripRepository;
        this.announcementRepository = announcementRepository;
    }

    @GetMapping("/manager/{id}")
    @JsonView({Views.ManagerView.class})
    public Optional<TripManager> tripManager(@PathVariable Long id) {
        return tripManagerRepository.findById(id); }

    // Association: Trip -> UserInTrip -> User
    @GetMapping("/manager/trip/{id}/users")
    @JsonView({Views.TripUsersView.class})
    public Trip getTripUsers(@PathVariable Long id) {
        return tripRepository.findById(id).orElseThrow();
    }

    // Association: Trip -> Tourist Services
    @GetMapping("/manager/trip/{id}/services")
    @JsonView({Views.TripTouristServicesView.class})
    public Trip getTripTouristServices(@PathVariable Long id) {
        return tripRepository.findById(id).orElseThrow();
    }

    // Association: Manager -> Trips
    @GetMapping("/manager/{id}/trips")
    @JsonView({Views.ManagerTripsView.class})
    public TripManager getManagerTrips(@PathVariable Long id) {
        return tripManagerRepository.findById(id).orElseThrow(); 
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
