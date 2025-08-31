package com.project.masp.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelInTripLinkRequest {
    private LocalDate hotelStartDate;
    private LocalDate hotelEndDate;
}

