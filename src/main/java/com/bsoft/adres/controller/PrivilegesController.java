package com.bsoft.adres.controller;

import com.bsoft.adres.exceptions.InvalidParameterException;
import com.bsoft.adres.exceptions.PrivilegeNotDeletedException;
import com.bsoft.adres.generated.api.PrivilegesApi;
import com.bsoft.adres.generated.model.PagedPrivileges;
import com.bsoft.adres.generated.model.Privilege;
import com.bsoft.adres.generated.model.PrivilegeBody;
import com.bsoft.adres.service.PrivilegesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("${application.basePath}")
@Controller
public class PrivilegesController implements PrivilegesApi {

    private final PrivilegesService privilegesService;

    @Value("${info.project.version}")
    private String version;

    @Override
    public ResponseEntity<Void> _deletePrivilege(Long id, String X_API_KEY) {
        log.debug("_deletePrivilege apikey: {}", X_API_KEY);
        boolean deleted = privilegesService.deletePrivilege(id);
        if (!deleted) {
            throw new PrivilegeNotDeletedException("Privilege not deleted");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Version", version);

        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> _deleteAllPrivileges(String X_API_KEY) {
        log.debug("_deleteAllPrivileges apikey: {}", X_API_KEY);
        privilegesService.deleteAll();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Version", version);

        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Privilege> _getPrivilege(Long PrivilegeId, String X_API_KEY) {
        log.debug("_getPrivilege apikey: {}", X_API_KEY);
        Privilege Privilege = privilegesService.getPrivilege(PrivilegeId);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Version", version)
                .body(Privilege); // Return 201 Created with the created entity
    }

    @Override
    public ResponseEntity<PagedPrivileges> _getPrivileges(Integer page, Integer size, List<String> sort, String X_API_KEY) {

        log.debug("_getPrivileges apikey: {}", X_API_KEY);
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
        if (sort != null && sort.size() > 0) {
            List<Sort.Order> orders = ControllerSortUtil.getSortOrder(sort);
            pageRequest = PageRequest.of(page - 1, size, Sort.by(orders));
        } else {
            pageRequest = PageRequest.of(page - 1, size);
        }

        PagedPrivileges pageResponse = new PagedPrivileges();
        Page<Privilege> PrivilegePage = privilegesService.getPrivilegesPage(pageRequest);
        pageResponse.setContent(PrivilegePage.getContent());
        pageResponse.setPageNumber(PrivilegePage.getNumber() + 1);
        pageResponse.setPageSize(PrivilegePage.getSize());
        pageResponse.setTotalElements(BigDecimal.valueOf(PrivilegePage.getTotalElements()));
        pageResponse.setTotalPages(PrivilegePage.getTotalPages());

        return ResponseEntity.status(HttpStatus.OK)
                .header("Version", version)
                .body(pageResponse);
    }

    @Override
    public ResponseEntity<Privilege> _patchPrivilege(Long id, String X_API_KEY, PrivilegeBody PrivilegeBody) {
        log.debug("_patchPrivilege apikey: {}", X_API_KEY);
        Privilege Privilege = privilegesService.patch(id, PrivilegeBody);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Version", version)
                .body(Privilege); // Return 201 Created with the created entity
    }

    @Override
    public ResponseEntity<Privilege> _postPrivilege(String X_API_KEY, PrivilegeBody PrivilegeBody) {
        log.debug("_postPrivilege apikey: {}", X_API_KEY);

        Privilege Privilege = privilegesService.postPrivilege(false, PrivilegeBody); // Call the service method

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Version", version)
                .body(Privilege); // Return 201 Created with the created entity
    }

}
