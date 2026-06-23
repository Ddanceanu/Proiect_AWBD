package ro.biblioteca.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ro.biblioteca.entity.Cititor;
import ro.biblioteca.entity.Rol;
import ro.biblioteca.entity.Utilizator;
import ro.biblioteca.service.CititorService;
import ro.biblioteca.service.RolService;
import ro.biblioteca.service.UtilizatorService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/utilizatori")
public class UtilizatorController {

    private final UtilizatorService utilizatorService;
    private final RolService rolService;
    private final CititorService cititorService;

    public UtilizatorController(UtilizatorService utilizatorService,
                                RolService rolService,
                                CititorService cititorService) {
        this.utilizatorService = utilizatorService;
        this.rolService = rolService;
        this.cititorService = cititorService;
    }

    @GetMapping
    public String listUtilizatori(Model model) {
        model.addAttribute("utilizatori", utilizatorService.findAll());
        return "utilizator/list";
    }

    @GetMapping("/nou")
    public String showCreateForm(Model model) {
        model.addAttribute("utilizator", new Utilizator());
        model.addAttribute("roluri", rolService.findAll());
        model.addAttribute("cititori", cititoriDisponibiliPentruCreare());
        model.addAttribute("selectedRoleIds", List.of());
        return "utilizator/form";
    }

    @PostMapping("/salvare")
    public String saveUtilizator(@Valid @ModelAttribute("utilizator") Utilizator utilizator,
                                 BindingResult bindingResult,
                                 @RequestParam("cititorId") Long cititorId,
                                 @RequestParam(value = "rolIds", required = false) List<Long> rolIds,
                                 Model model) {
        if (utilizator.getParola() == null || utilizator.getParola().isBlank()) {
            bindingResult.rejectValue("parola", "NotBlank.utilizator.parola", "Parola este obligatorie.");
        }
        if (utilizatorService.isCititorAssigned(cititorId)) {
            bindingResult.reject("cititorInUse", "Cititorul selectat este deja asociat altui utilizator.");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("roluri", rolService.findAll());
            model.addAttribute("cititori", cititoriDisponibiliPentruCreare());
            model.addAttribute("selectedRoleIds", rolIds != null ? rolIds : List.of());
            return "utilizator/form";
        }
        utilizator.setCititor(cititorService.findById(cititorId));
        utilizator.setRoluri(convertRolIds(rolIds));
        utilizatorService.save(utilizator);
        return "redirect:/utilizatori";
    }

    @GetMapping("/editare/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Utilizator utilizator = utilizatorService.findById(id);
        utilizator.setParola("");
        model.addAttribute("utilizator", utilizator);
        model.addAttribute("roluri", rolService.findAll());
        model.addAttribute("cititori", cititoriDisponibiliPentruEditare(utilizator.getCititor().getId()));
        model.addAttribute("selectedRoleIds", utilizator.getRoluri().stream().map(Rol::getId).collect(Collectors.toList()));
        return "utilizator/form";
    }

    @PostMapping("/actualizare/{id}")
    public String updateUtilizator(@PathVariable Long id,
                                   @Valid @ModelAttribute("utilizator") Utilizator utilizator,
                                   BindingResult bindingResult,
                                   @RequestParam("cititorId") Long cititorId,
                                   @RequestParam(value = "rolIds", required = false) List<Long> rolIds,
                                   Model model) {
        if (utilizatorService.isCititorAssignedToAnotherUser(cititorId, id)) {
            bindingResult.reject("cititorInUse", "Cititorul selectat este deja asociat altui utilizator.");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("roluri", rolService.findAll());
            Long cititorCurentId = utilizatorService.findById(id).getCititor().getId();
            model.addAttribute("cititori", cititoriDisponibiliPentruEditare(cititorCurentId));
            model.addAttribute("selectedRoleIds", rolIds != null ? rolIds : List.of());
            return "utilizator/form";
        }
        utilizator.setCititor(cititorService.findById(cititorId));
        utilizator.setRoluri(convertRolIds(rolIds));
        utilizatorService.update(id, utilizator);
        return "redirect:/utilizatori";
    }

    @GetMapping("/stergere/{id}")
    public String deleteUtilizator(@PathVariable Long id) {
        utilizatorService.deleteById(id);
        return "redirect:/utilizatori";
    }

    private Set<Rol> convertRolIds(List<Long> rolIds) {
        if (rolIds == null || rolIds.isEmpty()) {
            return Set.of();
        }
        return rolIds.stream()
                .map(rolService::findById)
                .collect(Collectors.toSet());
    }

    private List<Cititor> cititoriDisponibiliPentruCreare() {
        return cititorService.findAll().stream()
                .filter(cititor -> !utilizatorService.isCititorAssigned(cititor.getId()))
                .collect(Collectors.toList());
    }

    private List<Cititor> cititoriDisponibiliPentruEditare(Long cititorCurentId) {
        return cititorService.findAll().stream()
                .filter(cititor -> cititor.getId().equals(cititorCurentId) || !utilizatorService.isCititorAssigned(cititor.getId()))
                .collect(Collectors.toList());
    }
}
