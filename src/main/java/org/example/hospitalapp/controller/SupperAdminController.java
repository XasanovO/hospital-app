package org.example.hospitalapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.hospitalapp.entity.Doctor;
import org.example.hospitalapp.repo.DoctorRepository;
import org.example.hospitalapp.repo.PatientRepository;
import org.example.hospitalapp.repo.SpecialityRepository;
import org.example.hospitalapp.repo.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/supperAdmin")
public class SupperAdminController {

    private final UserRepository userRepository;
    private final SpecialityRepository specialityRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @GetMapping
    public String supperAdmin(Model model) {
        model.addAttribute("supperAdmin", "supperAdmin");
        return "cabinet";
    }

    @GetMapping("/doctors")
    public String allDoctors(Model model) {
        model.addAttribute("doctors", doctorRepository.findAll());
        model.addAttribute("supperAdmin", "supperAdmin");
        return "cabinet";
    }

    @GetMapping("/patients")
    public String allPatients(Model model) {
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("supperAdmin", "supperAdmin");
        return "cabinet";
    }

    @GetMapping("/specialities")
    public String allSpecialities(Model model) {
        model.addAttribute("specialities", specialityRepository.findAll());
        model.addAttribute("supperAdmin", "supperAdmin");
        return "cabinet";
    }

}
