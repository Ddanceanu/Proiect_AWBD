package ro.biblioteca.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "carti")
public class Carte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titlu", nullable = false)
    private String titlu;

    @Column(name = "isbn", unique = true)
    private String isbn;

    @Column(name = "an_publicare")
    private Integer anPublicare;

    @Column(name = "numar_pagini")
    private Integer numarPagini;

    @Column(name = "disponibila")
    private Boolean disponibila = true;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    @ManyToOne
    @JoinColumn(name = "editura_id", nullable = false)
    private Editura editura;

    @ManyToMany
    @JoinTable(
            name = "carti_categorii",
            joinColumns = @JoinColumn(name = "carte_id"),
            inverseJoinColumns = @JoinColumn(name = "categorie_id")
    )
    private Set<Categorie> categorii = new HashSet<>();

    public Carte() {
    }

    public Carte(String titlu, String isbn, Integer anPublicare, Integer numarPagini,
                 Boolean disponibila, Autor autor, Editura editura) {
        this.titlu = titlu;
        this.isbn = isbn;
        this.anPublicare = anPublicare;
        this.numarPagini = numarPagini;
        this.disponibila = disponibila;
        this.autor = autor;
        this.editura = editura;
    }

    public Long getId() {
        return id;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getAnPublicare() {
        return anPublicare;
    }

    public void setAnPublicare(Integer anPublicare) {
        this.anPublicare = anPublicare;
    }

    public Integer getNumarPagini() {
        return numarPagini;
    }

    public void setNumarPagini(Integer numarPagini) {
        this.numarPagini = numarPagini;
    }

    public Boolean getDisponibila() {
        return disponibila;
    }

    public void setDisponibila(Boolean disponibila) {
        this.disponibila = disponibila;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Editura getEditura() {
        return editura;
    }

    public void setEditura(Editura editura) {
        this.editura = editura;
    }

    public Set<Categorie> getCategorii() {
        return categorii;
    }

    public void setCategorii(Set<Categorie> categorii) {
        this.categorii = categorii;
    }
}