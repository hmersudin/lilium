package com.lilium.jpatutorial.impl;

import com.lilium.jpatutorial.entity.DistributedEntity_;
import com.lilium.jpatutorial.repository.DistributedRepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.util.List;

public class DistributedRepositoryImpl<ENTITY> extends SimpleJpaRepository<ENTITY, String>
        implements DistributedRepository<ENTITY> {
    public DistributedRepositoryImpl(final JpaEntityInformation<ENTITY, String> entityInformation,
                                     final EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    @Override
    public List<ENTITY> findAllCreatedSince(Long timestamp) {
        return super.findAll(createdSince(timestamp));
    }

    @Override
    public List<ENTITY> findAll(Specification<ENTITY> specification) {
        return super.findAll(specification);
    }

    /** Here we wrote a query how to select based on the created timestamp!
     *
     * Used to find all entities created since forwarded timestamp.
     *
     * @param timestamp Timestamp since which entities were created.
     * @return Returns created specification.
     */
    private Specification<ENTITY> createdSince(Long timestamp) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(
                root.get(DistributedEntity_.CREATED_TIMESTAMP),
                timestamp
        );
    }
}
