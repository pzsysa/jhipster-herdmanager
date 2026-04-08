package com.nucleus.herdmanager.web.rest;

import com.nucleus.herdmanager.domain.Seed;
import com.nucleus.herdmanager.repository.SeedRepository;
import com.nucleus.herdmanager.service.SeedService;
import com.nucleus.herdmanager.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.nucleus.herdmanager.domain.Seed}.
 */
@RestController
@RequestMapping("/api")
public class SeedResource {

    private final Logger log = LoggerFactory.getLogger(SeedResource.class);

    private static final String ENTITY_NAME = "seed";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SeedService seedService;

    private final SeedRepository seedRepository;

    public SeedResource(SeedService seedService, SeedRepository seedRepository) {
        this.seedService = seedService;
        this.seedRepository = seedRepository;
    }

    /**
     * {@code POST  /seeds} : Create a new seed.
     *
     * @param seed the seed to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new seed, or with status {@code 400 (Bad Request)} if the seed has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/seeds")
    public ResponseEntity<Seed> createSeed(@Valid @RequestBody Seed seed) throws URISyntaxException {
        log.debug("REST request to save Seed : {}", seed);
        if (seed.getId() != null) {
            throw new BadRequestAlertException("A new seed cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Seed result = seedService.save(seed);
        return ResponseEntity
            .created(new URI("/api/seeds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /seeds/:id} : Updates an existing seed.
     *
     * @param id the id of the seed to save.
     * @param seed the seed to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seed,
     * or with status {@code 400 (Bad Request)} if the seed is not valid,
     * or with status {@code 500 (Internal Server Error)} if the seed couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/seeds/{id}")
    public ResponseEntity<Seed> updateSeed(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Seed seed)
        throws URISyntaxException {
        log.debug("REST request to update Seed : {}, {}", id, seed);
        if (seed.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, seed.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!seedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Seed result = seedService.update(seed);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, seed.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /seeds/:id} : Partial updates given fields of an existing seed, field will ignore if it is null
     *
     * @param id the id of the seed to save.
     * @param seed the seed to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seed,
     * or with status {@code 400 (Bad Request)} if the seed is not valid,
     * or with status {@code 404 (Not Found)} if the seed is not found,
     * or with status {@code 500 (Internal Server Error)} if the seed couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/seeds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Seed> partialUpdateSeed(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Seed seed
    ) throws URISyntaxException {
        log.debug("REST request to partial update Seed partially : {}, {}", id, seed);
        if (seed.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, seed.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!seedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Seed> result = seedService.partialUpdate(seed);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createAlert(applicationName, "A seed is updated with identifier " + seed.getId(), seed.getId().toString())
        );
    }

    /**
     * {@code GET  /seeds} : get all the seeds.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of seeds in body.
     */
    @GetMapping("/seeds")
    public ResponseEntity<List<Seed>> getAllSeeds(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Seeds");
        Page<Seed> page = seedService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /seeds/:id} : get the "id" seed.
     *
     * @param id the id of the seed to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the seed, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/seeds/{id}")
    public ResponseEntity<Seed> getSeed(@PathVariable Long id) {
        log.debug("REST request to get Seed : {}", id);
        Optional<Seed> seed = seedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(seed);
    }

    /**
     * {@code DELETE  /seeds/:id} : delete the "id" seed.
     *
     * @param id the id of the seed to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/seeds/{id}")
    public ResponseEntity<Void> deleteSeed(@PathVariable Long id) {
        log.debug("REST request to delete Seed : {}", id);
        seedService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
