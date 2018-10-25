package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Templates;
import io.github.jhipster.application.repository.TemplatesRepository;
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
 * REST controller for managing Templates.
 */
@RestController
@RequestMapping("/api")
public class TemplatesResource {

    private final Logger log = LoggerFactory.getLogger(TemplatesResource.class);

    private static final String ENTITY_NAME = "templates";

    private TemplatesRepository templatesRepository;

    public TemplatesResource(TemplatesRepository templatesRepository) {
        this.templatesRepository = templatesRepository;
    }

    /**
     * POST  /templates : Create a new templates.
     *
     * @param templates the templates to create
     * @return the ResponseEntity with status 201 (Created) and with body the new templates, or with status 400 (Bad Request) if the templates has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/templates")
    @Timed
    public ResponseEntity<Templates> createTemplates(@RequestBody Templates templates) throws URISyntaxException {
        log.debug("REST request to save Templates : {}", templates);
        if (templates.getId() != null) {
            throw new BadRequestAlertException("A new templates cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Templates result = templatesRepository.save(templates);
        return ResponseEntity.created(new URI("/api/templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /templates : Updates an existing templates.
     *
     * @param templates the templates to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated templates,
     * or with status 400 (Bad Request) if the templates is not valid,
     * or with status 500 (Internal Server Error) if the templates couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/templates")
    @Timed
    public ResponseEntity<Templates> updateTemplates(@RequestBody Templates templates) throws URISyntaxException {
        log.debug("REST request to update Templates : {}", templates);
        if (templates.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Templates result = templatesRepository.save(templates);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, templates.getId().toString()))
            .body(result);
    }

    /**
     * GET  /templates : get all the templates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of templates in body
     */
    @GetMapping("/templates")
    @Timed
    public List<Templates> getAllTemplates() {
        log.debug("REST request to get all Templates");
        return templatesRepository.findAll();
    }

    /**
     * GET  /templates/:id : get the "id" templates.
     *
     * @param id the id of the templates to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the templates, or with status 404 (Not Found)
     */
    @GetMapping("/templates/{id}")
    @Timed
    public ResponseEntity<Templates> getTemplates(@PathVariable Long id) {
        log.debug("REST request to get Templates : {}", id);
        Optional<Templates> templates = templatesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(templates);
    }

    /**
     * DELETE  /templates/:id : delete the "id" templates.
     *
     * @param id the id of the templates to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteTemplates(@PathVariable Long id) {
        log.debug("REST request to delete Templates : {}", id);

        templatesRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
