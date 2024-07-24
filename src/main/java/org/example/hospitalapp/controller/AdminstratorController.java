package org.example.hospitalapp.controller;


import lombok.RequiredArgsConstructor;
import org.example.hospitalapp.dto.AdmissionDto;
import org.example.hospitalapp.entity.Patient;
import org.example.hospitalapp.repo.AdmissionRepository;
import org.example.hospitalapp.repo.DoctorRepository;
import org.example.hospitalapp.repo.PatientRepository;
import org.example.hospitalapp.repo.UserRepository;
import org.example.hospitalapp.service.AdmissionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/adminstrator")
public class AdminstratorController {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AdmissionService admissionService;
    private final UserRepository userRepository;
    private final AdmissionRepository admissionRepository;

    @GetMapping
    public String adminstrator(
            Model model,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer patientId

    ) {
        List<Patient> patients = patientRepository.findAll();
        if (search != null) {
            model.addAttribute("search", search);
            patients = patientRepository.findByUserPhone(search);
        }
        if (patientId != null) {
            patientRepository.findById(patientId).ifPresent(patient -> {
                model.addAttribute("patient", patient);
                List<AdmissionDto> admissions = admissionService.getAllPatientAdmissions(patient);
                model.addAttribute("admissions", admissions);
            });
        }
        model.addAttribute("doctors", doctorRepository.findAll());
        model.addAttribute("patients", patients);

        // Flash atributni modelga qo'shish (agar mavjud bo'lsa)
        if (model.containsAttribute("errorMessage")) {
            model.addAttribute("errorMessage", model.getAttribute("errorMessage"));
        }

        return "adminstrator";
    }


}
