package de.felixhoevel.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import de.felixhoevel.application.domain.enumeration.Strength;

/**
 * A Contestant.
 */
@Entity
@Table(name = "contestant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Contestant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "e_mail")
    private String eMail;

    @Enumerated(EnumType.STRING)
    @Column(name = "strength")
    private Strength strength;

    @Column(name = "total_points")
    private Integer totalPoints;

    @Column(name = "confirmed")
    private Boolean confirmed;

    @Column(name = "confirmed_at")
    private ZonedDateTime confirmedAt;

    @Column(name = "payed")
    private Boolean payed;

    @Column(name = "payed_at")
    private ZonedDateTime payedAt;

    @Column(name = "token")
    private String token;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public Contestant lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Contestant firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String geteMail() {
        return eMail;
    }

    public Contestant eMail(String eMail) {
        this.eMail = eMail;
        return this;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public Strength getStrength() {
        return strength;
    }

    public Contestant strength(Strength strength) {
        this.strength = strength;
        return this;
    }

    public void setStrength(Strength strength) {
        this.strength = strength;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public Contestant totalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
        return this;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Boolean isConfirmed() {
        return confirmed;
    }

    public Contestant confirmed(Boolean confirmed) {
        this.confirmed = confirmed;
        return this;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public ZonedDateTime getConfirmedAt() {
        return confirmedAt;
    }

    public Contestant confirmedAt(ZonedDateTime confirmedAt) {
        this.confirmedAt = confirmedAt;
        return this;
    }

    public void setConfirmedAt(ZonedDateTime confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public Boolean isPayed() {
        return payed;
    }

    public Contestant payed(Boolean payed) {
        this.payed = payed;
        return this;
    }

    public void setPayed(Boolean payed) {
        this.payed = payed;
    }

    public ZonedDateTime getPayedAt() {
        return payedAt;
    }

    public Contestant payedAt(ZonedDateTime payedAt) {
        this.payedAt = payedAt;
        return this;
    }

    public void setPayedAt(ZonedDateTime payedAt) {
        this.payedAt = payedAt;
    }

    public String getToken() {
        return token;
    }

    public Contestant token(String token) {
        this.token = token;
        return this;
    }

    public void setToken(String token) {
        this.token = token;
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
        Contestant contestant = (Contestant) o;
        if (contestant.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contestant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Contestant{" +
            "id=" + getId() +
            ", lastName='" + getLastName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", eMail='" + geteMail() + "'" +
            ", strength='" + getStrength() + "'" +
            ", totalPoints=" + getTotalPoints() +
            ", confirmed='" + isConfirmed() + "'" +
            ", confirmedAt='" + getConfirmedAt() + "'" +
            ", payed='" + isPayed() + "'" +
            ", payedAt='" + getPayedAt() + "'" +
            ", token='" + getToken() + "'" +
            "}";
    }
}
