package com.project.masp.Models.Company;

import com.project.masp.Models.Trip.Trip;
import com.project.masp.Models.Users.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<Trip> trips = new ArrayList<>();
    @OneToMany(mappedBy = "company")
    @Builder.Default
    private List<ContactForm> contactForms = new ArrayList<>();
    @OneToMany(mappedBy = "company")
    @Builder.Default
    private List<Employee> employee = new ArrayList<>();

    public List<Employee> getEmployee() {
        return employee;
    }
}
