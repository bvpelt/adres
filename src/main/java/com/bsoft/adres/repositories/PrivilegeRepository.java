package com.bsoft.adres.repositories;

import com.bsoft.adres.database.PrivilegeDTO;
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
public interface PrivilegeRepository extends PagingAndSortingRepository<PrivilegeDTO, Long>,
        CrudRepository<PrivilegeDTO, Long>,
        JpaSpecificationExecutor<PrivilegeDTO> {

    @Query(value = "SELECT * FROM privilege where name = :privilegename", nativeQuery = true)
    Optional<PrivilegeDTO> findByPrivilegename(final String privilegename);

    @Query(value = "SELECT * FROM privilege where id = :id", nativeQuery = true)
    Optional<PrivilegeDTO> findByPrivilegeId(Long id);

    @Query(value = "SELECT * FROM privilege",
            countQuery = "SELECT * FROM privilege",
            nativeQuery = true)
    List<PrivilegeDTO> findAllByPaged(final Pageable pageable);

    @Query(value = "SELECT * FROM privilege WHERE hash = :hash", nativeQuery = true)
    Optional<PrivilegeDTO> findByHash(@Param("hash") Integer hash);

    @Query(value = "SELECT * FROM privilege",
            countQuery = "SELECT * FROM privilege",
            nativeQuery = true)
    Page<PrivilegeDTO> findAllByPage(PageRequest pageRequest);
}
