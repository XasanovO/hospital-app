package org.example.hospitalapp.repo;

import org.example.hospitalapp.entity.Patient;
import org.example.hospitalapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
    List<Patient> findByUserPhone(String phone);

    Patient findByUser(User user);
}