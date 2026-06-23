package ro.biblioteca.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
@Table(name = "imprumuturi")
public class Imprumut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "data_imprumut", nullable = false)
    private LocalDate dataImprumut;

    @NotNull
    @Column(name = "data_scadenta", nullable = false)
    private LocalDate dataScadenta;

    @Column(name = "data_returnare")
    private LocalDate dataReturnare;

    @NotBlank
    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "observatii")
    private String observatii;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cititor_id", nullable = false)
    private Cititor cititor;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "carte_id", nullable = false)
    private Carte carte;

    public Imprumut() {
    }

    public Imprumut(LocalDate dataImprumut, LocalDate dataScadenta, LocalDate dataReturnare,
                    String status, String observatii, Cititor cititor, Carte carte) {
        this.dataImprumut = dataImprumut;
        this.dataScadenta = dataScadenta;
        this.dataReturnare = dataReturnare;
        this.status = status;
        this.observatii = observatii;
        this.cititor = cititor;
        this.carte = carte;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataImprumut() {
        return dataImprumut;
    }

    public void setDataImprumut(LocalDate dataImprumut) {
        this.dataImprumut = dataImprumut;
    }

    public LocalDate getDataScadenta() {
        return dataScadenta;
    }

    public void setDataScadenta(LocalDate dataScadenta) {
        this.dataScadenta = dataScadenta;
    }

    public LocalDate getDataReturnare() {
        return dataReturnare;
    }

    public void setDataReturnare(LocalDate dataReturnare) {
        this.dataReturnare = dataReturnare;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObservatii() {
        return observatii;
    }

    public void setObservatii(String observatii) {
        this.observatii = observatii;
    }

    public Cititor getCititor() {
        return cititor;
    }

    public void setCititor(Cititor cititor) {
        this.cititor = cititor;
    }

    public Carte getCarte() {
        return carte;
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }
}