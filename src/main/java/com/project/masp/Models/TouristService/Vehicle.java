package com.project.masp.Models.TouristService;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
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
@PrimaryKeyJoinColumn(name = "TouristServices_id")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@JsonView({Views.UserView.class, Views.OrganiserView.class, Views.OrganiserTouristServicesView.class, Views.ManagerView.class})
public class Vehicle extends TouristServices {
    private String vehicleType;
    private String driverCompany;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @JsonIgnore
    private List<VehicleInTrip> vehiclesInTrip = new ArrayList<>();


}
