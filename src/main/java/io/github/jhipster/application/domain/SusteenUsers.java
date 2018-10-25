package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A SusteenUsers.
 */
@Entity
@Table(name = "susteen_users")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SusteenUsers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "jhi_password")
    private String password;

    @ManyToOne
    @JsonIgnoreProperties("users")
    private Clients clients;

    @OneToMany(mappedBy = "susteenUsers")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Templates> templates = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public SusteenUsers username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public SusteenUsers email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public SusteenUsers password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Clients getClients() {
        return clients;
    }

    public SusteenUsers clients(Clients clients) {
        this.clients = clients;
        return this;
    }

    public void setClients(Clients clients) {
        this.clients = clients;
    }

    public Set<Templates> getTemplates() {
        return templates;
    }

    public SusteenUsers templates(Set<Templates> templates) {
        this.templates = templates;
        return this;
    }

    public SusteenUsers addTemplates(Templates templates) {
        this.templates.add(templates);
        templates.setSusteenUsers(this);
        return this;
    }

    public SusteenUsers removeTemplates(Templates templates) {
        this.templates.remove(templates);
        templates.setSusteenUsers(null);
        return this;
    }

    public void setTemplates(Set<Templates> templates) {
        this.templates = templates;
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
        SusteenUsers susteenUsers = (SusteenUsers) o;
        if (susteenUsers.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), susteenUsers.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SusteenUsers{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }
}
