package com.bsoft.adres.controller;

import com.bsoft.adres.exceptions.InvalidParameterException;
import com.bsoft.adres.exceptions.UserNotDeletedException;
import com.bsoft.adres.generated.api.UsersApi;
import com.bsoft.adres.generated.model.PagedUsers;
import com.bsoft.adres.generated.model.User;
import com.bsoft.adres.generated.model.UserBody;
import com.bsoft.adres.service.UsersService;
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
public class UsersController implements UsersApi {

    private final UsersService usersService;

    @Value("${info.project.version}")
    private String version;

    private HttpHeaders getVersionHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Version", version);
        return headers;
    }

    @Override
    public ResponseEntity<Void> _deleteUser(Long id, String X_API_KEY) {
        log.debug("_deleteUser apikey: {}", X_API_KEY);
        boolean deleted = usersService.deleteUser(id);
        if (!deleted) {
            throw new UserNotDeletedException("User not deleted");
        }

        HttpHeaders headers = getVersionHeaders();

        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> _deleteAllUsers(String X_API_KEY) {
        log.debug("_deleteAllUsers apikey: {}", X_API_KEY);
        usersService.deleteAll();

        HttpHeaders headers = getVersionHeaders();

        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<User> _getUser(Long userId, String X_API_KEY) {
        log.debug("_getUser apikey: {}", X_API_KEY);
        User user = usersService.getUser(userId);

        return ResponseEntity.status(HttpStatus.OK)
                .headers(getVersionHeaders())
                .body(user); // Return 201 Created with the created entity
    }

    @Override
    public ResponseEntity<PagedUsers> _getUsers(Integer page, Integer size, String X_API_KEY, List<String> sort) {

        log.debug("_getUsers apikey: {}", X_API_KEY);
        List<Sort.Order> sortParameter;
        PageRequest pageRequest;
        log.info("Get adresses for pagenumber: {} pagesize: {}, sort: {}", page, size, sort);
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
            List<Sort.Order> orders = ControllerSortUtil.getSortOrder(sort);
            pageRequest = PageRequest.of(page - 1, size, Sort.by(orders));
        } else {
            pageRequest = PageRequest.of(page - 1, size);
        }

        PagedUsers pageResponse = new PagedUsers();
        Page<User> usersPage = usersService.getUsersPage(pageRequest);
        pageResponse.setContent(usersPage.getContent());
        pageResponse.setPageNumber(usersPage.getNumber() + 1);
        pageResponse.setPageSize(usersPage.getSize());
        pageResponse.setTotalElements(BigDecimal.valueOf(usersPage.getTotalElements()).longValue());
        pageResponse.setTotalPages(usersPage.getTotalPages());

        return ResponseEntity.status(HttpStatus.OK)
                .headers(getVersionHeaders())
                .body(pageResponse);
    }

    @Override
    public ResponseEntity<User> _patchUser(Long id, String X_API_KEY, UserBody userBody) {
        log.debug("_patchUser apikey: {}", X_API_KEY);
        User user = usersService.patch(id, userBody);

        return ResponseEntity.status(HttpStatus.OK)
                .headers(getVersionHeaders())
                .body(user); // Return 201 Created with the created entity
    }

    @Override
    public ResponseEntity<User> _postUser(String X_API_KEY, UserBody userBody) {
        log.debug("_postUser apikey: {}", X_API_KEY);

        User user = usersService.postUser(false, userBody); // Call the service method

        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(getVersionHeaders())
                .body(user); // Return 201 Created with the created entity
    }

}
