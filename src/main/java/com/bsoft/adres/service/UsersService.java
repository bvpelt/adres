package com.bsoft.adres.service;

import com.bsoft.adres.database.RoleDAO;
import com.bsoft.adres.database.UserDAO;
import com.bsoft.adres.exceptions.UserExistsException;
import com.bsoft.adres.exceptions.UserNotExistsException;
import com.bsoft.adres.generated.model.Role;
import com.bsoft.adres.generated.model.User;
import com.bsoft.adres.generated.model.UserBody;
import com.bsoft.adres.mappers.UserMapper;
import com.bsoft.adres.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final UserMapper userMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder; // = new BCryptPasswordEncoder();

    public void deleteAll() {
        try {
            usersRepository.deleteAll();
        } catch (Exception e) {
            log.error("Deleting all users failed: {}", e);
        }
    }

    public boolean deleteUser(Long userId) {
        boolean deleted = false;
        try {
            Optional<UserDAO> optionalUserDAO = usersRepository.findByUserId(userId);
            if (optionalUserDAO.isEmpty()) {
                throw new UserNotExistsException("User with id " + userId + " not found and not deleted");
            }
            usersRepository.deleteById(userId);
            deleted = true;
        } catch (Exception e) {
            log.error("Delete user failed: {}", e);
            throw e;
        }
        return deleted;
    }

    public User getUser(Long userId) {
        Optional<UserDAO> optionalUserDAO = usersRepository.findByUserId(userId);
        if (optionalUserDAO.isEmpty()) {
            throw new UserNotExistsException("User with id " + userId + " not found");
        }

        UserDAO userDAO = optionalUserDAO.get();
        User user = UserDAO2User(userDAO);

        return user;
    }

    public User getUserByName(String username) {
        Optional<UserDAO> optionalUserDAO = usersRepository.findByUserName(username);
        if (optionalUserDAO.isEmpty()) {
            throw new UserNotExistsException("User with name " + username + " not found");
        }

        UserDAO userDAO = optionalUserDAO.get();
        User user = UserDAO2User(userDAO);

        return user;
    }

    public List<User> getUsers() {
        List<User> userList = new ArrayList<User>();
        Iterable<UserDAO> userDAOIterable = usersRepository.findAll();

        userDAOIterable.forEach(userDAO -> {
            User user = UserDAO2User(userDAO);
            userList.add(user);
        });

        return userList;
    }

    public List<User> getUsers(final PageRequest pageRequest) {
        List<User> userList = new ArrayList<User>();
        Iterable<UserDAO> userDAOIterable = usersRepository.findAllByPaged(pageRequest);

        userDAOIterable.forEach(userDAO -> {
            User user = UserDAO2User(userDAO);
            userList.add(user);
        });

        return userList;
    }

    public User postUser(Boolean override, final UserBody userBody) {
        UserDAO userDAO = new UserDAO(userBody);

        try {
            if (!override) {
                Optional<UserDAO> optionalUserDAO = usersRepository.findByHash(userDAO.getHash());
                if (optionalUserDAO.isPresent()) {
                    throw new UserExistsException("User " + userDAO + " already exists cannot insert again");
                }
            }

            String pwd = userBody.getPassword();
            String encodedPwd = bCryptPasswordEncoder.encode(pwd);
            userDAO.setPassword(encodedPwd);
            userDAO.genHash();

            usersRepository.save(userDAO);

            return UserDAO2User(userDAO); // Return 201 Created with the created entity
        } catch (Error e) {
            log.error("Error inserting adres: {}", e);
            throw e;
        }
    }

    public User patch(Long userId, final UserBody userBody) {
        User user = new User();

        try {
            Optional<UserDAO> optionalUserDAO = usersRepository.findByUserId(userId);
            if (optionalUserDAO.isEmpty()) {
                throw new UserNotExistsException("User with id " + userId + " not found");
            }
            UserDAO foundUser = optionalUserDAO.get();
            if (userBody.getEmail() != null) {
                foundUser.setEmail(userBody.getEmail());
            }
            if (userBody.getUsername() != null) {
                foundUser.setUsername(userBody.getUsername());
            }
            if (userBody.getPassword() != null) {
                foundUser.setPassword(userBody.getPassword());
            }
            if (userBody.getPhone() != null) {
                foundUser.setPhone(userBody.getPhone());
            }
            foundUser.setHash(foundUser.getHash());

            usersRepository.save(foundUser);

            return UserDAO2User(foundUser);
        } catch (Error e) {
            log.error("Error patching adres: {}", e);
            throw e;
        }

    }

    private User UserDAO2User(final UserDAO userDAO) {
        User user = new User();
        user.setId(userDAO.getId());
        user.setUsername(userDAO.getUsername());
        user.setPassword(userDAO.getPassword());
        user.setEmail(userDAO.getEmail());
        user.setPhone(userDAO.getPhone());
        user.setAccountNonExpired(userDAO.getAccount_non_expired());
        user.setAccountNonLocked(userDAO.getAccount_non_locked());
        user.setCredentialsNonExpired(userDAO.getCredentials_non_expired());
        user.setEnabled(userDAO.getEnabled());
        user.setRoles(new ArrayList<>());
        userDAO.getRoles().forEach(role -> {
            user.getRoles().add(RoleDAOtoRole(role));
        });
        return user;
    }

    public Role RoleDAOtoRole(RoleDAO roleDAO) {
        Role role = new Role();
        role.setId(roleDAO.getId());
        role.setRolename(roleDAO.getRolename());
        role.setDescription(roleDAO.getDescription());
        return role;
    }

    public Page<User> getUsersPage(PageRequest pageRequest) {
        Page<UserDAO> foundUsersPage = usersRepository.findAllByPage(pageRequest);

        List<User> usersList = new ArrayList<>();
        usersList = foundUsersPage.getContent().stream()
                .map(userMapper::map) // Apply mapper to each UserDAO
                .toList();

        return new PageImpl<>(usersList, foundUsersPage.getPageable(), foundUsersPage.getTotalElements());
    }
}
