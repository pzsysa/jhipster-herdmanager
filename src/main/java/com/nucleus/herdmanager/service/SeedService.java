package com.nucleus.herdmanager.service;

import com.nucleus.herdmanager.domain.Seed;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Seed}.
 */
public interface SeedService {
    /**
     * Save a seed.
     *
     * @param seed the entity to save.
     * @return the persisted entity.
     */
    Seed save(Seed seed);

    /**
     * Updates a seed.
     *
     * @param seed the entity to update.
     * @return the persisted entity.
     */
    Seed update(Seed seed);

    /**
     * Partially updates a seed.
     *
     * @param seed the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Seed> partialUpdate(Seed seed);

    /**
     * Get all the seeds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Seed> findAll(Pageable pageable);

    /**
     * Get the "id" seed.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Seed> findOne(Long id);

    /**
     * Delete the "id" seed.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
