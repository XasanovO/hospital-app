package org.example.hospitalapp.repo;

import org.example.hospitalapp.entity.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialityRepository extends JpaRepository<Speciality, Integer> {
}