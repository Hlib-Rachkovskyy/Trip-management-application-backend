package com.project.masp.Models.Users;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.project.masp.Models.Trip.Announcement;
import com.project.masp.Models.Trip.Trip;
import com.project.masp.Views;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "Employee_id")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@JsonView({Views.UserView.class, Views.ManagerView.class, Views.ManagerTripsView.class})
public class TripManager extends Employee {


    @OneToMany(mappedBy = "tripManager")
    @Builder.Default
    @JsonView({Views.ManagerAnnouncementsView.class})
    private List<Announcement> announcements = new ArrayList<>();

    @ManyToMany(mappedBy = "tripManager")
    @Builder.Default
    @JsonView({Views.ManagerTripsView.class})
    private List<Trip> trips = new ArrayList<>();
}
