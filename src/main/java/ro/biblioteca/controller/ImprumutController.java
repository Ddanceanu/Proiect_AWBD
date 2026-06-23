package ro.biblioteca.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ro.biblioteca.entity.Imprumut;
import ro.biblioteca.service.CarteService;
import ro.biblioteca.service.CititorService;
import ro.biblioteca.service.ImprumutService;

@Controller
@RequestMapping("/imprumuturi")
public class ImprumutController {

    private final ImprumutService imprumutService;
    private final CititorService cititorService;
    private final CarteService carteService;

    public ImprumutController(ImprumutService imprumutService,
                              CititorService cititorService,
                              CarteService carteService) {
        this.imprumutService = imprumutService;
        this.cititorService = cititorService;
        this.carteService = carteService;
    }

    @GetMapping
    public String listImprumuturi(Model model) {
        model.addAttribute("imprumuturi", imprumutService.findAll());
        return "imprumut/list";
    }

    @GetMapping("/nou")
    public String showCreateForm(Model model) {
        model.addAttribute("imprumut", new Imprumut());
        model.addAttribute("cititori", cititorService.findAll());
        model.addAttribute("carti", carteService.findAll());
        return "imprumut/form";
    }

    @PostMapping("/salvare")
    public String saveImprumut(@Valid @ModelAttribute("imprumut") Imprumut imprumut,
                               BindingResult bindingResult,
                               @RequestParam("cititorId") Long cititorId,
                               @RequestParam("carteId") Long carteId,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("cititori", cititorService.findAll());
            model.addAttribute("carti", carteService.findAll());
            return "imprumut/form";
        }
        imprumut.setCititor(cititorService.findById(cititorId));
        imprumut.setCarte(carteService.findById(carteId));
        imprumutService.save(imprumut);
        return "redirect:/imprumuturi";
    }

    @GetMapping("/editare/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("imprumut", imprumutService.findById(id));
        model.addAttribute("cititori", cititorService.findAll());
        model.addAttribute("carti", carteService.findAll());
        return "imprumut/form";
    }

    @PostMapping("/actualizare/{id}")
    public String updateImprumut(@PathVariable Long id,
                                 @Valid @ModelAttribute("imprumut") Imprumut imprumut,
                                 BindingResult bindingResult,
                                 @RequestParam("cititorId") Long cititorId,
                                 @RequestParam("carteId") Long carteId,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("cititori", cititorService.findAll());
            model.addAttribute("carti", carteService.findAll());
            return "imprumut/form";
        }
        imprumut.setCititor(cititorService.findById(cititorId));
        imprumut.setCarte(carteService.findById(carteId));
        imprumutService.update(id, imprumut);
        return "redirect:/imprumuturi";
    }

    @GetMapping("/stergere/{id}")
    public String deleteImprumut(@PathVariable Long id) {
        imprumutService.deleteById(id);
        return "redirect:/imprumuturi";
    }
}
