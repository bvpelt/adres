package com.bsoft.adres.controller;

import com.bsoft.adres.exceptions.InvalidParameterException;
import com.bsoft.adres.exceptions.RoleNotDeletedException;
import com.bsoft.adres.generated.api.RolesApi;
import com.bsoft.adres.generated.model.Role;
import com.bsoft.adres.generated.model.RoleBody;
import com.bsoft.adres.service.RolesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/adres/api/v1")
@Controller
public class RolesController implements RolesApi {

    private final RolesService rolesService;

    @Value("${info.project.version}")
    private String version;

    @Override
    public ResponseEntity<Void> _deleteRole(Long id, String X_API_KEY) {
        log.debug("_deleteRole apikey: {}", X_API_KEY);
        boolean deleted = rolesService.deleteRole(id);
        if (!deleted) {
            throw new RoleNotDeletedException("Role not deleted");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Version", version);

        ResponseEntity<Void> responseEntity = new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);

        return responseEntity;
    }

    @Override
    public ResponseEntity<Void> _deleteAllRoles(String X_API_KEY) {
        log.debug("_deleteAllRoles apikey: {}", X_API_KEY);
        rolesService.deleteAll();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Version", version);

        ResponseEntity<Void> responseEntity = new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);

        return responseEntity;
    }

    @Override
    public ResponseEntity<Role> _getRole(Long roleId, String X_API_KEY) {
        log.debug("_getRole apikey: {}", X_API_KEY);
        Role Role = rolesService.getRole(roleId);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Version", version)
                .body(Role); // Return 201 Created with the created entity
    }

    @Override
    public ResponseEntity<List<Role>> _getRoles(Integer page, Integer size, String sort, String X_API_KEY) {

        log.debug("_getRoles apikey: {}", X_API_KEY);
        List<Sort.Order> sortParameter;
        PageRequest pageRequest;
        log.trace("Get adresses for pagenumber: {} pagesize: {}, sort: {}", page, size, sort);
        // Validate input parameters
        if (page == null) {
            page = 1;
        }
        if (page < 1) {
            throw new InvalidParameterException("Page number must be greater than 0");
        }
        if (size == null) { // set to default
            size = 25;
        }
        if (size < 1) {
            throw new InvalidParameterException("Page size must be greater than 0");
        }
        //
        if (sort != null && !sort.isEmpty()) {
            //sortParameter = ControllerSortUtil.getSortOrder(sortBy);
            //pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(sortParameter));
            pageRequest = PageRequest.of(page - 1, size, Sort.by(sort));
        } else {
            pageRequest = PageRequest.of(page - 1, size);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .header("Version", version)
                .body(rolesService.getRoles(pageRequest));
    }

    @Override
    public ResponseEntity<Role> _patchRole(Long id, String X_API_KEY, RoleBody roleBody) {
        log.debug("_patchRole apikey: {}", X_API_KEY);
        Role Role = rolesService.patch(id, roleBody);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Version", version)
                .body(Role); // Return 201 Created with the created entity
    }

    @Override
    public ResponseEntity<Role> _postRole(String X_API_KEY, RoleBody roleBody) {
        log.debug("_postRole apikey: {}", X_API_KEY);

        Role Role = rolesService.postRole(false, roleBody); // Call the service method

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Version", version)
                .body(Role); // Return 201 Created with the created entity
    }

}
