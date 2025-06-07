package com.project.masp.Models.Users;

import com.project.masp.Models.TouristService.TouristServices;
import com.project.masp.Models.Trip.Trip;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Entity
@PrimaryKeyJoinColumn(name = "Employee_id")
@NoArgsConstructor
public class Organizer extends Employee {


    private List<Trip> getListOfTripsThatAreCurrentlyActive(){
        return trips.stream().filter(a -> a.getRegistrationDateEnd().isAfter(LocalDate.now())).toList();
    }

    @OneToMany(mappedBy = "organizer")
    private List<Trip> trips;

    @OneToMany(mappedBy = "organizer")
    private List<TouristServices> touristServices;
}
