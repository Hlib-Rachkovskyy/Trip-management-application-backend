package com.project.masp.Models;

import com.project.masp.Models.Enums.RegistrationState;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Trip {
    private String name;
    private LocalDate registrationDateEnd;
    private String departurePoint;
    private String arrivalPoint;
    private LocalDate startDate;
    private LocalDate endDate;
    private String programDescription;
    private int numberOfUsersInGroup;
    private double price;
    private List<String> images;
    private RegistrationState registrationState;

    public Trip() {

    }

    public Trip(LocalDate startDate, String name, LocalDate onBoardDateEnd, String departurePoint, String arrivalPoint, LocalDate endDate, String programDescription, int numberOfUsersInGroup, double price, ArrayList<String> images, RegistrationState registrationState) {
        this.startDate = startDate;
        this.name = name;
        this.registrationDateEnd = onBoardDateEnd;
        this.departurePoint = departurePoint;
        this.arrivalPoint = arrivalPoint;
        this.endDate = endDate;
        this.programDescription = programDescription;
        this.numberOfUsersInGroup = numberOfUsersInGroup;
        this.price = price;
        this.images = images;
        this.registrationState = registrationState;
    }
}
