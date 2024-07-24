package org.example.hospitalapp.controller;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.hospitalapp.entity.Admission;
import org.example.hospitalapp.entity.Patient;
import org.example.hospitalapp.entity.Role;
import org.example.hospitalapp.entity.User;
import org.example.hospitalapp.repo.AdmissionRepository;
import org.example.hospitalapp.repo.PatientRepository;
import org.example.hospitalapp.repo.RoleRepository;
import org.example.hospitalapp.repo.UserRepository;
import org.example.hospitalapp.service.AdmissionService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/patient")
public class PatientController {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdmissionRepository admissionRepository;
    private final AdmissionService admissionService;

    @GetMapping
    public String patientPage(Model model, @AuthenticationPrincipal User user) {
        Patient patient = patientRepository.findByUser(user);
        Integer queueCount = admissionService.countOfQueueAdmission(patient);
        model.addAttribute("queueCount", queueCount);
        model.addAttribute("admissions", admissionService.getAllPatientAdmissions(patient));
        return "patient";
    }


    @GetMapping("/add")
    public String acceptAdd() {
        return "addPatient";
    }

    @PostMapping("/add")
    @Transactional
    public String addPatient(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String phone,
            @AuthenticationPrincipal User currUser
    ) {
        Role rolePatient = roleRepository.findByRole("ROLE_PATIENT");

        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .phone(phone)
                .password(passwordEncoder.encode(phone))
                .roles(new ArrayList<>(List.of(rolePatient)))
                .build();

        User savedUser = userRepository.save(user);

        Patient patient = Patient
                .builder()
                .user(savedUser)
                .build();

        patientRepository.save(patient);
        if (currUser.getRoles().get(0).getRole().equals("ROLE_ADMIN")) {
            return "redirect:/adminstrator";
        } else {
            return "redirect:/supperAdmin/patients";
        }
    }

    @PostMapping("delete")
    public String deletePatient(@RequestParam Integer id) {
        patientRepository.findById(id).ifPresent(
                patient -> {
                    userRepository.delete(patient.getUser());
                    patientRepository.delete(patient);
                }
        );
        return "redirect:/supperAdmin/patients";
    }

    @GetMapping("/edit")
    public String editPatient(@RequestParam Integer id, Model model) {
        patientRepository.findById(id).ifPresent(
                patient -> model.addAttribute("patient", patient)
        );
        return "addPatient";
    }

    @PostMapping("/edit")
    public String editPatient(@RequestParam String firstName,
                              @RequestParam String lastName,
                              @RequestParam String phone,
                              @RequestParam Integer id
    ) {
        patientRepository.findById(id).ifPresent(
                patient -> {
                    patient.getUser().setFirstName(firstName);
                    patient.getUser().setLastName(lastName);
                    patient.getUser().setPhone(phone);
                    patient.getUser().setPassword(passwordEncoder.encode(phone));
                    patientRepository.save(patient);
                }
        );
        return "redirect:/supperAdmin/patients";
    }
}
