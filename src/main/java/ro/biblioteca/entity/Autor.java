package ro.biblioteca.entity;
import jakarta.persistence.*;

@Entity  // clasa reprezinta o entitate in baza de date
@Table(name = "autori")
public class Autor {
    @Id  // cheie primara
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // id se genereaza automat
    private Long id;

    @Column(name = "nume", nullable = false)
    private String nume;

    @Column(name = "prenume", nullable = false)
    private String prenume;

    @Column(name = "nationalitate")
    private String nationalitate;

    @Column(name = "an_nastere")  // coloana an_nastere din bd corespunde cu variabila anNastere din java
    private Integer anNastere;

    public Autor() {
    }

    public Autor(String nume, String prenume, String nationalitate, Integer anNastere) {
        this.nume = nume;
        this.prenume = prenume;
        this.nationalitate = nationalitate;
        this.anNastere = anNastere;
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

    public String getNationalitate() {
        return nationalitate;
    }

    public void setNationalitate(String nationalitate) {
        this.nationalitate = nationalitate;
    }

    public Integer getAnNastere() {
        return anNastere;
    }

    public void setAnNastere(Integer anNastere) {
        this.anNastere = anNastere;
    }
}
