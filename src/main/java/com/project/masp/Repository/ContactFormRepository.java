package com.project.masp.Repository;

import com.project.masp.Models.Company.ContactForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ContactFormRepository extends JpaRepository<ContactForm, Integer> {
}
