package com.project.masp.Models.Users;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.masp.Models.Company.ContactForm;
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
    @JsonBackReference
    private List<ContactForm> contactFormList = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    @Builder.Default
    @JsonBackReference
    private List<UserInTrip> userInTripList = new ArrayList<>();
}
