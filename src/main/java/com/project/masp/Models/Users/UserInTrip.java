package com.project.masp.Models.Users;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.project.masp.Models.Enums.Role;
import com.project.masp.Models.Trip.Trip;
import com.project.masp.Views;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonView({Views.UserView.class, Views.ManagerView.class,
        Views.TripUsersView.class, Views.UserTripsView.class, Views.OrganiserView.class})
public class UserInTrip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Role role;
    private LocalDate registerDate;
    private int registrationOrder;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonView({Views.TripUsersView.class, Views.OrganiserView.class, Views.ManagerView.class})
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "trip_id")
    @JsonView({Views.UserView.class, Views.UserTripsView.class})
    private Trip trip;
}
