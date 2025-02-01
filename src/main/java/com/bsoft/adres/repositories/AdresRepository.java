package com.bsoft.adres.repositories;

import com.bsoft.adres.database.AdresDTO;
import org.springframework.data.domain.Page;
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
public interface AdresRepository extends PagingAndSortingRepository<AdresDTO, Long>,
        CrudRepository<AdresDTO, Long>,
        JpaSpecificationExecutor<AdresDTO> {

    @Query(value = "SELECT * FROM adres WHERE id = :id", nativeQuery = true)
    Optional<AdresDTO> findByAdresId(@Param("id") Long id);

    @Query(value = "SELECT * FROM adres WHERE hash = :hash", nativeQuery = true)
    Optional<AdresDTO> findByHash(@Param("hash") Integer hash);

    @Query(value = "SELECT * FROM adres",
            countQuery = "SELECT * FROM adres",
            nativeQuery = true)
    List<AdresDTO> findAllByPaged(Pageable pageable);

    @Query(value = "SELECT * FROM adres",
            countQuery = "SELECT * FROM adres",
            nativeQuery = true)
    Page<AdresDTO> findAllByPage(Pageable pageable);

    @Query(value = "SELECT adres.* FROM adres, adres_person WHERE adres.id = adres_person.adresid and adres_person.personid = :personId", nativeQuery = true)
    List<AdresDTO> findAdresByPersonId(Long personId);
}
