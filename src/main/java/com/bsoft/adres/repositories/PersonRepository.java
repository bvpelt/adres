package com.bsoft.adres.repositories;

import com.bsoft.adres.database.PersonDAO;
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
public interface PersonRepository extends PagingAndSortingRepository<PersonDAO, Long>,
        CrudRepository<PersonDAO, Long>,
        JpaSpecificationExecutor<PersonDAO> {
    @Query(value = "SELECT * FROM person WHERE id = :id", nativeQuery = true)
    Optional<PersonDAO> findByPersonId(@Param("id") Long id);

    @Query(value = "SELECT * FROM person WHERE hash = :hash", nativeQuery = true)
    Optional<PersonDAO> findByHash(@Param("hash") Integer hash);

    @Query(value = "SELECT * FROM person",
            countQuery = "SELECT * FROM person",
            nativeQuery = true)
    List<PersonDAO> findAllByPaged(Pageable pageable);

    @Query(value = "SELECT person.* FROM person, adres_person WHERE person.id = adres_person.personid and adres_person.adresid = :adresId", nativeQuery = true)
    List<PersonDAO> findPersonsByAdresId(Long adresId);
}
