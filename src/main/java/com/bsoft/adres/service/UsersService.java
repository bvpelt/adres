package com.bsoft.adres.service;

import com.bsoft.adres.database.UserDTO;
import com.bsoft.adres.exceptions.UserExistsException;
import com.bsoft.adres.exceptions.UserNotExistsException;
import com.bsoft.adres.generated.model.User;
import com.bsoft.adres.generated.model.UserBody;
import com.bsoft.adres.mappers.UserMapper;
import com.bsoft.adres.repositories.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder; // = new BCryptPasswordEncoder();

    public void deleteAll() {
        try {
            usersRepository.deleteAll();
        } catch (Exception e) {
            log.error("Deleting all users failed: {}", e.toString());
        }
    }

    public boolean deleteUser(Long userId) {
        boolean deleted = false;
        try {
            Optional<UserDTO> optionalUserDAO = usersRepository.findByUserId(userId);
            if (optionalUserDAO.isEmpty()) {
                throw new UserNotExistsException("User with id " + userId + " not found and not deleted");
            }
            usersRepository.deleteById(userId);
            deleted = true;
        } catch (Exception e) {
            log.error("Delete user failed: {}", e.toString());
            throw e;
        }
        return deleted;
    }

    public User getUser(Long userId) {
        Optional<UserDTO> optionalUserDAO = usersRepository.findByUserId(userId);
        if (optionalUserDAO.isEmpty()) {
            throw new UserNotExistsException("User with id " + userId + " not found");
        }

        UserDTO userDTO = optionalUserDAO.get();

        return userMapper.map(userDTO);
    }

    public User getUserByName(String username) {
        Optional<UserDTO> optionalUserDAO = usersRepository.findByUserName(username);
        if (optionalUserDAO.isEmpty()) {
            throw new UserNotExistsException("User with name " + username + " not found");
        }

        UserDTO userDTO = optionalUserDAO.get();

        return userMapper.map(userDTO);
    }

    public List<User> getUsers() {
        List<User> userList = new ArrayList<User>();
        Iterable<UserDTO> userDAOIterable = usersRepository.findAll();

        userDAOIterable.forEach(userDAO -> {
            User user = userMapper.map(userDAO);
            userList.add(user);
        });

        return userList;
    }

    public Page<User> getUsersPage(PageRequest pageRequest) {
        Page<UserDTO> foundUsersPage = usersRepository.findAllByPage(pageRequest);

        List<User> usersList = new ArrayList<>();
        usersList = foundUsersPage.getContent().stream()
                .map(userMapper::map) // Apply mapper to each UserDAO
                .toList();

        return new PageImpl<>(usersList, foundUsersPage.getPageable(), foundUsersPage.getTotalElements());
    }

    public User postUser(Boolean override, final UserBody userBody) {
        UserDTO userDTO = new UserDTO(userBody);

        try {
            if (!override) {
                Optional<UserDTO> optionalUserDAO = usersRepository.findByHash(userDTO.getHash());
                if (optionalUserDAO.isPresent()) {
                    throw new UserExistsException("User " + userDTO + " already exists cannot insert again");
                }
            }

            String pwd = userBody.getPassword();
            String encodedPwd = passwordEncoder.encode(pwd);
            userDTO.setPassword(encodedPwd);
            userDTO.genHash();

            usersRepository.save(userDTO);

            return userMapper.map(userDTO); // Return 201 Created with the created entity
        } catch (Error e) {
            log.error("Error inserting adres: {}", e.toString());
            throw e;
        }
    }

    public User patch(Long userId, final UserBody userBody) {

        try {
            Optional<UserDTO> optionalUserDAO = usersRepository.findByUserId(userId);
            if (optionalUserDAO.isEmpty()) {
                throw new UserNotExistsException("User with id " + userId + " not found");
            }
            UserDTO foundUser = optionalUserDAO.get();
            if (userBody.getEmail() != null) {
                foundUser.setEmail(userBody.getEmail());
            }
            if (userBody.getUsername() != null) {
                foundUser.setUsername(userBody.getUsername());
            }
            if (userBody.getPassword() != null) {
                String encodedPwd = passwordEncoder.encode(userBody.getPassword());
                foundUser.setPassword(encodedPwd);
            }
            if (userBody.getPhone() != null) {
                foundUser.setPhone(userBody.getPhone());
            }
            if (userBody.getAccountNonExpired() != null) {
                foundUser.setAccount_non_expired(userBody.getAccountNonExpired());
            }
            if (userBody.getAccountNonLocked() != null) {
                foundUser.setAccount_non_locked(userBody.getAccountNonLocked());
            }
            if (userBody.getCredentialsNonExpired() != null) {
                foundUser.setCredentials_non_expired(userBody.getCredentialsNonExpired());
            }
            if (userBody.getEnabled() != null) {
                foundUser.setEnabled(userBody.getEnabled());
            }

            // set new hash -- assumption is at least one field is changed, no need to check!
            foundUser.setHash(foundUser.genHash());

            usersRepository.save(foundUser);

            return userMapper.map(foundUser);
        } catch (Error e) {
            log.error("Error patching adres: {}", e.toString());
            throw e;
        }

    }

}
