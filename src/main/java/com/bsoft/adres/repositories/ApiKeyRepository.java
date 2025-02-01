package com.bsoft.adres.repositories;

import com.bsoft.adres.database.ApiKeyDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApiKeyRepository extends PagingAndSortingRepository<ApiKeyDTO, Long>,
        CrudRepository<ApiKeyDTO, Long>,
        JpaSpecificationExecutor<ApiKeyDTO> {

    @Query(value = "SELECT * FROM apikey WHERE id = :id", nativeQuery = true)
    Optional<ApiKeyDTO> findById(@Param("id") Long id);

    @Query(value = "SELECT * FROM apikey WHERE apikey = :key", nativeQuery = true)
    Optional<ApiKeyDTO> findByKey(@Param("key") String key);

    @Query(value = "SELECT * FROM adres",
            countQuery = "SELECT * FROM apikey",
            nativeQuery = true)
    List<ApiKeyDTO> findAllByPaged(Pageable pageable);

}
