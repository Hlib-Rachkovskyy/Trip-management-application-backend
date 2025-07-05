package com.project.masp.DTOs;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TripCreateRequest {
    private String name;
    private LocalDate registrationDateEnd;
    private String departurePoint;
    private String arrivalPoint;
    private LocalDate startDate;
    private LocalDate endDate;
    private String programDescription;
    private int numberOfUsersInGroup;
    private double price;
    private String registrationState;
    private Long companyId;
    private Long organiserId;
}
