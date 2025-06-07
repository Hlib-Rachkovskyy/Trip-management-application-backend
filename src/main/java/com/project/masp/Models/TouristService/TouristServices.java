package com.project.masp.Models.TouristService;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.masp.Models.Enums.Activity;
import com.project.masp.Models.Users.Organiser;
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
    @JsonIgnore
    private Organiser organiser;
}
