package com.bsoft.adres.repositories;

import com.bsoft.adres.database.PrivilegeDAO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends PagingAndSortingRepository<PrivilegeDAO, Long>,
        CrudRepository<PrivilegeDAO, Long>,
        JpaSpecificationExecutor<PrivilegeDAO> {
}
