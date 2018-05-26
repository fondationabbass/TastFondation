package com.bdi.fondation.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "projet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Projet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "intitule")
    private String intitule;

    @Column(name = "montant")
    private String montant;

    @Column(name = "domaine")
    private String domaine;

    @Column(name = "jhi_type")
    private String type;

    @OneToOne
    @JoinColumn(unique = true)
    private Candidature candidature;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public Projet intitule(String intitule) {
        this.intitule = intitule;
        return this;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getMontant() {
        return montant;
    }

    public Projet montant(String montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public String getDomaine() {
        return domaine;
    }

    public Projet domaine(String domaine) {
        this.domaine = domaine;
        return this;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public String getType() {
        return type;
    }

    public Projet type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Candidature getCandidature() {
        return candidature;
    }

    public Projet candidature(Candidature candidature) {
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
        Projet projet = (Projet) o;
        if (projet.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), projet.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Projet{" +
            "id=" + getId() +
            ", intitule='" + getIntitule() + "'" +
            ", montant='" + getMontant() + "'" +
            ", domaine='" + getDomaine() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
