package ro.biblioteca.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cititori")
public class Cititor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nume", nullable = false)
    private String nume;

    @Column(name = "prenume", nullable = false)
    private String prenume;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "telefon")
    private String telefon;

    @Column(name = "data_inscriere")
    private LocalDate dataInscriere;

    @Column(name = "tip_cititor")
    private String tipCititor;

    public Cititor() {
    }

    public Cititor(String nume, String prenume, String email, String telefon, LocalDate dataInscriere, String tipCititor) {
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.telefon = telefon;
        this.dataInscriere = dataInscriere;
        this.tipCititor = tipCititor;
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

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public LocalDate getDataInscriere() {
        return dataInscriere;
    }

    public void setDataInscriere(LocalDate dataInscriere) {
        this.dataInscriere = dataInscriere;
    }

    public String getTipCititor() {
        return tipCititor;
    }

    public void setTipCititor(String tipCititor) {
        this.tipCititor = tipCititor;
    }
}