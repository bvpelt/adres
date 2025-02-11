package com.bsoft.adres.repositories;

import com.bsoft.adres.database.PersonDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
public interface PersonRepository extends PagingAndSortingRepository<PersonDTO, Long>,
        CrudRepository<PersonDTO, Long>,
        JpaSpecificationExecutor<PersonDTO> {
    @Query(value = "SELECT * FROM person WHERE id = :id", nativeQuery = true)
    Optional<PersonDTO> findByPersonId(@Param("id") Long id);

    @Query(value = "SELECT * FROM person WHERE hash = :hash", nativeQuery = true)
    Optional<PersonDTO> findByHash(@Param("hash") Integer hash);

    @Query(value = "SELECT * FROM person",
            countQuery = "SELECT * FROM person",
            nativeQuery = true)
    List<PersonDTO> findAllByPaged(Pageable pageable);

    @Query(value = "SELECT person.* FROM person, adres_person WHERE person.id = adres_person.personid and adres_person.adresid = :adresId", nativeQuery = true)
    List<PersonDTO> findPersonsByAdresId(Long adresId);

    @Query(value = "SELECT * FROM person",
            countQuery = "SELECT * FROM person",
            nativeQuery = true)
    Page<PersonDTO> findAllByPage(PageRequest pageRequest);

    @Query(value = "SELECT * from person where id not in (select p.id from person p, adres_person ap where p.id = ap.personid and ap.adresid = :adresId)",
            countQuery = "SELECT * from person where id not in (select p.id from person p, adres_person ap where p.id = ap.personid and ap.adresid = :adresId)",
            nativeQuery = true)
    Page<PersonDTO> findAllByAdresIdAndPaged(Long adresId, PageRequest pageRequest);
}
