package org.example.hospitalapp.controller;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.hospitalapp.dto.DoctorReq;
import org.example.hospitalapp.entity.*;
import org.example.hospitalapp.entity.enums.AdmissionStatus;
import org.example.hospitalapp.repo.*;
import org.example.hospitalapp.service.AdmissionService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/doctor")
public class DoctorController {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SpecialityRepository specialityRepository;
    private final RoleRepository roleRepository;
    private final RoomRepository roomRepository;
    private final AdmissionRepository admissionRepository;
    private final AdmissionService admissionService;

    @GetMapping
    public String doctor(
            @AuthenticationPrincipal User user,
            Model model,
            @RequestParam(required = false) Integer admissionId
    ) {
        Doctor doctor = doctorRepository.findByUser(user);
        List<Admission> admissions = admissionRepository.findByDoctorAndStatusWaiting(doctor);
        model.addAttribute("admissions", admissions);

        Admission inProgressAdmission = admissionService.haveInProgressAdmission(admissions);

        if (admissionId != null) {
            if (inProgressAdmission != null) {
                model.addAttribute("currentAdmission", inProgressAdmission);
            } else {
                admissionRepository.findById(admissionId).ifPresent(admission -> {
                    admission.setStatus(AdmissionStatus.IN_PROGRESS);
                    admissionRepository.save(admission);
                    model.addAttribute("currentAdmission", admission);
                });
            }
        }
        return "doctor";
    }


    @GetMapping("/add")
    public String acceptAdd(Model model) {
        model.addAttribute("specialities", specialityRepository.findAll());
        model.addAttribute("rooms", roomRepository.findAll());
        return "addDoctor";
    }

    @PostMapping("/add")
    public String addDoctor(@ModelAttribute DoctorReq doctorReq) {
        Role roleDoctor = roleRepository.findByRole("ROLE_DOCTOR");
        User user = new User(
                doctorReq.firstName(),
                doctorReq.lastName(),
                doctorReq.phone(),
                passwordEncoder.encode(doctorReq.phone()),
                new ArrayList<>(List.of(roleDoctor))
        );
        userRepository.save(user);
        Speciality speciality = specialityRepository.findById(doctorReq.specialtyId()).get();
        Room room = roomRepository.findById(doctorReq.roomId()).get();
        Doctor doctor = Doctor.builder().user(user).speciality(speciality).room(room).build();
        doctorRepository.save(doctor);
        return "redirect:/supperAdmin/doctors";
    }

    @GetMapping("/edit")
    public String acceptEdit(@RequestParam Integer id, Model model) {
        model.addAttribute("specialities", specialityRepository.findAll());
        model.addAttribute("rooms", roomRepository.findAll());
        doctorRepository.findById(id).ifPresent(doctor -> {
                    model.addAttribute("doctor", doctor);
                }
        );
        return "addDoctor";
    }

    @PostMapping("/edit")
    public String editDoctor(@ModelAttribute DoctorReq doctorReq, @RequestParam Integer id) {
        Speciality speciality = specialityRepository.findById(doctorReq.specialtyId()).get();
        Room room = roomRepository.findById(doctorReq.roomId()).get();
        doctorRepository.findById(id).ifPresent(doctor -> {
            doctor.getUser().setFirstName(doctorReq.firstName());
            doctor.getUser().setLastName(doctorReq.lastName());
            doctor.getUser().setPassword(passwordEncoder.encode(doctorReq.phone()));
            doctor.getUser().setPhone(doctorReq.phone());
            doctor.setRoom(room);
            doctor.setSpeciality(speciality);
            doctorRepository.save(doctor);
        });
        return "redirect:/supperAdmin/doctors";
    }

    @PostMapping("/delete")
    @Transactional
    public String deleteDoctor(@RequestParam Integer id) {
        doctorRepository.findById(id).ifPresent(doctor -> {
            userRepository.delete(doctor.getUser());
            doctorRepository.deleteById(id);
        });
        return "redirect:/supperAdmin/doctors";
    }
}
