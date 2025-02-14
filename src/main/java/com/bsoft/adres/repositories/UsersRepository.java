package com.bsoft.adres.repositories;

import com.bsoft.adres.database.UserDTO;
import org.springframework.data.domain.Page;
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
public interface UsersRepository extends PagingAndSortingRepository<UserDTO, Long>,
        CrudRepository<UserDTO, Long>,
        JpaSpecificationExecutor<UserDTO> {

    /*
    Find active user based on username
     */

    //@Query(value = "SELECT * FROM users where username = :username and account_non_expired = true and account_non_locked = true and credentials_non_expired = true and enabled = true", nativeQuery = true)
    @Query(value = "SELECT * FROM users where username = :username", nativeQuery = true)
    Optional<UserDTO> findByUserName(final String username);

    /*
    Find active user based on email
     */
    @Query(value = "SELECT * FROM users where email = :email and account_non_expired = true and account_non_locked = true and credentials_non_expired = true and enabled = true", nativeQuery = true)
    Optional<UserDTO> findByEmail(final String email);

    /*
    Find user based on id regardless of locking or any status
     */
    @Query(value = "SELECT * FROM users where id = :Id", nativeQuery = true)
    Optional<UserDTO> findByUserId(Long Id);

    /*
    @Query(value = "SELECT * FROM users where username = :username", nativeQuery = true)
    Optional<UserDAO> findByUserName(String username);
     */

    /*
    List of all users regardless of locking or any status
     */
    @Query(value = "SELECT * FROM users",
            countQuery = "SELECT * FROM users",
            nativeQuery = true)
    List<UserDTO> findAllByPaged(final Pageable pageable);

    /*
    Lookup for duplicates based on content
     */
    @Query(value = "SELECT * FROM users WHERE hash = :hash", nativeQuery = true)
    Optional<UserDTO> findByHash(@Param("hash") Integer hash);

    @Query(value = "SELECT * FROM users",
            countQuery = "SELECT * FROM users",
            nativeQuery = true)
    Page<UserDTO> findAllByPage(Pageable pageable);
}
