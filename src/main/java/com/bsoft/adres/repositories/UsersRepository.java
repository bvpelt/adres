package com.bsoft.adres.repositories;

import com.bsoft.adres.database.AdresDAO;
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

    @Query(value = "SELECT user.* FROM users where username = :username" , nativeQuery = true)
    Optional<UserDAO> findByUsername(final String username);

    @Query(value = "SELECT user.* FROM users where userid = :userId" , nativeQuery = true)
    Optional<UserDAO> findByUserId(Long userId);

    @Query(value = "SELECT * FROM users",
            countQuery = "SELECT * FROM users",
            nativeQuery = true)
    List<UserDAO> findAllByPaged(Pageable pageable);

    @Query(value = "SELECT * FROM users WHERE hash = :hash", nativeQuery = true)
    Optional<UserDAO> findByHash(@Param("hash") Integer hash);
}
