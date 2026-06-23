package ro.biblioteca.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ro.biblioteca.entity.Rol;
import ro.biblioteca.service.RolService;

@Controller
@RequestMapping("/roluri")
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping
    public String listRoluri(Model model) {
        model.addAttribute("roluri", rolService.findAll());
        return "rol/list";
    }

    @GetMapping("/nou")
    public String showCreateForm(Model model) {
        model.addAttribute("rol", new Rol());
        return "rol/form";
    }

    @PostMapping("/salvare")
    public String saveRol(@Valid @ModelAttribute("rol") Rol rol,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "rol/form";
        }
        rolService.save(rol);
        return "redirect:/roluri";
    }

    @GetMapping("/editare/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("rol", rolService.findById(id));
        return "rol/form";
    }

    @PostMapping("/actualizare/{id}")
    public String updateRol(@PathVariable Long id,
                            @Valid @ModelAttribute("rol") Rol rol,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "rol/form";
        }
        rolService.update(id, rol);
        return "redirect:/roluri";
    }

    @GetMapping("/stergere/{id}")
    public String deleteRol(@PathVariable Long id) {
        rolService.deleteById(id);
        return "redirect:/roluri";
    }
}
