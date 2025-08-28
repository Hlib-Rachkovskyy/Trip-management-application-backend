package com.project.masp.Models.Company;

import com.fasterxml.jackson.annotation.JsonView;
import com.project.masp.Models.Trip.Trip;
import com.project.masp.Models.Users.Employee;
import com.project.masp.Views;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonView({Views.ManagerView.class, Views.UserView.class, Views.OrganiserView.class, Views.CompanyEmployee.class,
        Views.CompanyTripsView.class, Views.CompanyContactFormsView.class, Views.CompanyManagersView.class})
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

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @JsonView({Views.CompanyTripsView.class})
    private List<Trip> trips = new ArrayList<>();
    
    @OneToMany(mappedBy = "company")
    @Builder.Default
    @JsonView({Views.CompanyContactFormsView.class})
    private List<ContactForm> contactForms = new ArrayList<>();
    
    @Getter
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @JsonView({Views.CompanyContactFormsView.class, Views.CompanyEmployee.class})
    private List<Employee> employee = new ArrayList<>();

}
