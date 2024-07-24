package org.example.hospitalapp.repo;

import org.example.hospitalapp.entity.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcedureRepository extends JpaRepository<Procedure, Integer> {
}