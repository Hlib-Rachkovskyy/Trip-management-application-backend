package com.project.masp.Controlers;

import com.fasterxml.jackson.annotation.JsonView;
import com.project.masp.DTOs.*;
import com.project.masp.Models.Enums.Activity;
import com.project.masp.Models.TouristService.Hotel;
import com.project.masp.Models.TouristService.HotelInTrip;
import com.project.masp.Models.TouristService.Vehicle;
import com.project.masp.Models.TouristService.VehicleInTrip;
import com.project.masp.Models.Trip.Trip;
import com.project.masp.Models.Users.Organiser;
import com.project.masp.Repository.*;
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
    private final HotelInTripRepository hotelInTripRepository;
    private final VehicleInTripRepository vehicleInTripRepository;
    private final TripRepository tripRepository;


    public TouristServiceController(HotelRepository hotelRepository, 
                                   VehicleRepository vehicleRepository,
                                   OrganiserRepository organiserRepository,
                                    HotelInTripRepository hotelInTripRepository,
                                    VehicleInTripRepository vehicleInTripRepository,
                                    TripRepository tripRepository) {
        this.hotelRepository = hotelRepository;
        this.vehicleRepository = vehicleRepository;
        this.organiserRepository = organiserRepository;
        this.hotelInTripRepository = hotelInTripRepository;
        this.vehicleInTripRepository = vehicleInTripRepository;
        this.tripRepository = tripRepository;

    }

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

    @PutMapping("/hotel/{id}")
    @JsonView({Views.ManagerView.class})
    public ResponseEntity<?> updateHotel(@PathVariable Long id, @RequestBody Hotel request) {
        return hotelRepository.findById(id)
                .map(hotel -> {
                    hotel.setName(request.getName());
                    hotel.setHotelAddress(request.getHotelAddress());
                    hotel.setHotelWebsite(request.getHotelWebsite());
                    hotel.setHotelEmail(request.getHotelEmail());
                    hotel.setPhoneNumbers(request.getPhoneNumbers());
                    hotelRepository.save(hotel);
                    return ResponseEntity.ok(hotel);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/vehicle/{id}")
    @JsonView({Views.ManagerView.class})
    public ResponseEntity<?> updateVehicle(@PathVariable Long id, @RequestBody Vehicle request) {
        return vehicleRepository.findById(id)
                .map(vehicle -> {
                    vehicle.setName(request.getName());
                    vehicle.setVehicleType(request.getVehicleType());
                    vehicle.setDriverCompany(request.getDriverCompany());
                    vehicle.setPhoneNumbers(request.getPhoneNumbers());
                    vehicleRepository.save(vehicle);
                    return ResponseEntity.ok(vehicle);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/hotel/trip/{id}")
    public ResponseEntity<?> deleteHotelFromTrip(@PathVariable Long id) {
        return hotelInTripRepository.findById(id)
                .map(hotelInTrip -> {
                    hotelInTripRepository.delete(hotelInTrip);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/vehicle/trip/{id}")
    public ResponseEntity<?> deleteVehicleFromTrip(@PathVariable Long id) {
        return vehicleInTripRepository.findById(id)
                .map(vehicleInTrip -> {
                    vehicleInTripRepository.delete(vehicleInTrip);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/hotel/{id}")
    public ResponseEntity<?> deleteHotel(@PathVariable Long id) {
        if (hotelRepository.existsById(id)) {
            hotelRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/vehicle/{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable Long id) {
        if (vehicleRepository.existsById(id)) {
            vehicleRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/hotel")
    @JsonView({Views.OrganiserTouristServicesView.class})
    public ResponseEntity<Long> createHotel(@RequestBody HotelCreateRequest request) {
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
            System.out.println(savedHotel.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedHotel.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/vehicle")
    @JsonView({Views.OrganiserTouristServicesView.class})
    public ResponseEntity<Long> createVehicle(@RequestBody VehicleCreateRequest request) {
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
            return ResponseEntity.status(HttpStatus.CREATED).body(savedVehicle.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/hotel-in-trip/{tripId}/{hotelId}")
    @JsonView({Views.ManagerView.class})
    public ResponseEntity<?> linkHotelToTrip(
            @PathVariable Long tripId,
            @PathVariable Long hotelId,
            @RequestBody HotelInTripLinkRequest request) {

        Optional<Trip> trip = tripRepository.findById(tripId);
        Optional<Hotel> hotel = hotelRepository.findById(hotelId);

        if (trip.isEmpty() || hotel.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Hotel hotelEntity = hotel.get();
        hotelEntity.setState(Activity.Assigned);
        hotelRepository.save(hotelEntity);

        HotelInTrip hotelInTrip = HotelInTrip.builder()
                .trip(trip.get())
                .hotel(hotelEntity)
                .hotelStartDate(request.getHotelStartDate())
                .hotelEndDate(request.getHotelEndDate())
                .build();

        HotelInTrip savedHotelInTrip = hotelInTripRepository.save(hotelInTrip);
        return ResponseEntity.ok(savedHotelInTrip);
    }

    @PostMapping("/vehicle-in-trip/{tripId}/{vehicleId}")
    @JsonView({Views.ManagerView.class})
    public ResponseEntity<?> linkVehicleToTrip(
            @PathVariable Long tripId,
            @PathVariable Long vehicleId,
            @RequestBody VehicleInTripLinkRequest request) {

        Optional<Trip> trip = tripRepository.findById(tripId);
        Optional<Vehicle> vehicle = vehicleRepository.findById(vehicleId);

        if (trip.isEmpty() || vehicle.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Vehicle vehicleEntity = vehicle.get();
        vehicleEntity.setState(Activity.Assigned);
        vehicleRepository.save(vehicleEntity);

        VehicleInTrip vehicleInTrip = VehicleInTrip.builder()
                .trip(trip.get())
                .vehicle(vehicleEntity)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .startPoint(request.getStartPoint())
                .build();

        VehicleInTrip savedVehicleInTrip = vehicleInTripRepository.save(vehicleInTrip);
        return ResponseEntity.ok(savedVehicleInTrip);
    }
}
