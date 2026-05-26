package ro.biblioteca.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "edituri")
public class Editura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nume", nullable = false)
    private String nume;

    @Column(name = "oras")
    private String oras;

    @Column(name = "email_contact")
    private String emailContact;

    public Editura() {
    }

    public Editura(String nume, String oras, String emailContact) {
        this.nume = nume;
        this.oras = oras;
        this.emailContact = emailContact;
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

    public String getOras() {
        return oras;
    }

    public void setOras(String oras) {
        this.oras = oras;
    }

    public String getEmailContact() {
        return emailContact;
    }

    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
    }
}