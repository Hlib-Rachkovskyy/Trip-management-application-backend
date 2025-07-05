package com.project.masp.Repository;

import com.project.masp.Models.Users.TripManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripManagerRepository extends JpaRepository<TripManager, Long> {
    List<TripManager> findByCompanyId(Long companyId);
}
