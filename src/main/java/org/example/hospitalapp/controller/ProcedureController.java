package org.example.hospitalapp.controller;


import lombok.RequiredArgsConstructor;
import org.example.hospitalapp.entity.Admission;
import org.example.hospitalapp.entity.Procedure;
import org.example.hospitalapp.repo.AdmissionRepository;
import org.example.hospitalapp.repo.ProcedureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/procedure")
public class ProcedureController {

    private final ProcedureRepository procedureRepository;
    private final AdmissionRepository admissionRepository;

    @PostMapping("/add")
    public String addProcedure(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam Integer price,
            @RequestParam Integer admissionId
    ) {
        Admission admission = admissionRepository.findById(admissionId).get();
        Procedure procedure = new Procedure(title, description, price);
        Procedure savedProcedure = procedureRepository.save(procedure);
        admission.getProcedures().add(savedProcedure);
        admissionRepository.save(admission);
        return "redirect:/doctor?admissionId=" + admissionId;
    }


}
