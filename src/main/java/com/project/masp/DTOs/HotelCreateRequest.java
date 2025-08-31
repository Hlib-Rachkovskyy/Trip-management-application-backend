package com.project.masp.DTOs;

import com.project.masp.Models.Enums.Activity;
import com.project.masp.Models.TouristService.HotelInTrip;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class HotelCreateRequest {
    private String name;
    private List<String> phoneNumbers;
    private Activity state;
    private Integer organiserId;
    private String hotelWebsite;
    private String hotelAddress;
    private String hotelEmail;
    private List<HotelInTrip> hotelInTrips;
    private LocalDate startDate;
    private LocalDate endDate;
}
