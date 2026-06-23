package ro.biblioteca.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ro.biblioteca.entity.Categorie;
import ro.biblioteca.service.CategorieService;

@Controller
@RequestMapping("/categorii")
public class CategorieController {

    private final CategorieService categorieService;

    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @GetMapping
    public String listCategorii(Model model) {
        model.addAttribute("categorii", categorieService.findAll());
        return "categorie/list";
    }

    @GetMapping("/nou")
    public String showCreateForm(Model model) {
        model.addAttribute("categorie", new Categorie());
        return "categorie/form";
    }

    @PostMapping("/salvare")
    public String saveCategorie(@Valid @ModelAttribute("categorie") Categorie categorie,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "categorie/form";
        }
        categorieService.save(categorie);
        return "redirect:/categorii";
    }

    @GetMapping("/editare/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("categorie", categorieService.findById(id));
        return "categorie/form";
    }

    @PostMapping("/actualizare/{id}")
    public String updateCategorie(@PathVariable Long id,
                                  @Valid @ModelAttribute("categorie") Categorie categorie,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "categorie/form";
        }
        categorieService.update(id, categorie);
        return "redirect:/categorii";
    }

    @GetMapping("/stergere/{id}")
    public String deleteCategorie(@PathVariable Long id) {
        categorieService.deleteById(id);
        return "redirect:/categorii";
    }
}
