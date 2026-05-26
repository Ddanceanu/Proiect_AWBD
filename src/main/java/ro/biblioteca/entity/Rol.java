package ro.biblioteca.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roluri")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nume", nullable = false, unique = true)
    private String nume;

    public Rol() {
    }

    public Rol(String nume) {
        this.nume = nume;
    }

    public Long getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }
}