package com.bsoft.adres.repositories;

import com.bsoft.adres.database.UserDAO;
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
public interface UsersRepository extends PagingAndSortingRepository<UserDAO, Long>,
        CrudRepository<UserDAO, Long>,
        JpaSpecificationExecutor<UserDAO> {

    @Query(value = "SELECT * FROM \"user\" where username = :username", nativeQuery = true)
    Optional<UserDAO> findByUsername(final String username);

    @Query(value = "SELECT * FROM \"user\" where email = :email", nativeQuery = true)
    Optional<UserDAO> findByEmail(final String email);

    @Query(value = "SELECT * FROM \"user\" where id = :Id", nativeQuery = true)
    Optional<UserDAO> findByUserId(Long Id);

    @Query(value = "SELECT * FROM \"user\"",
            countQuery = "SELECT * FROM \"user\"",
            nativeQuery = true)
    List<UserDAO> findAllByPaged(final Pageable pageable);

    @Query(value = "SELECT * FROM \"user\" WHERE hash = :hash", nativeQuery = true)
    Optional<UserDAO> findByHash(@Param("hash") Integer hash);
}
