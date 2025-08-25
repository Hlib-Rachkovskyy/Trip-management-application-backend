package com.project.masp.DTOs;

import com.project.masp.Models.Enums.Activity;
import lombok.Data;

import java.util.List;

@Data
public class VehicleCreateRequest {
    private String name;
    private List<String> phoneNumbers;
    private Activity state;
    private Integer organiserId;
    private String vehicleType;
    private String driverCompany;
}
