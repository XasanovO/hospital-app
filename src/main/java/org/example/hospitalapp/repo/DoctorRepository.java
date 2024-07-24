package org.example.hospitalapp.repo;

import org.example.hospitalapp.entity.Doctor;
import org.example.hospitalapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    Doctor findByUser(User user);

}