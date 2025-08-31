package com.project.masp.Models.TouristService;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.project.masp.Models.Trip.Trip;
import com.project.masp.Views;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonView({Views.UserView.class, Views.OrganiserView.class, Views.ManagerView.class})
public class HotelInTrip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate hotelStartDate;
    private LocalDate hotelEndDate;


    public long getDaysInHotel(){
        return ChronoUnit.DAYS.between(hotelStartDate, hotelEndDate);
    };

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    @JsonView({Views.UserView.class, Views.OrganiserView.class, Views.ManagerView.class})
    private Hotel hotel;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "trip_id")
    @JsonIgnore
    private Trip trip;
}
