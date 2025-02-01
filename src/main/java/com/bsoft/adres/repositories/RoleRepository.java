package com.bsoft.adres.repositories;

import com.bsoft.adres.database.RolesDTO;
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
public interface RoleRepository extends PagingAndSortingRepository<RolesDTO, Long>,
        CrudRepository<RolesDTO, Long>,
        JpaSpecificationExecutor<RolesDTO> {

    @Query(value = "SELECT * FROM roles where rolename = :rolename", nativeQuery = true)
    Optional<RolesDTO> findByRolename(final String rolename);

    @Query(value = "SELECT * FROM roles where id = :id", nativeQuery = true)
    Optional<RolesDTO> findByRoleId(Long id);

    @Query(value = "SELECT * FROM roles",
            countQuery = "SELECT * FROM role",
            nativeQuery = true)
    List<RolesDTO> findAllByPaged(final Pageable pageable);

    @Query(value = "SELECT * FROM roles WHERE hash = :hash", nativeQuery = true)
    Optional<RolesDTO> findByHash(@Param("hash") Integer hash);

    @Query(value = "SELECT * FROM roles",
            countQuery = "SELECT * FROM roles",
            nativeQuery = true)
    Page<RolesDTO> findAllByPage(PageRequest pageRequest);
}
