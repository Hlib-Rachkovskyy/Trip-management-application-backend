package com.project.masp.Repository;

import com.project.masp.Models.Trip.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
}
