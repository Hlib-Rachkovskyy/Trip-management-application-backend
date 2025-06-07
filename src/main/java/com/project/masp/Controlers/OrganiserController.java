package com.project.masp.Controlers;

import com.project.masp.Models.Trip.Trip;
import com.project.masp.Models.Users.User;
import com.project.masp.Models.Users.UserInTrip;
import com.project.masp.Repository.OrganiserRepository;
import com.project.masp.Repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrganiserController {
    private final UserRepository userRepository;
    private final OrganiserRepository organiserRepository;

    public OrganiserController(UserRepository userRepository, OrganiserRepository organiserRepository) {
        this.userRepository = userRepository;
        this.organiserRepository = organiserRepository;
    }

    @GetMapping("/organiser/{id}/trips/{tripId}/users")
    public List<UserInTrip> OrganiserTrips(@PathVariable Integer id, @PathVariable Long tripId){
        return organiserRepository.findById(id).orElseThrow().getTrips()
                .stream().filter(trip-> trip.getId().equals(tripId))
                .findFirst().orElseThrow().getUsers();
    }

    @GetMapping("/organiser/{id}/trips")
    public List<Trip> OrganiserTripsTrip(@PathVariable Integer id){
        return organiserRepository.findById(id).orElseThrow().getTrips();
    }

    @GetMapping("/organiser/{id}/trips/{tripId}")
    public Trip OrganizerTrip(@PathVariable Integer id, @PathVariable Long tripId){
        return organiserRepository.findById(id).orElseThrow().getTrips()
                .stream().filter(trip-> trip.getId().equals(tripId))
                .findFirst().orElseThrow();
    }








    @GetMapping("/company/forms")
    public String OrganiserCompanyForms(@RequestParam Long id){
        return "User Company Forms " + id;
    }

    @PostMapping("/trip/aprove/{userId}")
    public String OrganiserApprove(@PathVariable Long userId){
        return "User Approve " + userId;
    }

    @PostMapping("/trip/create")
    public String CreateTrip(){
        return "Trip created";
    }

    @DeleteMapping("/trip/delete/{tripId}")
    public String DelteTrip(@RequestParam Long tripId){
        return "User Create " + tripId;
    }

    @GetMapping("/company/managers/{companyId}")
    public String OrganiserManagers(@RequestParam Long companyId){
        return "User Managers " + companyId;
    }

    @PostMapping("/trip/create/trip={tripId}/{managerId}")
    public String AssignManager(@RequestParam Long tripID, @RequestParam String managerId){
        return "User Assign Manager " + tripID;
    }

    @PostMapping("/trip/edit/{tripId}")
    public String EditTrip(@RequestParam Long tripID){
        return "User Assign Manager " + tripID;
    }

    @GetMapping("/tourist-services/{companyId}")
    public String TouristServices(@RequestParam Long companyId){
        return "User Assign Manager " + companyId;
    }

    @PostMapping("/tourist-services/")
    public String CreateTouristServices(@RequestParam Long employeeId){
        return "User Assign Manager " + employeeId;
    }

    @DeleteMapping("/tourist-services/{touristServiceId}")
    public String DeleteTouristServices(@RequestParam Long touristServiceId){
        return "User Assign Manager " + touristServiceId;
    }

    @PutMapping("/tourist-services/{touristServiceId}")
    public String EditTouristService(@RequestParam Long touristServiceId){
        return "User Assign Manager " + touristServiceId;
    }

    @DeleteMapping("/trip={tripId}/tourist-services={touristServiceId}")
    public String DeleteTouristServiceFromTrip(@RequestParam Long touristServiceId, @RequestParam Long tripId){
        return "User Assign Manager " + touristServiceId;
    }

    @PostMapping("/trip={tripId}/tourist-services={touristServiceId}")
    public String AddTouristServiceToTrip(@RequestParam Long touristServiceId, @RequestParam Long tripId){
        return "User Assign Manager " + touristServiceId;
    }
}
