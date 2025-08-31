package com.project.masp.Models.Trip;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.project.masp.Models.Company.Company;
import com.project.masp.Models.Enums.RegistrationState;
import com.project.masp.Models.TouristService.HotelInTrip;
import com.project.masp.Models.TouristService.VehicleInTrip;
import com.project.masp.Models.Users.Organiser;
import com.project.masp.Models.Users.TripManager;
import com.project.masp.Models.Users.UserInTrip;
import com.project.masp.Views;
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
@JsonView({Views.UserView.class,
        Views.TripManagersView.class, Views.TripUsersView.class,  Views.UserTripsView.class, Views.OrganiserView.class, Views.OrganiserTripsView.class, Views.ManagerView.class, Views.ManagerTripsView.class})
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
    @JsonView({Views.UserView.class, Views.OrganiserView.class, Views.ManagerView.class})
    private List<HotelInTrip> hotelsInTrip= new ArrayList<>();

    @OneToMany(mappedBy = "trip", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Builder.Default
    @JsonView({Views.UserView.class, Views.OrganiserView.class, Views.ManagerView.class})
    private List<VehicleInTrip> vehiclesInTrip = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonView({Views.UserView.class})
    private Company company;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @JsonView({Views.UserView.class,
            Views.TripAnnouncementsView.class})
    private List<Announcement> announcement = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "trip_managers",
            joinColumns = {@JoinColumn(name = "trip_id")},
    inverseJoinColumns = {@JoinColumn(name = "trip_manager_id")})
    @Builder.Default
    @JsonView({Views.OrganiserView.class, Views.UserView.class})
    private List<TripManager> tripManager = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "organiser_id")
    @JsonView({Views.UserView.class})
    private Organiser organiser;

    @OneToMany(mappedBy = "trip")
    @Builder.Default
    @JsonView({Views.OrganiserView.class, Views.TripUsersView.class, Views.ManagerView.class})
    private List<UserInTrip> users = new ArrayList<>();
}
