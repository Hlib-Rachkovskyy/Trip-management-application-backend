package com.project.masp.Controlers;

import com.fasterxml.jackson.annotation.JsonView;
import com.project.masp.DTOs.HotelCreateRequest;
import com.project.masp.DTOs.StatusUpdateRequest;
import com.project.masp.DTOs.VehicleCreateRequest;
import com.project.masp.Models.Enums.Activity;
import com.project.masp.Models.TouristService.Hotel;
import com.project.masp.Models.TouristService.Vehicle;
import com.project.masp.Models.Users.Organiser;
import com.project.masp.Repository.HotelRepository;
import com.project.masp.Repository.OrganiserRepository;
import com.project.masp.Repository.VehicleRepository;
import com.project.masp.Views;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TouristServiceController {
    
    private final HotelRepository hotelRepository;
    private final VehicleRepository vehicleRepository;
    private final OrganiserRepository organiserRepository;

    public TouristServiceController(HotelRepository hotelRepository, 
                                   VehicleRepository vehicleRepository,
                                   OrganiserRepository organiserRepository) {
        this.hotelRepository = hotelRepository;
        this.vehicleRepository = vehicleRepository;
        this.organiserRepository = organiserRepository;
    }

    @PostMapping("/hotels")
    @JsonView({Views.OrganiserTouristServicesView.class})
    public ResponseEntity<Hotel> createHotel(@RequestBody HotelCreateRequest request) {
        try {
            Optional<Organiser> organiser = organiserRepository.findById(Long.valueOf(request.getOrganiserId()));
            if (organiser.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Hotel hotel = Hotel.builder()
                    .name(request.getName())
                    .phoneNumbers(request.getPhoneNumbers())
                    .state(request.getState())
                    .organiser(organiser.get())
                    .hotelWebsite(request.getHotelWebsite())
                    .hotelAddress(request.getHotelAddress())
                    .hotelEmail(request.getHotelEmail())
                    .build();

            Hotel savedHotel = hotelRepository.save(hotel);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedHotel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/hotels/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        if (!hotelRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        hotelRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Vehicle endpoints
    @PostMapping("/vehicles")
    @JsonView({Views.OrganiserTouristServicesView.class})
    public ResponseEntity<Vehicle> createVehicle(@RequestBody VehicleCreateRequest request) {
        try {
            Optional<Organiser> organiser = organiserRepository.findById(Long.valueOf(request.getOrganiserId()));
            if (organiser.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Vehicle vehicle = Vehicle.builder()
                    .name(request.getName())
                    .phoneNumbers(request.getPhoneNumbers())
                    .state(request.getState())
                    .organiser(organiser.get())
                    .vehicleType(request.getVehicleType())
                    .driverCompany(request.getDriverCompany())
                    .build();

            Vehicle savedVehicle = vehicleRepository.save(vehicle);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedVehicle);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/vehicles/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        if (!vehicleRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        vehicleRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Status management endpoints
    @PutMapping("/tourist-service/{id}/status")
    @JsonView({Views.ManagerView.class})
    public ResponseEntity<?> updateTouristServiceStatus(@PathVariable Long id, @RequestBody StatusUpdateRequest request) {
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if (hotel.isPresent()) {
            hotel.get().setState(request.getNewStatus());
            hotelRepository.save(hotel.get());
            return ResponseEntity.ok(hotel.get());
        }
        
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);
        if (vehicle.isPresent()) {
            vehicle.get().setState(request.getNewStatus());
            vehicleRepository.save(vehicle.get());
            return ResponseEntity.ok(vehicle.get());
        }
        
        return ResponseEntity.notFound().build();
    }
}
