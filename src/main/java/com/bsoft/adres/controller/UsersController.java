package com.bsoft.adres.controller;

import com.bsoft.adres.exceptions.InvalidParameterException;
import com.bsoft.adres.exceptions.UserNotDeletedException;
import com.bsoft.adres.generated.api.UsersApi;
import com.bsoft.adres.generated.model.User;
import com.bsoft.adres.generated.model.UserBody;
import com.bsoft.adres.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/adres/api/v1")
@Controller
public class UsersController implements UsersApi {

    private final UsersService usersService;

    @Value("${info.project.version}")
    private String version;

    @Override
    public ResponseEntity<Void> _deleteUser(Long userId, String xApiKey, String authorization) {
        log.debug("_deleteUser apikey: {} authorization: {}", xApiKey, authorization);
        boolean deleted = usersService.deleteUser(userId);
        if (!deleted) {
            throw new UserNotDeletedException("User not deleted");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> _deleteAllUsers(String xApiKey, String authorization) {
        log.debug("_deleteAllUsers apikey: {} authorization: {}", xApiKey, authorization);
        usersService.deleteAll();
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<User> _getUser(Long userId, String xApiKey) {
        log.debug("_getUser apikey: {}", xApiKey);
        User user = usersService.getUser(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Version", version)
                .body(user); // Return 201 Created with the created entity
    }

    @Override
    public ResponseEntity<List<User>> _getUsers(Integer page, Integer size, String sort, String X_API_KEY) {

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
            //sortParameter = ControllerSortUtil.getSortOrder(sortBy);
            //pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(sortParameter));
            pageRequest = PageRequest.of(page - 1, size, Sort.by(sort));
        } else {
            pageRequest = PageRequest.of(page - 1, size);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .header("Version", version)
                .body(usersService.getUsers(pageRequest));
    }

    @Override
    public ResponseEntity<User> _patchUser(Long userId, String xApiKey, String authorization, UserBody userBody) {
        log.debug("_patchUser apikey: {} authorization: {}", xApiKey, authorization);
        User user = usersService.patch(userId, userBody);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Version", version)
                .body(user); // Return 201 Created with the created entity
    }


    @Override
    public ResponseEntity<User> _postUser(String xApiKey, String authorization, UserBody userBody) {
        log.debug("_postUser apikey: {} authorization: {}", xApiKey, authorization);

        User user = usersService.postUser(false, userBody); // Call the service method
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Version", version)
                .body(user); // Return 201 Created with the created entity
    }

}
