package com.project.masp.Repository;

import com.project.masp.Models.Users.TripManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface TripManagerRepository extends JpaRepository<TripManager, Long> {
}
