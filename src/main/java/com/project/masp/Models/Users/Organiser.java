package com.project.masp.Models.Users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.masp.Models.TouristService.TouristServices;
import com.project.masp.Models.Trip.Trip;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "Employee_id")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class Organiser extends Employee {


    private List<Trip> getListOfTripsThatAreCurrentlyActive(){
        return trips.stream().filter(a -> a.getRegistrationDateEnd().isAfter(LocalDate.now())).toList();
    }

    @OneToMany(mappedBy = "organiser")
    @Builder.Default
    @JsonManagedReference
    private List<Trip> trips = new ArrayList<>();

    @OneToMany(mappedBy = "organiser")
    @Builder.Default
    @JsonIgnore
    private List<TouristServices> touristServices = new ArrayList<>();
}
