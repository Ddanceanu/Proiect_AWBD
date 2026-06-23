package ro.biblioteca.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ro.biblioteca.entity.Autor;
import ro.biblioteca.service.AutorService;

@Controller
@RequestMapping("/autori")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping
    public String listAutori(Model model) {
        model.addAttribute("autori", autorService.findAll());
        return "autor/list";
    }

    @GetMapping("/nou")
    public String showCreateForm(Model model) {
        model.addAttribute("autor", new Autor());
        return "autor/form";
    }

    @PostMapping("/salvare")
    public String saveAutor(@Valid @ModelAttribute("autor") Autor autor,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "autor/form";
        }
        autorService.save(autor);
        return "redirect:/autori";
    }

    @GetMapping("/editare/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("autor", autorService.findById(id));
        return "autor/form";
    }

    @PostMapping("/actualizare/{id}")
    public String updateAutor(@PathVariable Long id,
                              @Valid @ModelAttribute("autor") Autor autor,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "autor/form";
        }
        autorService.update(id, autor);
        return "redirect:/autori";
    }

    @GetMapping("/stergere/{id}")
    public String deleteAutor(@PathVariable Long id) {
        autorService.deleteById(id);
        return "redirect:/autori";
    }
}
