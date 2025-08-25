package com.project.masp.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagerAssignmentRequest {
    private Long tripId;
    private Long managerId;
}
