package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.SusteenUsers;
import io.github.jhipster.application.repository.SusteenUsersRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SusteenUsers.
 */
@RestController
@RequestMapping("/api")
public class SusteenUsersResource {

    private final Logger log = LoggerFactory.getLogger(SusteenUsersResource.class);

    private static final String ENTITY_NAME = "susteenUsers";

    private SusteenUsersRepository susteenUsersRepository;

    public SusteenUsersResource(SusteenUsersRepository susteenUsersRepository) {
        this.susteenUsersRepository = susteenUsersRepository;
    }

    /**
     * POST  /susteen-users : Create a new susteenUsers.
     *
     * @param susteenUsers the susteenUsers to create
     * @return the ResponseEntity with status 201 (Created) and with body the new susteenUsers, or with status 400 (Bad Request) if the susteenUsers has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/susteen-users")
    @Timed
    public ResponseEntity<SusteenUsers> createSusteenUsers(@RequestBody SusteenUsers susteenUsers) throws URISyntaxException {
        log.debug("REST request to save SusteenUsers : {}", susteenUsers);
        if (susteenUsers.getId() != null) {
            throw new BadRequestAlertException("A new susteenUsers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SusteenUsers result = susteenUsersRepository.save(susteenUsers);
        return ResponseEntity.created(new URI("/api/susteen-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /susteen-users : Updates an existing susteenUsers.
     *
     * @param susteenUsers the susteenUsers to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated susteenUsers,
     * or with status 400 (Bad Request) if the susteenUsers is not valid,
     * or with status 500 (Internal Server Error) if the susteenUsers couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/susteen-users")
    @Timed
    public ResponseEntity<SusteenUsers> updateSusteenUsers(@RequestBody SusteenUsers susteenUsers) throws URISyntaxException {
        log.debug("REST request to update SusteenUsers : {}", susteenUsers);
        if (susteenUsers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SusteenUsers result = susteenUsersRepository.save(susteenUsers);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, susteenUsers.getId().toString()))
            .body(result);
    }

    /**
     * GET  /susteen-users : get all the susteenUsers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of susteenUsers in body
     */
    @GetMapping("/susteen-users")
    @Timed
    public List<SusteenUsers> getAllSusteenUsers() {
        log.debug("REST request to get all SusteenUsers");
        return susteenUsersRepository.findAll();
    }

    /**
     * GET  /susteen-users/:id : get the "id" susteenUsers.
     *
     * @param id the id of the susteenUsers to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the susteenUsers, or with status 404 (Not Found)
     */
    @GetMapping("/susteen-users/{id}")
    @Timed
    public ResponseEntity<SusteenUsers> getSusteenUsers(@PathVariable Long id) {
        log.debug("REST request to get SusteenUsers : {}", id);
        Optional<SusteenUsers> susteenUsers = susteenUsersRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(susteenUsers);
    }

    /**
     * DELETE  /susteen-users/:id : delete the "id" susteenUsers.
     *
     * @param id the id of the susteenUsers to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/susteen-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteSusteenUsers(@PathVariable Long id) {
        log.debug("REST request to delete SusteenUsers : {}", id);

        susteenUsersRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
