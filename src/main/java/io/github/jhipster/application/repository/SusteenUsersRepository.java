package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.SusteenUsers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SusteenUsers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SusteenUsersRepository extends JpaRepository<SusteenUsers, Long> {

}
