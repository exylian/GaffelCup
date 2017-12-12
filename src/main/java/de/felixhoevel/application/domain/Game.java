package de.felixhoevel.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import de.felixhoevel.application.domain.enumeration.Status;

/**
 * A Game.
 */
@Entity
@Table(name = "game")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "calculated")
    private Boolean calculated;

    @ManyToOne
    private Contestant t1P1;

    @ManyToOne
    private Contestant t1P2;

    @ManyToOne
    private Contestant t2P1;

    @ManyToOne
    private Contestant t2P2;

    @ManyToOne
    private GamePoints points;

    @ManyToOne
    private Round round;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public Game status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Boolean isCalculated() {
        return calculated;
    }

    public Game calculated(Boolean calculated) {
        this.calculated = calculated;
        return this;
    }

    public void setCalculated(Boolean calculated) {
        this.calculated = calculated;
    }

    public Contestant getT1P1() {
        return t1P1;
    }

    public Game t1P1(Contestant contestant) {
        this.t1P1 = contestant;
        return this;
    }

    public void setT1P1(Contestant contestant) {
        this.t1P1 = contestant;
    }

    public Contestant getT1P2() {
        return t1P2;
    }

    public Game t1P2(Contestant contestant) {
        this.t1P2 = contestant;
        return this;
    }

    public void setT1P2(Contestant contestant) {
        this.t1P2 = contestant;
    }

    public Contestant getT2P1() {
        return t2P1;
    }

    public Game t2P1(Contestant contestant) {
        this.t2P1 = contestant;
        return this;
    }

    public void setT2P1(Contestant contestant) {
        this.t2P1 = contestant;
    }

    public Contestant getT2P2() {
        return t2P2;
    }

    public Game t2P2(Contestant contestant) {
        this.t2P2 = contestant;
        return this;
    }

    public void setT2P2(Contestant contestant) {
        this.t2P2 = contestant;
    }

    public GamePoints getPoints() {
        return points;
    }

    public Game points(GamePoints gamePoints) {
        this.points = gamePoints;
        return this;
    }

    public void setPoints(GamePoints gamePoints) {
        this.points = gamePoints;
    }

    public Round getRound() {
        return round;
    }

    public Game round(Round round) {
        this.round = round;
        return this;
    }

    public void setRound(Round round) {
        this.round = round;
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
        Game game = (Game) o;
        if (game.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), game.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Game{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", calculated='" + isCalculated() + "'" +
            "}";
    }
}
