package com.bsoft.adres.repositories;

import com.bsoft.adres.database.PrivilegeDAO;
import com.bsoft.adres.database.RoleDAO;
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
public interface PrivilegeRepository extends PagingAndSortingRepository<PrivilegeDAO, Long>,
        CrudRepository<PrivilegeDAO, Long>,
        JpaSpecificationExecutor<PrivilegeDAO> {

    @Query(value = "SELECT * FROM privilege where name = :privilegename", nativeQuery = true)
    Optional<PrivilegeDAO> findByPrivilegename(final String privilegename);

    @Query(value = "SELECT * FROM privilege where id = :id", nativeQuery = true)
    Optional<PrivilegeDAO> findByPrivilegeId(Long id);

    @Query(value = "SELECT * FROM privilege",
            countQuery = "SELECT * FROM privilege",
            nativeQuery = true)
    List<PrivilegeDAO> findAllByPaged(final Pageable pageable);

    @Query(value = "SELECT * FROM privilege WHERE hash = :hash", nativeQuery = true)
    Optional<PrivilegeDAO> findByHash(@Param("hash") Integer hash);

    @Query(value = "SELECT * FROM privilege",
            countQuery = "SELECT * FROM privilege",
            nativeQuery = true)
    Page<PrivilegeDAO> findAllByPage(PageRequest pageRequest);
}
