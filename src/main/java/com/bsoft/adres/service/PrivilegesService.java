package com.bsoft.adres.service;

import com.bsoft.adres.database.PrivilegeDAO;
import com.bsoft.adres.exceptions.PrivilegeExistsException;
import com.bsoft.adres.exceptions.PrivilegeNotExistsException;
import com.bsoft.adres.generated.model.Privilege;
import com.bsoft.adres.generated.model.PrivilegeBody;
import com.bsoft.adres.mappers.PrivilegeMapper;
import com.bsoft.adres.repositories.PrivilegeRepository;
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
public class PrivilegesService {

    private final PrivilegeRepository privilegeRepository;
    private final PrivilegeMapper privilegeMapper;

    public void deleteAll() {
        try {
            privilegeRepository.deleteAll();
        } catch (Exception e) {
            log.error("Deleting all Privileges failed: {}", e);
        }
    }

    public boolean deletePrivilege(Long privilegeId) {
        boolean deleted = false;
        try {
            Optional<PrivilegeDAO> optionalPrivilegeDAO = privilegeRepository.findByPrivilegeId(privilegeId);
            if (optionalPrivilegeDAO.isEmpty()) {
                throw new PrivilegeNotExistsException("Privilege with id " + privilegeId + " not found and not deleted");
            }
            privilegeRepository.deleteById(privilegeId);
            deleted = true;
        } catch (Exception e) {
            log.error("Delete Privilege failed: {}", e);
            throw e;
        }
        return deleted;
    }

    public Privilege getPrivilege(Long privilegeId) {
        Optional<PrivilegeDAO> optionalPrivilegeDAO = privilegeRepository.findByPrivilegeId(privilegeId);
        if (optionalPrivilegeDAO.isEmpty()) {
            throw new PrivilegeNotExistsException("Privilege with id " + privilegeId + " not found");
        }

        return privilegeMapper.map(optionalPrivilegeDAO.get());
    }

    public List<Privilege> getPrivileges() {
        List<Privilege> PrivilegeList = new ArrayList<Privilege>();
        Iterable<PrivilegeDAO> privilegeDAOIterable = privilegeRepository.findAll();

        privilegeDAOIterable.forEach(privilegeDAO -> {
            PrivilegeList.add(privilegeMapper.map(privilegeDAO));
        });

        return PrivilegeList;
    }

    public List<Privilege> getPrivileges(final PageRequest pageRequest) {
        List<Privilege> privilegeList = new ArrayList<Privilege>();
        Iterable<PrivilegeDAO> privilegeDAOIterable = privilegeRepository.findAllByPaged(pageRequest);

        privilegeDAOIterable.forEach(privilegeDAO -> {
            privilegeList.add(privilegeMapper.map(privilegeDAO));
        });

        return privilegeList;
    }

    public Privilege postPrivilege(Boolean override, final PrivilegeBody privilegeBody) {
        PrivilegeDAO privilegeDAO = new PrivilegeDAO(privilegeBody);

        try {
            if (!override) {
                Optional<PrivilegeDAO> optionalPrivilegeDAO = privilegeRepository.findByHash(privilegeDAO.getHash());
                if (optionalPrivilegeDAO.isPresent()) {
                    throw new PrivilegeExistsException("Privilege " + privilegeDAO + " already exists cannot insert again");
                }
            }

            privilegeRepository.save(privilegeDAO);

            return privilegeMapper.map(privilegeDAO); // Return 201 Created with the created entity
        } catch (Error e) {
            log.error("Error inserting adres: {}", e);
            throw e;
        }
    }

    public Privilege patch(Long privilegeId, final PrivilegeBody privilegeBody) {
        Privilege Privilege = new Privilege();

        try {
            Optional<PrivilegeDAO> optionalPrivilegeDAO = privilegeRepository.findByPrivilegeId(privilegeId);
            if (optionalPrivilegeDAO.isEmpty()) {
                throw new PrivilegeNotExistsException("Privilege with id " + privilegeId + " not found");
            }
            PrivilegeDAO foundPrivilege = optionalPrivilegeDAO.get();
            if (privilegeBody.getName() != null) {
                foundPrivilege.setName(privilegeBody.getName());
            }
            foundPrivilege.setHash(foundPrivilege.genHash());

            privilegeRepository.save(foundPrivilege);

            return privilegeMapper.map(foundPrivilege);
        } catch (Error e) {
            throw e;
        }

    }

    /*
        private Privilege PrivilegeDAO2Privilege(final PrivilegeDAO PrivilegeDAO) {
            Privilege Privilege = new Privilege();
            Privilege.setId(PrivilegeDAO.getId());
            Privilege.setPrivilegename(PrivilegeDAO.getPrivilegename());
            Privilege.setDescription(PrivilegeDAO.getDescription());

            return Privilege;
        }

        public Privilege PrivilegeDAOtoPrivilege(PrivilegeDAO PrivilegeDAO) {
            Privilege Privilege = new Privilege();
            Privilege.setId(PrivilegeDAO.getId());
            Privilege.setPrivilegename(PrivilegeDAO.getPrivilegename());
            Privilege.setDescription(PrivilegeDAO.getDescription());
            return Privilege;
        }
    */
    public Page<Privilege> getPrivilegesPage(PageRequest pageRequest) {
        Page<PrivilegeDAO> foundPrivilegesPage = privilegeRepository.findAllByPage(pageRequest);

        List<Privilege> privilegesList = new ArrayList<>();
        privilegesList = foundPrivilegesPage.getContent().stream()
                .map(privilegeMapper::map) // Apply mapper to each AdresDAO
                .toList();

        return new PageImpl<>(privilegesList, foundPrivilegesPage.getPageable(), foundPrivilegesPage.getTotalElements());
    }
}
