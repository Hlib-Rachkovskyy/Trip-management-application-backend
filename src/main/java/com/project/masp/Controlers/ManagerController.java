package com.project.masp.Controlers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public class OrganiserController {
    @GetMapping("/trips/organiser/{id}")
    public String OrganiserTrips(@PathVariable Long id){
        return "User Trips " + id;
    }
    @PostMapping("/announcements/trips/{id}/")
    public String WriteAnnouncement(@PathVariable Long id){
        return "User Trips " + id;
    }


    @PostMapping("/tourist_service/organiser/{id}/{option}")
    public String TouristServiceManagment(@PathVariable Long id, @PathVariable String option){
        return "User Trips " + id;
    }

}
