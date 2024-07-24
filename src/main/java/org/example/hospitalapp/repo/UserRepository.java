package org.example.hospitalapp.repo;

import org.example.hospitalapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    public Optional<User> findByPhone(String phone);
}