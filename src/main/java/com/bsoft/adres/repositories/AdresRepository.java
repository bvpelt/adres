package com.bsoft.adres.repositories;

import com.bsoft.adres.database.AdresDAO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdresRepository extends PagingAndSortingRepository<AdresDAO, Long>,
        CrudRepository<AdresDAO, Long>,
        JpaSpecificationExecutor<AdresDAO> {
    @Query(value = "SELECT * FROM adres WHERE adresid = :adresid", nativeQuery = true)
    Optional<AdresDAO> findByAdresId(@Param("adresid") Long adresid);
    @Query(value = "SELECT * FROM adres WHERE hash = :hash", nativeQuery = true)
    Optional<AdresDAO> findByHash(@Param("hash") Integer hash);
}
