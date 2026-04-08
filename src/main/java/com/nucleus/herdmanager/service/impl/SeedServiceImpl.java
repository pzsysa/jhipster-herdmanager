package com.nucleus.herdmanager.service.impl;

import com.nucleus.herdmanager.domain.Seed;
import com.nucleus.herdmanager.repository.SeedRepository;
import com.nucleus.herdmanager.service.SeedService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Seed}.
 */
@Service
@Transactional
public class SeedServiceImpl implements SeedService {

    private final Logger log = LoggerFactory.getLogger(SeedServiceImpl.class);

    private final SeedRepository seedRepository;

    public SeedServiceImpl(SeedRepository seedRepository) {
        this.seedRepository = seedRepository;
    }

    @Override
    public Seed save(Seed seed) {
        log.debug("Request to save Seed : {}", seed);
        return seedRepository.save(seed);
    }

    @Override
    public Seed update(Seed seed) {
        log.debug("Request to save Seed : {}", seed);
        return seedRepository.save(seed);
    }

    @Override
    public Optional<Seed> partialUpdate(Seed seed) {
        log.debug("Request to partially update Seed : {}", seed);

        return seedRepository
            .findById(seed.getId())
            .map(existingSeed -> {
                if (seed.getName() != null) {
                    existingSeed.setName(seed.getName());
                }
                if (seed.getCropType() != null) {
                    existingSeed.setCropType(seed.getCropType());
                }
                if (seed.getRegion() != null) {
                    existingSeed.setRegion(seed.getRegion());
                }
                if (seed.getRainfallPattern() != null) {
                    existingSeed.setRainfallPattern(seed.getRainfallPattern());
                }
                if (seed.getWaterRequirementMmToEstablish() != null) {
                    existingSeed.setWaterRequirementMmToEstablish(seed.getWaterRequirementMmToEstablish());
                }
                if (seed.getNotes() != null) {
                    existingSeed.setNotes(seed.getNotes());
                }

                return existingSeed;
            })
            .map(seedRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Seed> findAll(Pageable pageable) {
        log.debug("Request to get all Seeds");
        return seedRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Seed> findOne(Long id) {
        log.debug("Request to get Seed : {}", id);
        return seedRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Seed : {}", id);
        seedRepository.deleteById(id);
    }
}
