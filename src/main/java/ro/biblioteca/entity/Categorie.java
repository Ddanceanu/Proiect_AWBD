package ro.biblioteca.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "categorii")
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nume", nullable = false)
    private String nume;

    @Column(name = "descriere")
    private String descriere;

    public Categorie() {
    }

    public Categorie(String nume, String descriere) {
        this.nume = nume;
        this.descriere = descriere;
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

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }
}