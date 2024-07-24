package org.example.hospitalapp.service;


import lombok.RequiredArgsConstructor;
import org.example.hospitalapp.dto.AdmissionDto;
import org.example.hospitalapp.entity.Admission;
import org.example.hospitalapp.entity.Doctor;
import org.example.hospitalapp.entity.Patient;
import org.example.hospitalapp.entity.Procedure;
import org.example.hospitalapp.entity.enums.AdmissionStatus;
import org.example.hospitalapp.repo.AdmissionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdmissionService {
    private final AdmissionRepository admissionRepository;

    public List<AdmissionDto> getAllPatientAdmissions(Patient patient) {
        List<Admission> admissions = admissionRepository.findByPatient(patient);
        List<AdmissionDto> admissionDtos = new ArrayList<>();
        for (Admission admission : admissions) {
            int sumOfProcedure = admission.getProcedures().stream().mapToInt(Procedure::getPrice).sum();
            admissionDtos.add(
                    new AdmissionDto(
                            admission.getDoctor().getUser().toString(),
                            admission.getDoctor().getSpeciality().getName(),
                            admission.getSubscribeDateTime(),
                            admission.getId(),
                            sumOfProcedure,
                            admission.getStatus()
                    )
            );
        }
        return admissionDtos;
    }

    public Admission haveInProgressAdmission(List<Admission> admissions) {
        for (Admission admission : admissions) {
            if (admission.getStatus().equals(AdmissionStatus.IN_PROGRESS)) {
                return admission;
            }
        }
        return null;
    }

    public Integer countOfQueueAdmission(Patient patient) {
        List<Admission> patientAdmissions = admissionRepository.findByPatient(patient);
        List<Admission> admissions = admissionRepository.findAll();
        Integer count = 0;
        for (Admission admission : patientAdmissions) {
            if (admission.getStatus().equals(AdmissionStatus.WAITING)) {
                Doctor doctor = admission.getDoctor();
                for (Admission admission1 : admissions) {
                    if (admission1.getDoctor().equals(doctor) && admission1.getStatus().equals(AdmissionStatus.WAITING)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}
