package com.project.masp.Repository;

import com.project.masp.Models.TouristService.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
