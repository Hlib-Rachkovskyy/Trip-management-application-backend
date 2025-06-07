package com.project.masp.Models.Users;


import com.project.masp.Models.Trip.Announcement;
import com.project.masp.Models.Trip.Trip;
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
public class TripManager extends Employee {


    @OneToMany(mappedBy = "tripManager")
    @Builder.Default
    private List<Announcement> announcements = new ArrayList<>();

    @ManyToMany(mappedBy = "tripManager")
    @Builder.Default
    private List<Trip> trips = new ArrayList<>();
}
