package de.felixhoevel.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A GamePoints.
 */
@Entity
@Table(name = "game_points")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GamePoints implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "s_1_t_1")
    private Integer s1T1;

    @Column(name = "s_1_t_2")
    private Integer s1T2;

    @Column(name = "s_2_t_1")
    private Integer s2T1;

    @Column(name = "s_2_t_2")
    private Integer s2T2;

    @Column(name = "s_3_t_1")
    private Integer s3T1;

    @Column(name = "s_3_t_2")
    private Integer s3T2;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer gets1T1() {
        return s1T1;
    }

    public GamePoints s1T1(Integer s1T1) {
        this.s1T1 = s1T1;
        return this;
    }

    public void sets1T1(Integer s1T1) {
        this.s1T1 = s1T1;
    }

    public Integer gets1T2() {
        return s1T2;
    }

    public GamePoints s1T2(Integer s1T2) {
        this.s1T2 = s1T2;
        return this;
    }

    public void sets1T2(Integer s1T2) {
        this.s1T2 = s1T2;
    }

    public Integer gets2T1() {
        return s2T1;
    }

    public GamePoints s2T1(Integer s2T1) {
        this.s2T1 = s2T1;
        return this;
    }

    public void sets2T1(Integer s2T1) {
        this.s2T1 = s2T1;
    }

    public Integer gets2T2() {
        return s2T2;
    }

    public GamePoints s2T2(Integer s2T2) {
        this.s2T2 = s2T2;
        return this;
    }

    public void sets2T2(Integer s2T2) {
        this.s2T2 = s2T2;
    }

    public Integer gets3T1() {
        return s3T1;
    }

    public GamePoints s3T1(Integer s3T1) {
        this.s3T1 = s3T1;
        return this;
    }

    public void sets3T1(Integer s3T1) {
        this.s3T1 = s3T1;
    }

    public Integer gets3T2() {
        return s3T2;
    }

    public GamePoints s3T2(Integer s3T2) {
        this.s3T2 = s3T2;
        return this;
    }

    public void sets3T2(Integer s3T2) {
        this.s3T2 = s3T2;
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
        GamePoints gamePoints = (GamePoints) o;
        if (gamePoints.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gamePoints.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GamePoints{" +
            "id=" + getId() +
            ", s1T1=" + gets1T1() +
            ", s1T2=" + gets1T2() +
            ", s2T1=" + gets2T1() +
            ", s2T2=" + gets2T2() +
            ", s3T1=" + gets3T1() +
            ", s3T2=" + gets3T2() +
            "}";
    }
}
