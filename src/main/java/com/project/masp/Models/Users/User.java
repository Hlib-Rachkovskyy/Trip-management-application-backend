package com.project.masp.Models.Users;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.project.masp.Models.Company.ContactForm;
import com.project.masp.Views;
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
@Table(name = "users")
@JsonView({Views.UserView.class, Views.ManagerView.class,
        Views.TripUsersView.class,  Views.UserTripsView.class, Views.OrganiserView.class, Views.CompanyContactFormsView.class})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Name", length = 100)
    private String name;
    @Column(name = "Surname", length = 100)
    private String surname;
    @Column(name = "Phone", length = 100)
    private String phoneNumber;
    @Column(name = "Email", length = 100)
    private String email;
    @Column(name = "identificationData", columnDefinition = "TEXT")
    private String identificationData;

    @OneToMany(mappedBy = "user")
    @Builder.Default
    @JsonView({Views.UserContactFormsView.class})
    private List<ContactForm> contactFormList = new ArrayList<>();
    
    @OneToMany(mappedBy = "user")
    @Builder.Default
    @JsonView({Views.UserView.class, Views.UserTripsView.class})
    private List<UserInTrip> userInTripList = new ArrayList<>();
}
