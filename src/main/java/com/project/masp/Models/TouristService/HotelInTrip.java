package com.project.masp.Models.ManyToMany;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class HotelInTrip {
    private LocalDate hotelStartDate;
    private LocalDate hotelEndDate;


    public long getDaysInHotel(){
        return ChronoUnit.DAYS.between(hotelStartDate, hotelEndDate);
    };
}
