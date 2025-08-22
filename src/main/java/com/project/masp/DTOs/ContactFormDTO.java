package com.project.masp.DTOs;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Data
public class ContactFormDTO {
    private String text;
    private Long userId;
    private Long companyId;
    private Long sendDate;


}
