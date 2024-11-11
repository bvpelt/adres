package com.bsoft.adres.repositories;

import com.bsoft.adres.database.UserDAO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserDAO, Long>,
        CrudRepository<UserDAO, Long>,
        JpaSpecificationExecutor<UserDAO> {
}
