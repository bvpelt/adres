package com.bsoft.adres;

import com.bsoft.adres.database.PrivilegeDAO;
import com.bsoft.adres.database.RoleDAO;
import com.bsoft.adres.database.UserDAO;
import com.bsoft.adres.exceptions.UserExistsException;
import com.bsoft.adres.repositories.PrivilegeRepository;
import com.bsoft.adres.repositories.RoleRepository;
import com.bsoft.adres.repositories.UsersRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("${activeProfile}")
@Rollback(false)
public class UserRepositoryTests {

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PrivilegeRepository privilegeRepository;

    // test methods go below
    @DisplayName("testCreateUser")
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

    @DisplayName("testPasswordEncoder01")
    @Test
    public void testPasswordEncoder01() {

        UserDAO user = new UserDAO();
        user.setEmail("admin-11@gmail.com");
        user.setPassword("12345");
        user.setUsername("admin-11");

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        log.info("user: {}, password: {} - encoded: {}", user.getUsername(), user.getPassword(), encodedPassword);
        user.setPassword(encodedPassword);
        user.genHash();
        UserDAO savedUser = usersRepository.save(user);

        UserDAO existUser = entityManager.find(UserDAO.class, savedUser.getId());

        Assert.isTrue(user.getEmail().equals(existUser.getEmail()), "not equal");

        if (existUser != null) {
            entityManager.remove(existUser);
        }
    }


    @DisplayName("testPasswordEncoder02")
    @Test
    public void testPasswordEncoder02() {

        UserDAO user = new UserDAO();
        user.setEmail("user-12@gmail.com");
        user.setPassword("12345");
        user.setUsername("user-12");

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        log.info("user: {}, password: {} - encoded: {}", user.getUsername(), user.getPassword(), encodedPassword);
        user.setPassword(encodedPassword);
        user.genHash();
        UserDAO savedUser = usersRepository.save(user);

        UserDAO existUser = entityManager.find(UserDAO.class, savedUser.getId());

        Assert.isTrue(user.getEmail().equals(existUser.getEmail()), "not equal");

        if (existUser != null) {
            entityManager.remove(existUser);
        }
    }


    @DisplayName("testPasswordEncoder03")
    @Test
    public void testPasswordEncoder03() {

        UserDAO user = new UserDAO();
        user.setEmail("bvpelt-13@gmail.com");
        user.setPassword("12345");
        user.setUsername("bvpelt-13");

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        log.info("user: {}, password: {} - encoded: {}", user.getUsername(), user.getPassword(), encodedPassword);
        user.setPassword(encodedPassword);
        user.genHash();
        UserDAO savedUser = usersRepository.save(user);

        UserDAO existUser = entityManager.find(UserDAO.class, savedUser.getId());

        Assert.isTrue(user.getEmail().equals(existUser.getEmail()), "not equal");

        if (existUser != null) {
            entityManager.remove(existUser);
        }
    }


    @DisplayName("testUserRoles")
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
            user.setEmail("test-user-14@gmail.com");
            user.setPassword("12345");
            user.setUsername("test-user-14");
            user.setHash(user.hashCode());
            user.setRoles(roles);

            String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
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

            List<GrantedAuthority> authorities = new ArrayList<>();

            savedUser.getRoles().forEach(role1 -> {
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


    @DisplayName("secUserTest")
    @Test
    public void secUserTest() {
        boolean existUser1 = false;
        boolean existUser2 = false;
        Optional<UserDAO> userByName1 = Optional.empty();
        Optional<UserDAO> userByEmail1 = Optional.empty();
        Optional<UserDAO> userByName2 = Optional.empty();
        Optional<UserDAO> userByEmail2 = Optional.empty();
        try {
            Optional<RoleDAO> optionalRoleDAO = roleRepository.findByRolename("USER");
            RoleDAO defRole = null;

            if (optionalRoleDAO.isPresent()) {
                defRole = optionalRoleDAO.get();
            } else {
                RoleDAO roleDAO = new RoleDAO();
                roleDAO.setRolename("USER");
                roleDAO.setDescription("JWT ROLE");
                defRole = roleRepository.save(roleDAO);
            }

            String userName = "JWT-Test";
            String email = "@gmail.com";
            userByName1 = usersRepository.findByUserName(userName);
            userByEmail1 = usersRepository.findByEmail(email);
            existUser1 = userByName1.isPresent() || userByEmail1.isPresent();

            if (existUser1) {
                throw new UserExistsException("User already exists");
            }
            var user1 = new UserDAO();
            user1.setUsername(userName + "01");
            user1.setPassword(bCryptPasswordEncoder.encode("JWTPASSWORD"));
            defRole.addUser(user1);
            user1.getRoles().add(defRole);
            user1.setEmail(userName + "01" + email);
            user1.genHash();

            try {
                UserDAO newUser1 = usersRepository.save(user1);
                userByName1 = Optional.of(newUser1);
            } catch (Exception e) {
                log.error("User: {} not saved, error: ", user1.getUsername(), e);
                throw new UserExistsException("User already exists");
            }

            userByName2 = usersRepository.findByUserName(userName);
            userByEmail2 = usersRepository.findByEmail(email);
            existUser2 = userByName2.isPresent() || userByEmail2.isPresent();

            if (existUser2) {
                throw new UserExistsException("User already exists");
            }
            var user2 = new UserDAO();
            user2.setUsername(userName + "02");
            user2.setPassword(bCryptPasswordEncoder.encode("JWTPASSWORD"));
            defRole.addUser(user2);
            user2.getRoles().add(defRole);
            user2.setEmail(userName + "02" + email);
            user2.genHash();
            try {
                UserDAO newUser2 = usersRepository.save(user2);
                userByName2 = Optional.of(newUser2);
            } catch (Exception e) {
                log.error("User: {} not saved, error: ", user2.getUsername(), e);
                throw new UserExistsException("User already exists");
            }
        } catch (Exception e) {
        } finally {
            if (userByName1.isPresent()) {
                if (userByEmail1.isPresent()) {
                    if (!userByName1.get().getId().equals(userByEmail1.get().getId())) {
                        entityManager.remove(userByEmail1.get());
                    }
                }
                entityManager.remove(userByName1.get());
            } else {
                if (userByEmail1.isPresent()) entityManager.remove(userByEmail1.get());
            }

            if (userByName2.isPresent()) {
                if (userByEmail2.isPresent()) {
                    if (!userByName2.get().getId().equals(userByEmail2.get().getId())) {
                        entityManager.remove(userByEmail2.get());
                    }
                }
                entityManager.remove(userByName2.get());
            } else {
                if (userByEmail2.isPresent()) entityManager.remove(userByEmail2.get());
            }

        }
    }

    @DisplayName("Generate privileges")
    @Test
    public void generatePrivileges() {
        PrivilegeDAO privilegeDAO = new PrivilegeDAO();
        ArrayList<String> privileges = new ArrayList<>();
        privileges.add("ALL");
        privileges.add("APP_FULL_ACCESS");
        privileges.add("APP_SECURITY_ACCESS");
        privileges.add("APP_READ_ACCESS_BASIC");
        privileges.add("APP_READ_ACCESS_JWT");

        privileges.forEach(privilege -> {
            privilegeDAO.setName(privilege);
            privilegeDAO.genHash();
            log.info("generatePrivileges {}: {}", privilege, privilegeDAO);
        });
    }


    @DisplayName("Generate roles")
    @Test
    public void generateRoles() {
        RoleDAO roleDAO = new RoleDAO();

        ArrayList<Pair> roles = new ArrayList<>();
        roles.add(new Pair("ADMIN", "Administrator"));
        roles.add(new Pair("OPERATOR", "Operator"));
        roles.add(new Pair("USER", "Application user with basic authentication"));
        roles.add(new Pair("JWT-TOKEN", "Application user with JWT token"));


        roles.forEach(pair -> {
            roleDAO.setRolename(pair.getName());
            roleDAO.setDescription(pair.getDescription());
            roleDAO.genHash();
            log.info("generateRoles {}: {}", roleDAO.getRolename(), roleDAO);
        });

    }

    @DisplayName("Generate users")
    @Test
    public void generateUsers() {
        UserDAO userDAO = new UserDAO();

        ArrayList<Quart> users = new ArrayList<>();
        users.add(new Quart("admin","12345", "admin@gmail.com", "0612345678"));
        users.add(new Quart("bvpelt","12345", "bvpelt@gmail.com", "0656789012"));
        users.add(new Quart("user","12345", "user@gmail.com", "0678901234"));

        users.forEach(quart -> {
            userDAO.setUsername(quart.getUsername());
            userDAO.setPassword(quart.getPassword());
            userDAO.setEmail(quart.getEmail());
            userDAO.setPhone(quart.getPhone());
            userDAO.genHash();
            log.info("generateUsers {}: {}", userDAO.getUsername(), userDAO.toString());
        });
    }
}
