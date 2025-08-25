package com.project.masp.Models.Company;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.project.masp.Models.Users.User;
import com.project.masp.Views;
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
@JsonView({Views.OrganiserView.class, Views.CompanyContactFormsView.class})
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
    @JsonView({Views.OrganiserView.class, Views.CompanyContactFormsView.class})
    private User user;
}
