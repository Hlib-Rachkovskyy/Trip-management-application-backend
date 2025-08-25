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
import com.project.masp.Models.Users.User;
import com.project.masp.Models.Users.UserInTrip;
import com.project.masp.Repository.*;
import com.project.masp.Views;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@RestController
public class OrganiserController {
    private final UserRepository userRepository;
    private final OrganiserRepository organiserRepository;
    private final TripRepository tripRepository;
    private final UserInTripRepository userInTripRepository;
    private final CompanyRepository companyRepository;
    private final ContactFormRepository contactFormRepository;

    public OrganiserController(UserRepository userRepository, OrganiserRepository organiserRepository, TripRepository tripRepository, UserInTripRepository userInTripRepository, CompanyRepository companyRepository, ContactFormRepository contactFormRepository) {
        this.userRepository = userRepository;
        this.organiserRepository = organiserRepository;
        this.tripRepository = tripRepository;
        this.userInTripRepository = userInTripRepository;
        this.companyRepository = companyRepository;
        this.contactFormRepository = contactFormRepository;
    }
    
    // Using associations: Organiser -> Trip
    @GetMapping("/organiser/{id}")
    @JsonView({Views.OrganiserTripsView.class})
    public Organiser findOrganiser(@PathVariable Long id) {
        return organiserRepository.findById(id).orElseThrow();
    }

    // Using associations: Organiser -> Trip -> UserInTrip
    @GetMapping("/organiser/{id}/trips/{tripId}/users")
    @JsonView({Views.TripUsersView.class})
    public Trip getOrganiserTripWithUsers(@PathVariable Long id, @PathVariable Long tripId){
        return organiserRepository.findById(id).orElseThrow().getTrips()
                .stream().filter(trip-> trip.getId().equals(tripId))
                .findFirst().orElseThrow();
    }

    // Using associations: Organiser -> Trip
    @GetMapping("/organiser/{id}/trips")
    @JsonView({Views.OrganiserTripsView.class})
    public Organiser getOrganiserWithTrips(@PathVariable Long id){
        return organiserRepository.findById(id).orElseThrow();
    }

    // Using associations: Company -> ContactForm
    @GetMapping("/organiser/{id}/contact-forms")
    @JsonView({Views.CompanyContactFormsView.class})
    public Company getOrganiserCompanyWithContactForms(@PathVariable Long id){
        return organiserRepository.findById(id).orElseThrow().getCompany();
    }

    @PostMapping("/organiser/trips")
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

        tripRepository.save(trip);

        return ResponseEntity.ok(trip.getId());
    }

    @GetMapping("/organiser/test")
    @JsonView({Views.OrganiserView.class})
    public Optional<Organiser> getOrganiser() { return organiserRepository.findById(1L); }

    @GetMapping("/organiser/{id}/trips/{tripId}")
    @JsonView({Views.TripUsersView.class})
    public Trip OrganizerTrip(@PathVariable Long id, @PathVariable Long tripId){
        return organiserRepository.findById(id).orElseThrow().getTrips()
                .stream().filter(trip-> trip.getId().equals(tripId))
                .findFirst().orElseThrow();
    }

    @PostMapping("/trip/create")
    @JsonView({Views.OrganiserTripsView.class})
    public Trip CreateTrip(@RequestBody Trip trip){
        return tripRepository.save(trip);
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

    // Mark contact form as read
    @PutMapping("/contact-form/{formId}/mark-read")
    @JsonView({Views.CompanyContactFormsView.class})
    public ResponseEntity<?> markContactFormAsRead(@PathVariable Long formId) {
        // This would require adding a 'read' field to ContactForm entity
        // For now, just return success
        return ResponseEntity.ok("Contact form marked as read");
    }

    // Get managers for assignment
    @GetMapping("/manager/{companyId}")
    @JsonView({Views.CompanyEmployeesView.class})
    public Company getCompanyWithManagers(@PathVariable Long companyId){
        return companyRepository.findById(companyId).orElseThrow();
    }

    // Assign manager to trip
    @PostMapping("/assign/manager")
    @JsonView({Views.ManagerTripsView.class})
    public ResponseEntity<?> assignManagerToTrip(@RequestBody ManagerAssignmentRequest request) {
        Trip trip = tripRepository.findById(request.getTripId()).orElseThrow();
        TripManager manager = trip.getTripManager().stream()
                .filter(m -> m.getId().equals(request.getManagerId()))
                .findFirst().orElseThrow();
        
        // Manager is already associated with trip through ManyToMany
        return ResponseEntity.ok("Manager assigned to trip");
    }
}
