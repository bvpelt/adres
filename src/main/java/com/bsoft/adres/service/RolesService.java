package com.bsoft.adres.service;

import com.bsoft.adres.database.RolesDTO;
import com.bsoft.adres.exceptions.RoleExistsException;
import com.bsoft.adres.exceptions.RoleNotExistsException;
import com.bsoft.adres.generated.model.Role;
import com.bsoft.adres.generated.model.RoleBody;
import com.bsoft.adres.mappers.RoleMapper;
import com.bsoft.adres.repositories.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RolesService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;

    public void deleteAll() {
        try {
            roleRepository.deleteAll();
        } catch (Exception e) {
            log.error("Deleting all roles failed: {}", e.toString());
        }
    }

    public boolean deleteRole(Long roleId) {
        boolean deleted = false;
        try {
            Optional<RolesDTO> optionalRoleDAO = roleRepository.findByRoleId(roleId);
            if (optionalRoleDAO.isEmpty()) {
                throw new RoleNotExistsException("Role with id " + roleId + " not found and not deleted");
            }
            roleRepository.deleteById(roleId);
            deleted = true;
        } catch (Exception e) {
            log.error("Delete role failed: {}", e.toString());
            throw e;
        }
        return deleted;
    }

    public Role getRole(Long roleId) {
        Optional<RolesDTO> optionalRoleDAO = roleRepository.findByRoleId(roleId);
        if (optionalRoleDAO.isEmpty()) {
            throw new RoleNotExistsException("Role with id " + roleId + " not found");
        }

        RolesDTO RolesDTO = optionalRoleDAO.get();

        return roleMapper.map(RolesDTO);
    }

    public List<Role> getRoles() {
        List<Role> roleList = new ArrayList<Role>();
        Iterable<RolesDTO> RoleDAOIterable = roleRepository.findAll();

        RoleDAOIterable.forEach(RoleDAO -> {
            Role Role = roleMapper.map(RoleDAO);
            roleList.add(Role);
        });

        return roleList;
    }

    public List<Role> getRoles(final PageRequest pageRequest) {
        List<Role> roleList = new ArrayList<Role>();
        Iterable<RolesDTO> RoleDAOIterable = roleRepository.findAllByPaged(pageRequest);

        RoleDAOIterable.forEach(RoleDAO -> {
            Role Role = roleMapper.map(RoleDAO);
            roleList.add(Role);
        });

        return roleList;
    }

    public Role postRole(Boolean override, final RoleBody roleBody) {
        RolesDTO RolesDTO = new RolesDTO(roleBody);

        try {
            if (!override) {
                Optional<RolesDTO> optionalRoleDAO = roleRepository.findByHash(RolesDTO.getHash());
                if (optionalRoleDAO.isPresent()) {
                    throw new RoleExistsException("Role " + RolesDTO + " already exists cannot insert again");
                }
            }

            roleRepository.save(RolesDTO);

            return roleMapper.map(RolesDTO); // Return 201 Created with the created entity
        } catch (Error e) {
            log.error("Error inserting adres: {}", e.toString());
            throw e;
        }
    }

    public Role patch(Long roleId, final RoleBody roleBody) {
        Role role = new Role();

        Optional<RolesDTO> optionalRoleDAO = roleRepository.findByRoleId(roleId);
        if (optionalRoleDAO.isEmpty()) {
            throw new RoleNotExistsException("Role with id " + roleId + " not found");
        }
        RolesDTO foundRole = optionalRoleDAO.get();
        if (roleBody.getRolename() != null) {
            foundRole.setRolename(roleBody.getRolename());
        }
        if (roleBody.getDescription() != null) {
            foundRole.setDescription(roleBody.getDescription());
        }
        foundRole.setHash(foundRole.genHash());

        roleRepository.save(foundRole);

        return roleMapper.map(foundRole);

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
        Page<RolesDTO> foundRolesPage = roleRepository.findAllByPage(pageRequest);

        List<Role> rolesList = new ArrayList<>();
        rolesList = foundRolesPage.getContent().stream()
                .map(roleMapper::map) // Apply mapper to each AdresDAO
                .toList();

        return new PageImpl<>(rolesList, foundRolesPage.getPageable(), foundRolesPage.getTotalElements());
    }
}
