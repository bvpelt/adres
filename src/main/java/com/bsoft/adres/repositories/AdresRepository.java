package com.bsoft.adres.repositories;

import com.bsoft.adres.database.AdresDAO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdresRepository extends PagingAndSortingRepository<AdresDAO, Long>,
        CrudRepository<AdresDAO, Long>,
        JpaSpecificationExecutor<AdresDAO> {
}
