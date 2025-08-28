package com.project.masp.Models.TouristService;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.project.masp.Models.Enums.Activity;
import com.project.masp.Models.Users.Organiser;
import com.project.masp.Views;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Data
@SuperBuilder
@JsonView({Views.OrganiserView.class, Views.ManagerView.class})
public abstract class TouristServices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ElementCollection
    @Builder.Default
    private List<String> phoneNumbers = new ArrayList<>();
    private Activity state;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    @JsonView({Views.OrganiserTouristServicesView.class})
    private Organiser organiser;
}
