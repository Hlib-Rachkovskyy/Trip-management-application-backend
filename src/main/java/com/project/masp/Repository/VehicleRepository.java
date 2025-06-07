package com.project.masp.Repository;

import com.project.masp.Models.TouristService.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
