package ro.biblioteca.bootstrap;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.biblioteca.entity.Autor;
import ro.biblioteca.entity.Cititor;
import ro.biblioteca.entity.Editura;
import ro.biblioteca.entity.Rol;
import ro.biblioteca.entity.Utilizator;
import ro.biblioteca.repository.AutorRepository;
import ro.biblioteca.repository.CititorRepository;
import ro.biblioteca.repository.EdituraRepository;
import ro.biblioteca.repository.RolRepository;
import ro.biblioteca.repository.UtilizatorRepository;

@Component
public class DataInitializer implements ApplicationRunner {

    private final RolRepository rolRepository;
    private final CititorRepository cititorRepository;
    private final UtilizatorRepository utilizatorRepository;
    private final AutorRepository autorRepository;
    private final EdituraRepository edituraRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RolRepository rolRepository,
                           CititorRepository cititorRepository,
                           UtilizatorRepository utilizatorRepository,
                           AutorRepository autorRepository,
                           EdituraRepository edituraRepository,
                           PasswordEncoder passwordEncoder) {
        this.rolRepository = rolRepository;
        this.cititorRepository = cititorRepository;
        this.utilizatorRepository = utilizatorRepository;
        this.autorRepository = autorRepository;
        this.edituraRepository = edituraRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (rolRepository.count() == 0) {
            rolRepository.save(new Rol("USER"));
            rolRepository.save(new Rol("ADMIN"));
        }

        if (utilizatorRepository.count() == 0) {
            Cititor adminCititor = new Cititor("Admin", "Biblioteca", "admin@biblioteca.local", "0730000000", java.time.LocalDate.now(), "Administrator");
            cititorRepository.save(adminCititor);

            Utilizator admin = new Utilizator();
            admin.setUsername("admin");
            admin.setParola(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@biblioteca.local");
            admin.setActiv(true);
            admin.setCititor(adminCititor);
            admin.getRoluri().add(rolRepository.findByNume("ADMIN").orElseThrow());
            admin.getRoluri().add(rolRepository.findByNume("USER").orElseThrow());
            utilizatorRepository.save(admin);
        }

        if (autorRepository.count() == 0) {
            autorRepository.save(new Autor("Ion", "Creanga", "Romania", 1837));
            autorRepository.save(new Autor("Mihai", "Eminescu", "Romania", 1850));
        }

        if (edituraRepository.count() == 0) {
            edituraRepository.save(new Editura("Editura Universitară", "București", "contact@univ.ro"));
            edituraRepository.save(new Editura("Editura Polirom", "Iași", "contact@polirom.ro"));
        }
    }
}
