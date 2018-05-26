package com.bdi.fondation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Candidature.
 */
@Entity
@Table(name = "candidature")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Candidature implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "candidature")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Candidat> candidats = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public Candidature type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public Candidature status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Candidat> getCandidats() {
        return candidats;
    }

    public Candidature candidats(Set<Candidat> candidats) {
        this.candidats = candidats;
        return this;
    }

    public Candidature addCandidat(Candidat candidat) {
        this.candidats.add(candidat);
        candidat.setCandidature(this);
        return this;
    }

    public Candidature removeCandidat(Candidat candidat) {
        this.candidats.remove(candidat);
        candidat.setCandidature(null);
        return this;
    }

    public void setCandidats(Set<Candidat> candidats) {
        this.candidats = candidats;
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
        Candidature candidature = (Candidature) o;
        if (candidature.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), candidature.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Candidature{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
