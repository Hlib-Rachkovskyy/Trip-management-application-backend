package com.project.masp.Controlers;

import com.project.masp.DTOs.TripApplicationRequest;
import com.project.masp.DTOs.TripCreateRequest;
import com.project.masp.Models.Company.Company;
import com.project.masp.Models.Enums.RegistrationState;
import com.project.masp.Models.Enums.Role;
import com.project.masp.Models.Trip.Trip;
import com.project.masp.Models.Users.Organiser;
import com.project.masp.Models.Users.UserInTrip;
import com.project.masp.Repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrganiserController {
    private final UserRepository userRepository;
    private final OrganiserRepository organiserRepository;
    private final TripRepository tripRepository;
    private final UserInTripRepository userInTripRepository;
    private final CompanyRepository companyRepository;

    public OrganiserController(UserRepository userRepository, OrganiserRepository organiserRepository, TripRepository tripRepository, UserInTripRepository userInTripRepository, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.organiserRepository = organiserRepository;
        this.tripRepository = tripRepository;
        this.userInTripRepository = userInTripRepository;
        this.companyRepository = companyRepository;
    }
    @GetMapping("/organiser/{id}")
    public Organiser findOrganiser(@PathVariable Long id) {
        return organiserRepository.findById(id);
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

    @PostMapping("/organiser/trips")
    public ResponseEntity<?> createTrip(@RequestBody TripCreateRequest dto) {
        Company company = companyRepository.findById(dto.getCompanyId()).orElseThrow();
        Organiser organiser = organiserRepository.findById(dto.getOrganiserId());

        Trip trip = Trip.builder()
                .name(dto.getName())
                .registrationDateEnd(dto.getRegistrationDateEnd())
                .departurePoint(dto.getDeparturePoint())
                .arrivalPoint(dto.getArrivalPoint())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .programDescription(dto.getProgramDescription())
                .numberOfUsersInGroup(dto.getNumberOfUsersInGroup())
                .price(dto.getPrice())
                .registrationState(RegistrationState.valueOf(dto.getRegistrationState()))
                .company(company)
                .organiser(organiser)
                .build();

        tripRepository.save(trip);

        return ResponseEntity.ok(trip.getId());
    }


    @GetMapping("/organiser/{id}/trips/{tripId}")
    public Trip OrganizerTrip(@PathVariable Integer id, @PathVariable Long tripId){
        return organiserRepository.findById(id).orElseThrow().getTrips()
                .stream().filter(trip-> trip.getId().equals(tripId))
                .findFirst().orElseThrow();
    }

    @PostMapping("/trip/create")
    public Trip CreateTrip(@RequestBody Trip trip){
        return tripRepository.save(trip);
    }

    @PutMapping("/user-trip/assign")
    public ResponseEntity<?> assignToTrip(@RequestBody TripApplicationRequest request) {

        UserInTrip userInTrip = userInTripRepository.findByTripIdAndUserId(request.getTripId(), request.getUserId());
        userInTrip.setRole(Role.isPartOfTrip);

        userInTripRepository.save(userInTrip);

        return ResponseEntity.ok(userInTrip);
    }


}
