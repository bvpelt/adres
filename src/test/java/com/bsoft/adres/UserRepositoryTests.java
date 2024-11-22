package com.bsoft.adres;

import com.bsoft.adres.database.PrivilegeDAO;
import com.bsoft.adres.database.RoleDAO;
import com.bsoft.adres.database.UserDAO;
import com.bsoft.adres.repositories.PrivilegeRepository;
import com.bsoft.adres.repositories.RoleRepository;
import com.bsoft.adres.repositories.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PrivilegeRepository privilegeRepository;

    // test methods go below

    @Test
    public void testCreateUser() {
        UserDAO user = new UserDAO();
        user.setEmail("ravikumar@gmail.com");
        user.setPassword("ravi2020");
        user.setUsername("Ravi");
        user.setHash(1);


        UserDAO savedUser = usersRepository.save(user);

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

        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        log.info("user: {}, password: {} - encoded: {}", user.getUsername(), user.getPassword(), encodedPassword);
        user.setPassword(encodedPassword);
        UserDAO savedUser = usersRepository.save(user);

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

        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        log.info("user: {}, password: {} - encoded: {}", user.getUsername(), user.getPassword(), encodedPassword);
        user.setPassword(encodedPassword);
        UserDAO savedUser = usersRepository.save(user);

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

        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        log.info("user: {}, password: {} - encoded: {}", user.getUsername(), user.getPassword(), encodedPassword);
        user.setPassword(encodedPassword);
        UserDAO savedUser = usersRepository.save(user);

        UserDAO existUser = entityManager.find(UserDAO.class, savedUser.getId());

        Assert.isTrue(user.getEmail().equals(existUser.getEmail()), "not equal");

        if (existUser != null) {
            entityManager.remove(existUser);
        }
    }

    @Test
    public void testUserRoles() {

        UserDAO existUser = null;
        RoleDAO existRole = null;
        PrivilegeDAO existprivilege = null;
        PrivilegeDAO existprivilege2 = null;

        try {
            PrivilegeDAO privilege = new PrivilegeDAO();
            privilege.setName("TEST_READ");
            privilege.setHash(privilege.hashCode());


            PrivilegeDAO savedprivilege = privilegeRepository.save(privilege);
            existprivilege = entityManager.find(PrivilegeDAO.class, savedprivilege.getId());

            Assert.isTrue(privilege.getName().equals(existprivilege.getName()), "not equal");
            List<PrivilegeDAO> privileges = new ArrayList<>();
            privileges.add(savedprivilege);

            PrivilegeDAO privilege2 = new PrivilegeDAO();
            privilege2.setName("TEST_WRITE");
            privilege2.setHash(privilege2.hashCode());


            PrivilegeDAO savedprivilege2 = privilegeRepository.save(privilege2);
            existprivilege2 = entityManager.find(PrivilegeDAO.class, savedprivilege2.getId());

            Assert.isTrue(privilege2.getName().equals(existprivilege2.getName()), "not equal");
            privileges.add(savedprivilege2);


            RoleDAO role = new RoleDAO();
            role.setRolename("TEST");
            role.setDescription("Test rol");
            role.setPrivileges(privileges);
            role.setHash(role.hashCode());

            RoleDAO savedRole = roleRepository.save(role);
            existRole = entityManager.find(RoleDAO.class, savedRole.getId());

            Assert.isTrue(role.getRolename().equals(existRole.getRolename()), "not equal");
            List<RoleDAO> roles = new ArrayList<>();
            roles.add(savedRole);

            UserDAO user = new UserDAO();
            user.setEmail("admin-23@gmail.com");
            user.setPassword("12345");
            user.setUsername("bvpelt");
            user.setHash(user.hashCode());
            user.setRoles(roles);

            //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            log.info("user: {}, password: {} - encoded: {}", user.getUsername(), user.getPassword(), encodedPassword);
            user.setPassword(encodedPassword);
            UserDAO savedUser = usersRepository.save(user);

            existUser = entityManager.find(UserDAO.class, savedUser.getId());

            Assert.isTrue(user.getEmail().equals(existUser.getEmail()), "not equal");

            log.info("privilege: {}", privilege);
            privilege.getRoles().forEach(role1 -> {
                log.info("privilege: {} -- role: {}", privilege.getName(), role1.getRolename());
            });
            log.info("privilege2: {}", privilege2);
            privilege2.getRoles().forEach(role1 -> {
                log.info("privilege: {} -- role: {}", privilege2.getName(), role1.getRolename());
            });
            log.info("role: {}", savedRole);
            log.info("user: {}", savedUser);

/*
            List<GrantedAuthority> authorities = new ArrayList<>();

            Collection<RoleDAO> knownRoles = savedUser.getRoles();
            for(RoleDAO role1 : knownRoles) {
                authorities.add(new SimpleGrantedAuthority(role1.getRolename()));
                log.info("Add role: {}", role.getRolename());
            }

            savedUser.getRoles().forEach(role1 -> {
                //authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
                authorities.add(new SimpleGrantedAuthority(role1.getRolename()));
                log.info("Add role: {}", role.getRolename());
            });

             */

            List<GrantedAuthority> authorities = new ArrayList<>();

            savedUser.getRoles().forEach(role1 -> {
                //authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
                //authorities.add(new SimpleGrantedAuthority(role.getRolename()));
                log.info("get role: {}", role1.getRolename());
                role1.getPrivileges().forEach(principle1 -> {
                    log.info("get principle: {}", principle1.getName());
                    authorities.add(new SimpleGrantedAuthority(principle1.getName()));
                });
            });

        } catch (Exception e) {
            log.error("Error: {} ", e);
        } finally {
            if (existUser != null) {
                entityManager.remove(existUser);
            }
            if (existRole != null) {
                entityManager.remove(existRole);
            }
            if (existprivilege != null) {
                entityManager.remove(existprivilege);
            }
            if (existprivilege2 != null) {
                entityManager.remove(existprivilege2);
            }
        }
    }
}