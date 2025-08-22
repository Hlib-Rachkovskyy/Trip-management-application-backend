package com.project.masp.Models.Company;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.masp.Models.Trip.Trip;
import com.project.masp.Models.Users.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Company {
    @Column(name = "Name", length = 100)
    private String name;
    @Column(name = "CompanyEmail", length = 200)
    private String companyEmail;
    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Company(String name, String companyEmail, String description) {
        this.name = name;
        this.companyEmail = companyEmail;
        this.description = description;
    }

    @OneToMany(mappedBy = "company")
    @Builder.Default
    @JsonIgnore
    private List<Trip> trips = new ArrayList<>();
    @OneToMany(mappedBy = "company")
    @Builder.Default
    @JsonManagedReference
    private List<ContactForm> contactForms = new ArrayList<>();
    @Getter
    @OneToMany(mappedBy = "company")
    @Builder.Default
    @JsonBackReference
    private List<Employee> employee = new ArrayList<>();

}
