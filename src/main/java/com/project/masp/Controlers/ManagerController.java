package com.project.masp.Controlers;

import com.fasterxml.jackson.annotation.JsonView;
import com.project.masp.Models.Users.Organiser;
import com.project.masp.Models.Users.TripManager;
import com.project.masp.Repository.TripManagerRepository;
import com.project.masp.Views;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ManagerController {
    private final TripManagerRepository tripManagerRepository;


    public ManagerController(TripManagerRepository tripManagerRepository) {
        this.tripManagerRepository = tripManagerRepository;
    }

    @GetMapping("/manager/test")
    @JsonView({Views.ManagerView.class})
    public Optional<TripManager> tripManager() { return tripManagerRepository.findById(2L); }

    // Using associations: TripManager -> Trip
    @GetMapping("/manager/{id}/trips")
    @JsonView({Views.ManagerTripsView.class})
    public TripManager getManagerWithTrips(@PathVariable Long id) { 
        return tripManagerRepository.findById(id).orElseThrow(); 
    }

    // Using associations: TripManager -> Announcement
    @GetMapping("/manager/{id}/announcements")
    @JsonView({Views.ManagerAnnouncementsView.class})
    public TripManager getManagerWithAnnouncements(@PathVariable Long id) { 
        return tripManagerRepository.findById(id).orElseThrow(); 
    }
}
