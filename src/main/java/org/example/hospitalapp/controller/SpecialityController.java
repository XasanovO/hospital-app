package org.example.hospitalapp.controller;


import lombok.RequiredArgsConstructor;
import org.example.hospitalapp.entity.Speciality;
import org.example.hospitalapp.repo.SpecialityRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/speciality")
@RequiredArgsConstructor
public class SpecialityController {

    private final SpecialityRepository specialityRepository;

    @PostMapping("/add")
    public String addSpeciality(@RequestParam String name) {
        Speciality speciality = Speciality.builder().name(name).build();
        specialityRepository.save(speciality);
        return "redirect:/supperAdmin/specialities";
    }

    @PostMapping("/delete")
    public String deleteSpeciality(@RequestParam Integer id) {
        specialityRepository.deleteById(id);
        return "redirect:/supperAdmin/specialities";
    }

    @GetMapping("/edit")
    public String acceptEdit(Model model, @RequestParam Integer id) {
        specialityRepository.findById(id).ifPresent(speciality -> {
            model.addAttribute("speciality", speciality);
        });
        return "editSpeciality";
    }

    @PostMapping("/edit")
    public String editSpeciality(@RequestParam Integer id, @RequestParam String name) {
        specialityRepository.findById(id).ifPresent(speciality -> {
            speciality.setName(name);
            specialityRepository.save(speciality);
        });
        return "redirect:/supperAdmin/specialities";
    }
}
