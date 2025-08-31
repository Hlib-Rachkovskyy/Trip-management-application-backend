package com.project.masp.DTOs;

import com.project.masp.Models.Enums.Activity;
import com.project.masp.Models.TouristService.HotelInTrip;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class VehicleCreateRequest {
    private String name;
    private List<String> phoneNumbers;
    private Activity state;
    private Integer organiserId;
    private String vehicleType;
    private String driverCompany;
    private List<HotelInTrip> hotelInTrips;
    private LocalDate startDate;
    private LocalDate endDate;
    private String startPoint;
}
