package org.example.hospitalapp.repo;

import org.example.hospitalapp.entity.Adminstrator;
import org.example.hospitalapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminstratorRepository extends JpaRepository<Adminstrator, Integer> {
    Adminstrator findByUser(User user);
}