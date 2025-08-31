package com.project.masp.Controlers;

import com.fasterxml.jackson.annotation.JsonView;
import com.project.masp.DTOs.ManagerAssignmentRequest;
import com.project.masp.DTOs.TripApplicationRequest;
import com.project.masp.DTOs.TripCreateRequest;
import com.project.masp.Models.Company.Company;
import com.project.masp.Models.Enums.RegistrationState;
import com.project.masp.Models.Enums.Role;
import com.project.masp.Models.Trip.Trip;
import com.project.masp.Models.Users.Organiser;
import com.project.masp.Models.Users.TripManager;
import com.project.masp.Models.Users.UserInTrip;
import com.project.masp.Repository.*;
import com.project.masp.Views;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@RestController
public class OrganiserController {
    private final OrganiserRepository organiserRepository;
    private final TripRepository tripRepository;
    private final UserInTripRepository userInTripRepository;
    private final CompanyRepository companyRepository;
    private final TripManagerRepository tripManagerRepository;


    public OrganiserController(UserRepository userRepository, OrganiserRepository organiserRepository, TripRepository tripRepository, UserInTripRepository userInTripRepository, CompanyRepository companyRepository, TripManagerRepository tripManagerRepository) {
        this.organiserRepository = organiserRepository;
        this.tripRepository = tripRepository;
        this.userInTripRepository = userInTripRepository;
        this.companyRepository = companyRepository;
        this.tripManagerRepository = tripManagerRepository;

    }

    @GetMapping("/organiser/{id}")
    @JsonView({Views.OrganiserView.class})
    public Optional<Organiser> getOrganiser(@PathVariable Long id) {
        return organiserRepository.findById(id);
    }


    // Association: Company -> ContactForm and Managers
    @GetMapping("/organiser/company/{id}")
    @JsonView({Views.CompanyContactFormsView.class})
    public Company getOrganiserCompanyWithContactForms(@PathVariable Long id){
        return companyRepository.findById(id).orElseThrow();
    }


    @PostMapping("/organiser/trip")
    @JsonView({Views.OrganiserTripsView.class})
    public ResponseEntity<?> createTrip(@RequestBody TripCreateRequest dto) {
        Company company = companyRepository.findById(dto.getCompanyId()).orElseThrow();
        Organiser organiser = organiserRepository.findById(dto.getOrganiserId()).orElseThrow();

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


        Trip savedTrip = tripRepository.save(trip);

        return ResponseEntity.ok(savedTrip.getId());

    }

    @PutMapping("/organiser/trip/{id}")
    @JsonView({Views.OrganiserTripsView.class})
    public ResponseEntity<?> updateTrip(@PathVariable Long id, @RequestBody TripCreateRequest dto) {
        Trip existingTrip = tripRepository.findById(id).orElseThrow();

        existingTrip.setName(dto.getName());
        existingTrip.setRegistrationDateEnd(dto.getRegistrationDateEnd());
        existingTrip.setDeparturePoint(dto.getDeparturePoint());
        existingTrip.setArrivalPoint(dto.getArrivalPoint());
        existingTrip.setStartDate(dto.getStartDate());
        existingTrip.setEndDate(dto.getEndDate());
        existingTrip.setProgramDescription(dto.getProgramDescription());
        existingTrip.setNumberOfUsersInGroup(dto.getNumberOfUsersInGroup());
        existingTrip.setPrice(dto.getPrice());
        existingTrip.setRegistrationState(RegistrationState.valueOf(dto.getRegistrationState()));

        tripRepository.save(existingTrip);

        return ResponseEntity.ok(existingTrip.getId());
    }


    @PutMapping("/user-trip/assign")
    @JsonView({Views.TripUsersView.class})
    public ResponseEntity<?> assignToTrip(@RequestBody TripApplicationRequest request) {
        try {
            UserInTrip userInTrip = userInTripRepository.findByTripIdAndUserId(request.getTripId(), request.getUserId());
            
            if (userInTrip == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User is not registered for this trip.");
            }
            
            if (userInTrip.getRole() == Role.isPartOfTrip) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User is already assigned to this trip.");
            }
            
            userInTrip.setRole(Role.isPartOfTrip);
            userInTripRepository.save(userInTrip);

            return ResponseEntity.ok(userInTrip);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error assigning user to trip: " + e.getMessage());
        }
    }


    @PostMapping("/assign/manager")
    @JsonView({Views.ManagerTripsView.class})
    public ResponseEntity<?> assignManagerToTrip(@RequestBody ManagerAssignmentRequest request) {
        Trip trip = tripRepository.findById(request.getTripId()).orElseThrow();

        boolean isAlreadyAssigned = trip.getTripManager().stream()
                .anyMatch(m -> m.getId().equals(request.getManagerId()));

        if (isAlreadyAssigned) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("This manager is already assigned to this trip.");
        }

        TripManager manager = tripManagerRepository.findById(request.getManagerId())
                .orElse(null);

        if (manager == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("There's no manager with this id");
        }

        trip.getTripManager().add(manager);
        tripRepository.save(trip);

        return ResponseEntity.ok("Manager assigned to trip successfully");
    }
}
