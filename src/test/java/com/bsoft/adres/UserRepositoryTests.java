package com.bsoft.adres;

import com.bsoft.adres.database.PrivilegeDTO;
import com.bsoft.adres.database.RolesDTO;
import com.bsoft.adres.database.UserDTO;
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
@ActiveProfiles("${activeProfile}")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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
        UserDTO user = new UserDTO();
        user.setEmail("ravikumar@gmail.com");
        user.setPassword("ravi2020");
        user.setUsername("Ravi");
        user.setHash(1);


        UserDTO savedUser = usersRepository.save(user);

        UserDTO existUser = entityManager.find(UserDTO.class, savedUser.getId());

        Assert.isTrue(user.getEmail().equals(existUser.getEmail()), "not equal");

        if (existUser != null) {
            entityManager.remove(existUser);
        }
    }

    @DisplayName("testPasswordEncoder01")
    @Test
    public void testPasswordEncoder01() {

        UserDTO user = new UserDTO();
        user.setEmail("admin-11@gmail.com");
        user.setPassword("12345");
        user.setUsername("admin-11");

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        log.info("user: {}, password: {} - encoded: {}", user.getUsername(), user.getPassword(), encodedPassword);
        user.setPassword(encodedPassword);
        user.genHash();
        UserDTO savedUser = usersRepository.save(user);

        UserDTO existUser = entityManager.find(UserDTO.class, savedUser.getId());

        Assert.isTrue(user.getEmail().equals(existUser.getEmail()), "not equal");

        if (existUser != null) {
            entityManager.remove(existUser);
        }
    }


    @DisplayName("testPasswordEncoder02")
    @Test
    public void testPasswordEncoder02() {

        UserDTO user = new UserDTO();
        user.setEmail("user-12@gmail.com");
        user.setPassword("12345");
        user.setUsername("user-12");

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        log.info("user: {}, password: {} - encoded: {}", user.getUsername(), user.getPassword(), encodedPassword);
        user.setPassword(encodedPassword);
        user.genHash();
        UserDTO savedUser = usersRepository.save(user);

        UserDTO existUser = entityManager.find(UserDTO.class, savedUser.getId());

        Assert.isTrue(user.getEmail().equals(existUser.getEmail()), "not equal");

        if (existUser != null) {
            entityManager.remove(existUser);
        }
    }


    @DisplayName("testPasswordEncoder03")
    @Test
    public void testPasswordEncoder03() {

        UserDTO user = new UserDTO();
        user.setEmail("bvpelt-13@gmail.com");
        user.setPassword("12345");
        user.setUsername("bvpelt-13");

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        log.info("user: {}, password: {} - encoded: {}", user.getUsername(), user.getPassword(), encodedPassword);
        user.setPassword(encodedPassword);
        user.genHash();
        UserDTO savedUser = usersRepository.save(user);

        UserDTO existUser = entityManager.find(UserDTO.class, savedUser.getId());

        Assert.isTrue(user.getEmail().equals(existUser.getEmail()), "not equal");

        if (existUser != null) {
            entityManager.remove(existUser);
        }
    }


    @DisplayName("testUserRoles")
    @Test
    public void testUserRoles() {

        UserDTO existUser = null;
        RolesDTO existRole = null;
        PrivilegeDTO existprivilege = null;
        PrivilegeDTO existprivilege2 = null;

        try {
            PrivilegeDTO privilege = new PrivilegeDTO();
            privilege.setName("TEST_READ");
            privilege.setHash(privilege.hashCode());


            PrivilegeDTO savedprivilege = privilegeRepository.save(privilege);
            existprivilege = entityManager.find(PrivilegeDTO.class, savedprivilege.getId());

            Assert.isTrue(privilege.getName().equals(existprivilege.getName()), "not equal");
            List<PrivilegeDTO> privileges = new ArrayList<>();
            privileges.add(savedprivilege);

            PrivilegeDTO privilege2 = new PrivilegeDTO();
            privilege2.setName("TEST_WRITE");
            privilege2.setHash(privilege2.hashCode());


            PrivilegeDTO savedprivilege2 = privilegeRepository.save(privilege2);
            existprivilege2 = entityManager.find(PrivilegeDTO.class, savedprivilege2.getId());

            Assert.isTrue(privilege2.getName().equals(existprivilege2.getName()), "not equal");
            privileges.add(savedprivilege2);


            RolesDTO role = new RolesDTO();
            role.setRolename("TEST");
            role.setDescription("Test rol");
            role.setPrivileges(privileges);
            role.setHash(role.hashCode());

            RolesDTO savedRole = roleRepository.save(role);
            existRole = entityManager.find(RolesDTO.class, savedRole.getId());

            Assert.isTrue(role.getRolename().equals(existRole.getRolename()), "not equal");
            List<RolesDTO> roles = new ArrayList<>();
            roles.add(savedRole);

            UserDTO user = new UserDTO();
            user.setEmail("test-user-14@gmail.com");
            user.setPassword("12345");
            user.setUsername("test-user-14");
            user.setHash(user.hashCode());
            user.setRoles(roles);

            String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
            log.info("user: {}, password: {} - encoded: {}", user.getUsername(), user.getPassword(), encodedPassword);
            user.setPassword(encodedPassword);
            UserDTO savedUser = usersRepository.save(user);

            existUser = entityManager.find(UserDTO.class, savedUser.getId());

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
        Optional<UserDTO> userByName1 = Optional.empty();
        Optional<UserDTO> userByEmail1 = Optional.empty();
        Optional<UserDTO> userByName2 = Optional.empty();
        Optional<UserDTO> userByEmail2 = Optional.empty();

        try {
            Optional<RolesDTO> optionalRoleDAO = roleRepository.findByRolename("USER1");
            RolesDTO defRole = null;

            if (optionalRoleDAO.isPresent()) {
                defRole = optionalRoleDAO.get();
            } else {
                RolesDTO rolesDTO = new RolesDTO();
                rolesDTO.setRolename("USER1");
                rolesDTO.setDescription("JWT ROLE");
                defRole = roleRepository.save(rolesDTO);
            }

            String userName = "JWT-Test";
            String email = "@gmail.com";
            userByName1 = usersRepository.findByUserName(userName);
            userByEmail1 = usersRepository.findByEmail(email);
            existUser1 = userByName1.isPresent() || userByEmail1.isPresent();

            if (existUser1) {
                throw new UserExistsException("User already exists");
            }
            var user1 = new UserDTO();
            user1.setUsername(userName + "01");
            user1.setPassword(bCryptPasswordEncoder.encode("JWTPASSWORD"));
            defRole.addUser(user1);
            user1.getRoles().add(defRole);
            user1.setEmail(userName + "01" + email);
            user1.genHash();

            try {
                UserDTO newUser1 = usersRepository.save(user1);
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
            var user2 = new UserDTO();
            user2.setUsername(userName + "02");
            user2.setPassword(bCryptPasswordEncoder.encode("JWTPASSWORD"));
            defRole.addUser(user2);
            user2.getRoles().add(defRole);
            user2.setEmail(userName + "02" + email);
            user2.genHash();
            try {
                UserDTO newUser2 = usersRepository.save(user2);
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
        PrivilegeDTO privilegeDTO = new PrivilegeDTO();
        ArrayList<String> privileges = new ArrayList<>();
        privileges.add("ALL");
        privileges.add("APP_FULL_ACCESS");
        privileges.add("APP_SECURITY_ACCESS");
        privileges.add("APP_READ_ACCESS_BASIC");
        privileges.add("APP_READ_ACCESS_JWT");

        privileges.forEach(privilege -> {
            privilegeDTO.setName(privilege);
            privilegeDTO.genHash();
            log.info("generatePrivileges {}: {}", privilege, privilegeDTO);
        });
    }


    @DisplayName("Generate roles")
    @Test
    public void generateRoles() {
        RolesDTO rolesDTO = new RolesDTO();

        ArrayList<Pair> roles = new ArrayList<>();
        roles.add(new Pair("ADMIN", "Administrator"));
        roles.add(new Pair("OPERATOR", "Operator"));
        roles.add(new Pair("USER", "Application user with basic authentication"));
        roles.add(new Pair("JWT-TOKEN", "Application user with JWT token"));


        roles.forEach(pair -> {
            rolesDTO.setRolename(pair.getName());
            rolesDTO.setDescription(pair.getDescription());
            rolesDTO.genHash();
            log.info("generateRoles {}: {}", rolesDTO.getRolename(), rolesDTO);
        });

    }

    @DisplayName("Generate users")
    @Test
    public void generateUsers() {
        UserDTO userDTO = new UserDTO();

        ArrayList<Quart> users = new ArrayList<>();
        users.add(new Quart("admin", "12345", "admin@gmail.com", "0612345678"));
        users.add(new Quart("bvpelt", "12345", "bvpelt@gmail.com", "0656789012"));
        users.add(new Quart("user", "12345", "user@gmail.com", "0678901234"));

        users.forEach(quart -> {
            userDTO.setUsername(quart.getUsername());
            userDTO.setPassword(quart.getPassword());
            userDTO.setEmail(quart.getEmail());
            userDTO.setPhone(quart.getPhone());
            userDTO.genHash();
            log.info("generateUsers {}: {}", userDTO.getUsername(), userDTO);
        });
    }
}
