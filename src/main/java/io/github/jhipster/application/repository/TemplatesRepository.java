package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Templates;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Templates entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TemplatesRepository extends JpaRepository<Templates, Long> {

}
