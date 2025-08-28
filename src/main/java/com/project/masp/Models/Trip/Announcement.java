package com.project.masp.Models.Trip;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.project.masp.Models.Users.TripManager;
import com.project.masp.Views;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonView({Views.UserView.class,
        Views.TripAnnouncementsView.class})

public class Announcement {
    private LocalDate announcementDate;
    private String content;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    @JsonBackReference
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "tripManager_id")
    @JsonView({Views.UserView.class})
    private TripManager tripManager;
}
