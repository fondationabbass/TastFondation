package com.bdi.fondation.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Candidat.
 */
@Entity
@Table(name = "candidat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Candidat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "dna")
    private LocalDate dna;

    @Column(name = "lieu_na")
    private String lieuNa;

    @Column(name = "adresse")
    private String adresse;

    @ManyToOne
    private Candidature candidature;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Candidat nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDna() {
        return dna;
    }

    public Candidat dna(LocalDate dna) {
        this.dna = dna;
        return this;
    }

    public void setDna(LocalDate dna) {
        this.dna = dna;
    }

    public String getLieuNa() {
        return lieuNa;
    }

    public Candidat lieuNa(String lieuNa) {
        this.lieuNa = lieuNa;
        return this;
    }

    public void setLieuNa(String lieuNa) {
        this.lieuNa = lieuNa;
    }

    public String getAdresse() {
        return adresse;
    }

    public Candidat adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Candidature getCandidature() {
        return candidature;
    }

    public Candidat candidature(Candidature candidature) {
        this.candidature = candidature;
        return this;
    }

    public void setCandidature(Candidature candidature) {
        this.candidature = candidature;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidat candidat = (Candidat) o;
        if (candidat.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), candidat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Candidat{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", dna='" + getDna() + "'" +
            ", lieuNa='" + getLieuNa() + "'" +
            ", adresse='" + getAdresse() + "'" +
            "}";
    }
}
