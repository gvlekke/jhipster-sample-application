package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Templates.
 */
@Entity
@Table(name = "templates")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Templates implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "jhi_file")
    private byte[] file;

    @Column(name = "jhi_file_content_type")
    private String fileContentType;

    @ManyToOne
    @JsonIgnoreProperties("templates")
    private SusteenUsers susteenUsers;

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

    public Templates name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getFile() {
        return file;
    }

    public Templates file(byte[] file) {
        this.file = file;
        return this;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public Templates fileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
        return this;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public SusteenUsers getSusteenUsers() {
        return susteenUsers;
    }

    public Templates susteenUsers(SusteenUsers susteenUsers) {
        this.susteenUsers = susteenUsers;
        return this;
    }

    public void setSusteenUsers(SusteenUsers susteenUsers) {
        this.susteenUsers = susteenUsers;
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
        Templates templates = (Templates) o;
        if (templates.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), templates.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Templates{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", file='" + getFile() + "'" +
            ", fileContentType='" + getFileContentType() + "'" +
            "}";
    }
}
