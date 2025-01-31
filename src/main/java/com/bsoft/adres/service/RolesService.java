package com.bsoft.adres.service;

import com.bsoft.adres.database.RolesDAO;
import com.bsoft.adres.exceptions.RoleExistsException;
import com.bsoft.adres.exceptions.RoleNotExistsException;
import com.bsoft.adres.generated.model.Role;
import com.bsoft.adres.generated.model.RoleBody;
import com.bsoft.adres.mappers.RoleMapper;
import com.bsoft.adres.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class RolesService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public void deleteAll() {
        try {
            roleRepository.deleteAll();
        } catch (Exception e) {
            log.error("Deleting all roles failed: {}", e);
        }
    }

    public boolean deleteRole(Long roleId) {
        boolean deleted = false;
        try {
            Optional<RolesDAO> optionalRoleDAO = roleRepository.findByRoleId(roleId);
            if (optionalRoleDAO.isEmpty()) {
                throw new RoleNotExistsException("Role with id " + roleId + " not found and not deleted");
            }
            roleRepository.deleteById(roleId);
            deleted = true;
        } catch (Exception e) {
            log.error("Delete role failed: {}", e);
            throw e;
        }
        return deleted;
    }

    public Role getRole(Long roleId) {
        Optional<RolesDAO> optionalRoleDAO = roleRepository.findByRoleId(roleId);
        if (optionalRoleDAO.isEmpty()) {
            throw new RoleNotExistsException("Role with id " + roleId + " not found");
        }

        RolesDAO RolesDAO = optionalRoleDAO.get();

        return roleMapper.map(RolesDAO);
    }

    public List<Role> getRoles() {
        List<Role> roleList = new ArrayList<Role>();
        Iterable<RolesDAO> RoleDAOIterable = roleRepository.findAll();

        RoleDAOIterable.forEach(RoleDAO -> {
            Role Role = roleMapper.map(RoleDAO);
            roleList.add(Role);
        });

        return roleList;
    }

    public List<Role> getRoles(final PageRequest pageRequest) {
        List<Role> roleList = new ArrayList<Role>();
        Iterable<RolesDAO> RoleDAOIterable = roleRepository.findAllByPaged(pageRequest);

        RoleDAOIterable.forEach(RoleDAO -> {
            Role Role = roleMapper.map(RoleDAO);
            roleList.add(Role);
        });

        return roleList;
    }

    public Role postRole(Boolean override, final RoleBody roleBody) {
        RolesDAO RolesDAO = new RolesDAO(roleBody);

        try {
            if (!override) {
                Optional<RolesDAO> optionalRoleDAO = roleRepository.findByHash(RolesDAO.getHash());
                if (optionalRoleDAO.isPresent()) {
                    throw new RoleExistsException("Role " + RolesDAO + " already exists cannot insert again");
                }
            }

            roleRepository.save(RolesDAO);

            return roleMapper.map(RolesDAO); // Return 201 Created with the created entity
        } catch (Error e) {
            log.error("Error inserting adres: {}", e);
            throw e;
        }
    }

    public Role patch(Long roleId, final RoleBody roleBody) {
        Role role = new Role();

        try {
            Optional<RolesDAO> optionalRoleDAO = roleRepository.findByRoleId(roleId);
            if (optionalRoleDAO.isEmpty()) {
                throw new RoleNotExistsException("Role with id " + roleId + " not found");
            }
            RolesDAO foundRole = optionalRoleDAO.get();
            if (roleBody.getRolename() != null) {
                foundRole.setRolename(roleBody.getRolename());
            }
            if (roleBody.getDescription() != null) {
                foundRole.setDescription(roleBody.getDescription());
            }
            foundRole.setHash(foundRole.genHash());

            roleRepository.save(foundRole);

            return roleMapper.map(foundRole);
        } catch (Error e) {
            throw e;
        }

    }

    /*
    private Role RoleDAO2Role(final RoleDAO roleDAO) {
        Role role = new Role();
        role.setId(roleDAO.getId());
        role.setRolename(roleDAO.getRolename());
        role.setDescription(roleDAO.getDescription());

        return role;
    }

    public Role RoleDAOtoRole(RoleDAO roleDAO) {
        Role role = new Role();
        role.setId(roleDAO.getId());
        role.setRolename(roleDAO.getRolename());
        role.setDescription(roleDAO.getDescription());
        return role;
    }

     */

    public Page<Role> getRolesPage(PageRequest pageRequest) {
        Page<RolesDAO> foundRolesPage = roleRepository.findAllByPage(pageRequest);

        List<Role> rolesList = new ArrayList<>();
        rolesList = foundRolesPage.getContent().stream()
                .map(roleMapper::map) // Apply mapper to each AdresDAO
                .toList();

        return new PageImpl<>(rolesList, foundRolesPage.getPageable(), foundRolesPage.getTotalElements());
    }
}
