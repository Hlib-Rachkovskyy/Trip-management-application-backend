package com.project.masp.Models.Company;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.masp.Models.Users.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ContactForm {
    private String text;
    private LocalDate sendDate;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonBackReference
    private Company company;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore // JsonManagedReference
    private User user;
}
