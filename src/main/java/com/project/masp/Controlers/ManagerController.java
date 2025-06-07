package com.project.masp.Controlers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManagerController {
    @GetMapping("/manager/{id}/trips")
    public String ManagerTrips(@PathVariable Long id){
        return "User Trips " + id;
    }
    @PostMapping("/manager/{id}/trips/announcements/")
    public String WriteAnnouncement(@PathVariable Long id) {
        return "User Trips " + id;
    }

    @PostMapping("/manager/{id}/trips/{tripId}/tourist_service/{touristServiceId}/option/{option}")
    public String TouristServiceManagement(@PathVariable Long id, @PathVariable Long tripId, @PathVariable Long touristServiceId, @PathVariable String option){
        return "User Trips " + tripId;
    }

}
