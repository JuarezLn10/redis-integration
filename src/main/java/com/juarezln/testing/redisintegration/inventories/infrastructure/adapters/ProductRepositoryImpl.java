package com.juarezln.testing.redisintegration.inventories.infrastructure.adapters;

import com.juarezln.testing.redisintegration.inventories.domain.model.aggregates.Product;
import com.juarezln.testing.redisintegration.inventories.domain.repositories.ProductRepository;
import com.juarezln.testing.redisintegration.inventories.infrastructure.persistence.jpa.assemblers.ProductPersistenceAssembler;
import com.juarezln.testing.redisintegration.inventories.infrastructure.persistence.jpa.repositories.ProductPersistenceRepository;
import com.juarezln.testing.redisintegration.inventories.infrastructure.persistence.redis.assemblers.ProductCacheAssembler;
import com.juarezln.testing.redisintegration.inventories.infrastructure.persistence.redis.repositories.ProductCacheRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * ProductRepositoryImpl is an implementation of the ProductRepository interface that uses JPA for persistence.
 * It serves as an adapter between the domain layer and the persistence layer, allowing the application to interact with the database without being tightly coupled to the persistence technology.
 */
@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    // The productJpaRepository is responsible for interacting with the database using JPA.
    private final ProductPersistenceRepository productJpaRepository;

    // The productCacheRepository is responsible for interacting with the Redis cache to store and retrieve product data, improving performance by reducing database access.
    private final ProductCacheRepository productCacheRepository;

    /**
     * @inheritDocs
     */
    @Override
    public Product save(Product product) {
        var saved = productJpaRepository.save(ProductPersistenceAssembler.toPersistenceFromDomain(product));
        return ProductPersistenceAssembler.toDomainFromPersistence(saved);
    }

    /**
     * @inheritDocs
     */
    @Override
    public Optional<Product> findById(Long id) {
        Optional<Product> cached =
                productCacheRepository.findById(id.toString())
                        .map(ProductCacheAssembler::toDomainFromCache);

        if (cached.isPresent()) {
            return cached;
        }

        Optional<Product> product =
                productJpaRepository.findById(id)
                        .map(ProductPersistenceAssembler::toDomainFromPersistence);

        product.ifPresent(p ->
                productCacheRepository.save(
                        ProductCacheAssembler.toCacheFromDomain(p)
                ));

        return product;
    }

    /**
     * @inheritDocs
     */
    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll()
                .stream()
                .map(ProductPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    /**
     * @inheritDocs
     */
    @Override
    public boolean existsByName(String name) {
        return productJpaRepository.existsByName(name);
    }

    /**
     * @inheritDocs
     */
    @Override
    public boolean existsById(Long id) {
        return productJpaRepository.existsById(id);
    }

    /**
     * @inheritDocs
     */
    @Override
    public boolean existsByNameAndItIsNot(String name, Long id) {
        return productJpaRepository.existsByNameAndIdIsNot(name, id);
    }
}
