package com.bsoft.adres.repositories;

import com.bsoft.adres.database.RoleDAO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends PagingAndSortingRepository<RoleDAO, Long>,
        CrudRepository<RoleDAO, Long>,
        JpaSpecificationExecutor<RoleDAO> {
}
