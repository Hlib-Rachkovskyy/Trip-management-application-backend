package com.project.masp.Models.Users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.project.masp.Models.Company.Company;
import com.project.masp.Views;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@NoArgsConstructor
@Data
@SuperBuilder
@JsonView({Views.CompanyEmployee.class, Views.CompanyManagersView.class,
        Views.UserView.class, Views.OrganiserView.class, Views.ManagerView.class})
public abstract class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private String country;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonView({Views.OrganiserView.class, Views.ManagerView.class})
    private Company company;
}
