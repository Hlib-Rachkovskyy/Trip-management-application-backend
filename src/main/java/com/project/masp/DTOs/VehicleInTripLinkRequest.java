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
public class VehicleInTripLinkRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private String startPoint;
}
