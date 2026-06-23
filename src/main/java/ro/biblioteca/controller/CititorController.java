package ro.biblioteca.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ro.biblioteca.entity.Cititor;
import ro.biblioteca.service.CititorService;

@Controller
@RequestMapping("/cititori")
public class CititorController {

    private final CititorService cititorService;

    public CititorController(CititorService cititorService) {
        this.cititorService = cititorService;
    }

    @GetMapping
    public String listCititori(Model model) {
        model.addAttribute("cititori", cititorService.findAll());
        return "cititor/list";
    }

    @GetMapping("/nou")
    public String showCreateForm(Model model) {
        model.addAttribute("cititor", new Cititor());
        return "cititor/form";
    }

    @PostMapping("/salvare")
    public String saveCititor(@Valid @ModelAttribute("cititor") Cititor cititor,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "cititor/form";
        }
        cititorService.save(cititor);
        return "redirect:/cititori";
    }

    @GetMapping("/editare/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("cititor", cititorService.findById(id));
        return "cititor/form";
    }

    @PostMapping("/actualizare/{id}")
    public String updateCititor(@PathVariable Long id,
                                @Valid @ModelAttribute("cititor") Cititor cititor,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "cititor/form";
        }
        cititorService.update(id, cititor);
        return "redirect:/cititori";
    }

    @GetMapping("/stergere/{id}")
    public String deleteCititor(@PathVariable Long id) {
        cititorService.deleteById(id);
        return "redirect:/cititori";
    }
}
