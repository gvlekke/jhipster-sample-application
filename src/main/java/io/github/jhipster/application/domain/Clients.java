package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Clients.
 */
@Entity
@Table(name = "clients")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Clients implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "clients")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Users> users = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Clients name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Users> getUsers() {
        return users;
    }

    public Clients users(Set<Users> users) {
        this.users = users;
        return this;
    }

    public Clients addUsers(Users users) {
        this.users.add(users);
        users.setClients(this);
        return this;
    }

    public Clients removeUsers(Users users) {
        this.users.remove(users);
        users.setClients(null);
        return this;
    }

    public void setUsers(Set<Users> users) {
        this.users = users;
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
        Clients clients = (Clients) o;
        if (clients.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clients.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Clients{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
