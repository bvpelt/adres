package com.bsoft.adres;

import com.bsoft.adres.database.UserDAO;
import com.bsoft.adres.repositories.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.Assert;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UsersRepository repo;

    // test methods go below

    @Test
    public void testCreateUser() {
        UserDAO user = new UserDAO();
        user.setEmail("ravikumar@gmail.com");
        user.setPassword("ravi2020");
        user.setUsername("Ravi");
        user.setHash(1);


        UserDAO savedUser = repo.save(user);

        UserDAO existUser = entityManager.find(UserDAO.class, savedUser.getId());

        Assert.isTrue(user.getEmail().equals(existUser.getEmail()), "not equal");

        if (existUser != null) {
            entityManager.remove(existUser);
        }
    }

    @Test
    public void testPasswordEncoder01() {

        UserDAO user = new UserDAO();
        user.setEmail("admin-11@gmail.com");
        user.setPassword("12345");
        user.setUsername("admin");
        user.setHash(1);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        log.info("user: {}, password: {} - encoded: {}", user.getUsername(), user.getPassword(), encodedPassword);
        user.setPassword(encodedPassword);
        UserDAO savedUser = repo.save(user);

        UserDAO existUser = entityManager.find(UserDAO.class, savedUser.getId());

        Assert.isTrue(user.getEmail().equals(existUser.getEmail()), "not equal");

        if (existUser != null) {
            entityManager.remove(existUser);
        }
    }

    @Test
    public void testPasswordEncoder02() {

        UserDAO user = new UserDAO();
        user.setEmail("admin-12@gmail.com");
        user.setPassword("12345");
        user.setUsername("user");
        user.setHash(1);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        log.info("user: {}, password: {} - encoded: {}", user.getUsername(), user.getPassword(), encodedPassword);
        user.setPassword(encodedPassword);
        UserDAO savedUser = repo.save(user);

        UserDAO existUser = entityManager.find(UserDAO.class, savedUser.getId());

        Assert.isTrue(user.getEmail().equals(existUser.getEmail()), "not equal");

        if (existUser != null) {
            entityManager.remove(existUser);
        }
    }

    @Test
    public void testPasswordEncoder03() {

        UserDAO user = new UserDAO();
        user.setEmail("admin-13@gmail.com");
        user.setPassword("12345");
        user.setUsername("bvpelt");
        user.setHash(1);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        log.info("user: {}, password: {} - encoded: {}", user.getUsername(), user.getPassword(), encodedPassword);
        user.setPassword(encodedPassword);
        UserDAO savedUser = repo.save(user);

        UserDAO existUser = entityManager.find(UserDAO.class, savedUser.getId());

        Assert.isTrue(user.getEmail().equals(existUser.getEmail()), "not equal");

        if (existUser != null) {
            entityManager.remove(existUser);
        }
    }
}