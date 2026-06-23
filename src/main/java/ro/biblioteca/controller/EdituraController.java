package ro.biblioteca.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ro.biblioteca.entity.Editura;
import ro.biblioteca.service.EdituraService;

@Controller
@RequestMapping("/edituri")
public class EdituraController {

    private final EdituraService edituraService;

    public EdituraController(EdituraService edituraService) {
        this.edituraService = edituraService;
    }

    @GetMapping
    public String listEdituri(Model model) {
        model.addAttribute("edituri", edituraService.findAll());
        return "editura/list";
    }

    @GetMapping("/nou")
    public String showCreateForm(Model model) {
        model.addAttribute("editura", new Editura());
        return "editura/form";
    }

    @PostMapping("/salvare")
    public String saveEditura(@Valid @ModelAttribute("editura") Editura editura,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editura/form";
        }
        edituraService.save(editura);
        return "redirect:/edituri";
    }

    @GetMapping("/editare/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("editura", edituraService.findById(id));
        return "editura/form";
    }

    @PostMapping("/actualizare/{id}")
    public String updateEditura(@PathVariable Long id,
                                @Valid @ModelAttribute("editura") Editura editura,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editura/form";
        }
        edituraService.update(id, editura);
        return "redirect:/edituri";
    }

    @GetMapping("/stergere/{id}")
    public String deleteEditura(@PathVariable Long id) {
        edituraService.deleteById(id);
        return "redirect:/edituri";
    }
}
