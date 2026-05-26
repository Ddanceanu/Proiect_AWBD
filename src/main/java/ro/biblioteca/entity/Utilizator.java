package ro.biblioteca.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "utilizatori")
public class Utilizator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "parola", nullable = false)
    private String parola;

    @Column(name = "activ")
    private Boolean activ = true;

    @OneToOne
    @JoinColumn(name = "cititor_id", nullable = false, unique = true)
    private Cititor cititor;

    @ManyToMany
    @JoinTable(
            name = "utilizatori_roluri",
            joinColumns = @JoinColumn(name = "utilizator_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Set<Rol> roluri = new HashSet<>();

    public Utilizator() {
    }

    public Utilizator(String username, String parola, Boolean activ, Cititor cititor) {
        this.username = username;
        this.parola = parola;
        this.activ = activ;
        this.cititor = cititor;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public Boolean getActiv() {
        return activ;
    }

    public void setActiv(Boolean activ) {
        this.activ = activ;
    }

    public Cititor getCititor() {
        return cititor;
    }

    public void setCititor(Cititor cititor) {
        this.cititor = cititor;
    }
}