package com.project.catalog_service.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    // derived query
    Optional<ProductEntity> findByCode(String code);

}
