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
    @Query(value = "SELECT * FROM person WHERE personid = :personid", nativeQuery = true)
    Optional<PersonDAO> findByPersonId(@Param("personid") Long personid);

    @Query(value = "SELECT * FROM person WHERE hash = :hash", nativeQuery = true)
    Optional<PersonDAO> findByHash(@Param("hash") Integer hash);

    @Query(value = "SELECT * FROM person",
            countQuery = "SELECT * FROM person",
            nativeQuery = true)
    List<PersonDAO> findAllByPaged(Pageable pageable);
}
