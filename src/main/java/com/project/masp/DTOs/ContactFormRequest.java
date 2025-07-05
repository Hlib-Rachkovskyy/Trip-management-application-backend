package com.project.masp.DTOs;

import lombok.Data;

@Data
public class ContactFormRequest {
    private String companyName;
    private String text;
    private Long userId;
}
