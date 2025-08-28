package com.project.masp.Models.TouristService;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.project.masp.Models.Trip.Trip;
import com.project.masp.Views;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Collection;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonView({Views.OrganiserView.class, Views.ManagerView.class})
public class VehicleInTrip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;
    private String startPoint;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    @JsonView({Views.OrganiserView.class, Views.ManagerView.class})
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    @JsonIgnore
    private Trip trip;

}
