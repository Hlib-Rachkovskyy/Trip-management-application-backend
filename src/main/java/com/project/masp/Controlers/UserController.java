package com.project.masp.Controlers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public class TripsController {

    @GetMapping("/usertrip/{id}")
    public String UserTrips(@PathVariable Long id){
        return "User Trips " + id;
    }

    @PostMapping("/usertrip/resign/{tripId}")
    public String Apply(@PathVariable Long tripId){
        return "User Trips " + tripId;
    }
    @PostMapping("/writeform/{companyId}")
    public String Form(@PathVariable String companyId){
        return "User Trips";
    }

    @PostMapping("/usertrip/apply/{tripId}")
    public String Resign(@PathVariable Long tripId){
        return "User Trips " + tripId;
    }

    @GetMapping("/usertrip/announcements/{tripId}")
    public String Announcements(@PathVariable Long tripId){
        return "User Trips";
    }

    @GetMapping("/usertrip")
    public String UserTrips(){
        return "User Trips";
    }
}
