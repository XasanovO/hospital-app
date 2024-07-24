package org.example.hospitalapp.controller;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.hospitalapp.entity.*;
import org.example.hospitalapp.entity.enums.AdmissionStatus;
import org.example.hospitalapp.repo.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admission")
public class AdmissionController {

    private final AdminstratorRepository adminstratorRepository;
    private final AdmissionRepository admissionRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @PostMapping("/subscribe")
    public String subscribe(
            @RequestParam Integer doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
            @RequestParam String description,
            @AuthenticationPrincipal User currentUser,
            @RequestParam Integer patientId,
            RedirectAttributes redirectAttributes
    ) {

        if (dateTime.isBefore(LocalDateTime.now())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Xato: sana tanlandi. Iltimos, to'g'ri sanani tanlang.");
            return "redirect:/adminstrator?patientId=" + patientId;
        }

        Doctor doctor = doctorRepository.findById(doctorId).get();
        Patient patient = patientRepository.findById(patientId).get();
        Adminstrator adminstrator = adminstratorRepository.findByUser(currentUser);

        Admission admission = Admission.builder()
                .doctor(doctor)
                .patient(patient)
                .description(description)
                .adminstrator(adminstrator)
                .subscribeDate(dateTime)
                .status(AdmissionStatus.REGISTRATION)
                .procedures(new ArrayList<>())
                .build();

        admissionRepository.save(admission);

        return "redirect:/adminstrator?patientId=" + patientId;
    }

    @PostMapping("/enter")
    public String enterPatientAdmission(@RequestParam Integer patientId, @RequestParam Integer admissionId) {
        admissionRepository.findById(admissionId).ifPresent(admission -> {
            admission.setArrivedDate(LocalDateTime.now());
            admission.setStatus(AdmissionStatus.WAITING);
            admissionRepository.save(admission);
        });
        return "redirect:/adminstrator?patientId=" + patientId;
    }

    @PostMapping("/complete")
    public String completePatientAdmission(@RequestParam Integer admissionId) {
        admissionRepository.findById(admissionId).ifPresent(admission -> {
            if (admission.getArrivedDate().isBefore(admission.getSubscribeDate())) {
                admission.setStatus(AdmissionStatus.ON_TIME);
            } else {
                admission.setStatus(AdmissionStatus.NOT_ON_TIME);
            }
            admissionRepository.save(admission);
        });
        return "redirect:/doctor";
    }

    @GetMapping("/info")
    public String admissionInfo(@RequestParam Integer admissionId, Model model, @AuthenticationPrincipal User user) {
        Admission admission = admissionRepository.findById(admissionId).get();
        model.addAttribute("procedures", admission.getProcedures());
        model.addAttribute("admission", admission);
        return "admissionInfo";
    }

}
