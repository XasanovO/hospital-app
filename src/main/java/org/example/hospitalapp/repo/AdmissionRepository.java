package org.example.hospitalapp.repo;

import org.example.hospitalapp.entity.Admission;
import org.example.hospitalapp.entity.Doctor;
import org.example.hospitalapp.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdmissionRepository extends JpaRepository<Admission, Integer> {

    List<Admission> findByPatient(Patient patient);
    @Query("""
                select a from Admission a 
                where a.doctor = ?1 and (a.status = 'WAITING' or a.status = 'IN_PROGRESS') 
                order by (case when a.status = 'IN_PROGRESS' then 0 else 1 end), a.id desc
            """)
    List<Admission> findByDoctorAndStatusWaiting(Doctor doctor);

}