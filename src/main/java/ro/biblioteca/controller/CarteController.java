package ro.biblioteca.controller;

import jakarta.validation.Validator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ro.biblioteca.entity.Carte;
import ro.biblioteca.entity.Categorie;
import ro.biblioteca.entity.Editura;
import ro.biblioteca.entity.Autor;
import ro.biblioteca.service.AutorService;
import ro.biblioteca.service.CarteService;
import ro.biblioteca.service.CategorieService;
import ro.biblioteca.service.EdituraService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/carti")
public class CarteController {

    private final CarteService carteService;
    private final AutorService autorService;
    private final EdituraService edituraService;
    private final CategorieService categorieService;
    private final Validator validator;

    public CarteController(CarteService carteService,
                          AutorService autorService,
                          EdituraService edituraService,
                          CategorieService categorieService,
                          Validator validator) {
        this.carteService = carteService;
        this.autorService = autorService;
        this.edituraService = edituraService;
        this.categorieService = categorieService;
        this.validator = validator;
    }

    @GetMapping
    public String listCarti(Model model) {
        model.addAttribute("carti", carteService.findAll());
        return "carte/list";
    }

    @GetMapping("/nou")
    public String showCreateForm(Model model) {
        model.addAttribute("carte", new Carte());
        populateFormModel(model);
        return "carte/form";
    }

    @PostMapping("/salvare")
    public String saveCarte(@ModelAttribute("carte") Carte carte,
                            BindingResult bindingResult,
                            @RequestParam("autorId") Long autorId,
                            @RequestParam("edituraId") Long edituraId,
                            @RequestParam(value = "categorieIds", required = false) List<Long> categorieIds,
                            Model model) {
        carte.setAutor(autorService.findById(autorId));
        carte.setEditura(edituraService.findById(edituraId));
        carte.setCategorii(convertCategorieIds(categorieIds));
        validateCarte(carte, bindingResult);

        if (bindingResult.hasErrors()) {
            populateFormModel(model);
            return "carte/form";
        }

        carteService.save(carte);
        return "redirect:/carti";
    }

    @GetMapping("/editare/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("carte", carteService.findById(id));
        populateFormModel(model);
        return "carte/form";
    }

    @PostMapping("/actualizare/{id}")
    public String updateCarte(@PathVariable Long id,
                              @ModelAttribute("carte") Carte carte,
                              BindingResult bindingResult,
                              @RequestParam("autorId") Long autorId,
                              @RequestParam("edituraId") Long edituraId,
                              @RequestParam(value = "categorieIds", required = false) List<Long> categorieIds,
                              Model model) {
        carte.setAutor(autorService.findById(autorId));
        carte.setEditura(edituraService.findById(edituraId));
        carte.setCategorii(convertCategorieIds(categorieIds));
        validateCarte(carte, bindingResult);

        if (bindingResult.hasErrors()) {
            populateFormModel(model);
            return "carte/form";
        }

        carteService.update(id, carte);
        return "redirect:/carti";
    }

    @GetMapping("/stergere/{id}")
    public String deleteCarte(@PathVariable Long id) {
        carteService.deleteById(id);
        return "redirect:/carti";
    }

    private void populateFormModel(Model model) {
        model.addAttribute("autori", autorService.findAll());
        model.addAttribute("edituri", edituraService.findAll());
        model.addAttribute("categorii", categorieService.findAll());
    }

    private Set<Categorie> convertCategorieIds(List<Long> categorieIds) {
        if (categorieIds == null || categorieIds.isEmpty()) {
            return new HashSet<>();
        }
        return categorieIds.stream()
                .map(categorieService::findById)
                .collect(Collectors.toSet());
    }

    private void validateCarte(Carte carte, BindingResult bindingResult) {
        validator.validate(carte).forEach(violation ->
                bindingResult.rejectValue(
                        violation.getPropertyPath().toString(),
                        violation.getMessageTemplate(),
                        violation.getMessage()));
    }
}
