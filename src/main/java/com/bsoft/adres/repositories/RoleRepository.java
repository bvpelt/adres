package com.bsoft.adres.repositories;

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
public interface RoleRepository extends PagingAndSortingRepository<RoleDAO, Long>,
        CrudRepository<RoleDAO, Long>,
        JpaSpecificationExecutor<RoleDAO> {

    @Query(value = "SELECT * FROM role where rolename = :rolename", nativeQuery = true)
    Optional<RoleDAO> findByRolename(final String rolename);

    @Query(value = "SELECT * FROM role where id = :id", nativeQuery = true)
    Optional<RoleDAO> findByRoleId(Long id);

    @Query(value = "SELECT * FROM role",
            countQuery = "SELECT * FROM role",
            nativeQuery = true)
    List<RoleDAO> findAllByPaged(final Pageable pageable);

    @Query(value = "SELECT * FROM role WHERE hash = :hash", nativeQuery = true)
    Optional<RoleDAO> findByHash(@Param("hash") Integer hash);

    @Query(value = "SELECT * FROM role",
            countQuery = "SELECT * FROM role",
            nativeQuery = true)
    Page<RoleDAO> findAllByPage(PageRequest pageRequest);
}
