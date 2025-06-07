package com.project.masp.Models.TouristService;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Hotel extends TouristServices {
    private String hotelWebsite;
    private String hotelAddress;
    private String hotelEmail;


    @OneToMany(mappedBy = "hotel")
    @Builder.Default
    @JsonBackReference
    List<HotelInTrip> hotelInTripList = new ArrayList<>();
}
