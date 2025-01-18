package com.bsoft.adres.repositories;

import com.bsoft.adres.database.RolesDAO;
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
public interface RoleRepository extends PagingAndSortingRepository<RolesDAO, Long>,
        CrudRepository<RolesDAO, Long>,
        JpaSpecificationExecutor<RolesDAO> {

    @Query(value = "SELECT * FROM role where rolename = :rolename", nativeQuery = true)
    Optional<RolesDAO> findByRolename(final String rolename);

    @Query(value = "SELECT * FROM role where id = :id", nativeQuery = true)
    Optional<RolesDAO> findByRoleId(Long id);

    @Query(value = "SELECT * FROM role",
            countQuery = "SELECT * FROM role",
            nativeQuery = true)
    List<RolesDAO> findAllByPaged(final Pageable pageable);

    @Query(value = "SELECT * FROM role WHERE hash = :hash", nativeQuery = true)
    Optional<RolesDAO> findByHash(@Param("hash") Integer hash);

    @Query(value = "SELECT * FROM role",
            countQuery = "SELECT * FROM role",
            nativeQuery = true)
    Page<RolesDAO> findAllByPage(PageRequest pageRequest);
}
