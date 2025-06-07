package com.project.masp.Models.Trip;

import com.project.masp.Models.Enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
@Entity
public class UserInTrip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Role role;
    private LocalDate registerDate;
    private int registrationOrder;
}
