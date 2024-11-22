package com.bsoft.adres.repositories;

import com.bsoft.adres.database.AdresDAO;
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
public interface AdresRepository extends PagingAndSortingRepository<AdresDAO, Long>,
        CrudRepository<AdresDAO, Long>,
        JpaSpecificationExecutor<AdresDAO> {
    @Query(value = "SELECT * FROM adres WHERE id = :id", nativeQuery = true)
    Optional<AdresDAO> findByAdresId(@Param("id") Long id);

    @Query(value = "SELECT * FROM adres WHERE hash = :hash", nativeQuery = true)
    Optional<AdresDAO> findByHash(@Param("hash") Integer hash);

    @Query(value = "SELECT * FROM adres",
            countQuery = "SELECT * FROM adres",
            nativeQuery = true)
    List<AdresDAO> findAllByPaged(Pageable pageable);

    @Query(value = "SELECT adres.* FROM adres, adres_person WHERE adres.adresid = adres_person.adresid and adres_person.personid = :personId", nativeQuery = true)
    List<AdresDAO> findAdresByPersonId(Long personId);
}
