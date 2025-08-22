package com.project.masp.Models.Trip;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.masp.Models.Company.Company;
import com.project.masp.Models.Enums.RegistrationState;
import com.project.masp.Models.TouristService.HotelInTrip;
import com.project.masp.Models.TouristService.VehicleInTrip;
import com.project.masp.Models.Users.Organiser;
import com.project.masp.Models.Users.TripManager;
import com.project.masp.Models.Users.User;
import com.project.masp.Models.Users.UserInTrip;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Trip {
    private String name;
    private LocalDate registrationDateEnd;
    private String departurePoint;
    private String arrivalPoint;
    private LocalDate startDate;
    private LocalDate endDate;
    private String programDescription;
    private int numberOfUsersInGroup;
    private double price;
    private RegistrationState registrationState;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @OneToMany(mappedBy = "trip")
    @Builder.Default
    @JsonManagedReference
    private List<HotelInTrip> hotelsInTrip= new ArrayList<>();

    @OneToMany(mappedBy = "trip")
    @Builder.Default
    @JsonManagedReference
    private List<VehicleInTrip> vehiclesInTrip = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonIgnore
    private Company company;

    @OneToMany(mappedBy = "trip")
    @Builder.Default
    @JsonManagedReference
    private List<Announcement> announcement = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "trip_managers",
            joinColumns = {@JoinColumn(name = "tripManager_id")},
    inverseJoinColumns = {@JoinColumn(name = "trip_id")})
    @Builder.Default
    @JsonBackReference
    private List<TripManager> tripManager = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "organiser_id")
    @JsonBackReference
    private Organiser organiser;


    @OneToMany(mappedBy = "trip")
    @Builder.Default
    @JsonManagedReference
    private List<UserInTrip> users = new ArrayList<>();
}
