package com.bsoft.adres.controller;

import com.bsoft.adres.exceptions.InvalidParameterException;
import com.bsoft.adres.exceptions.PersonNotDeletedException;
import com.bsoft.adres.generated.api.PersonsApi;
import com.bsoft.adres.generated.model.Person;
import com.bsoft.adres.generated.model.PersonAdres;
import com.bsoft.adres.generated.model.PersonBody;
import com.bsoft.adres.service.PersonService;
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
public class PersonController implements PersonsApi {

    private final PersonService personService;

    @Value("${info.project.version}")
    private String version;

    @Override
    public ResponseEntity<Void> _deleteAllPersons(String X_API_KEY) {
        log.debug("_deleteAllPersons apikey: {}", X_API_KEY);
        personService.deleteAll();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Version", version);

        ResponseEntity<Void> responseEntity = new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);

        return responseEntity;
    }

    @Override
    public ResponseEntity<Void> _deletePerson(Long id, String X_API_KEY) {
        log.debug("_deletePerson apikey: {}", X_API_KEY);
        boolean deleted = personService.deletePerson(id);
        if (!deleted) {
            throw new PersonNotDeletedException("Person not deleted");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Version", version);

        ResponseEntity<Void> responseEntity = new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);

        return responseEntity;
    }

    @Override
    public ResponseEntity<PersonAdres> _getPeronsAdresses(Long personId, String X_API_KEY) {
        log.debug("_getPeronsAdresses apikey: {}", X_API_KEY);
        PersonAdres personAdres = personService.getPersonAdres(personId);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Version", version)
                .body(personAdres);
    }

    @Override
    public ResponseEntity<Person> _getPerson(Long personId, String X_API_KEY) {
        log.debug("_getPerson apikey: {}", X_API_KEY);
        Person person = personService.getPerson(personId);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Version", version)
                .body(person); // Return 201 Created with the created entity
    }

    @Override
    public ResponseEntity<List<Person>> _getPersons(Integer page, Integer size, String X_API_KEY, String sort) {
        log.debug("_getPersons apikey: {}", X_API_KEY);
        List<Sort.Order> sortParameter;
        PageRequest pageRequest;
        log.trace("Get persons for pagenumber: {} pagesize: {}, sort: {}", page, size, sort);
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
                .body(personService.getPersons(pageRequest));
    }

    @Override
    public ResponseEntity<Person> _patchPerson(Long id, String X_API_KEY, PersonBody personBody) {
        log.debug("_patchPerson apikey: {}", X_API_KEY);
        Person person = personService.patch(id, personBody);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Version", version)
                .body(person); // Return 201 Created with the created entity

    }

    @Override
    public ResponseEntity<Person> _postPerson(Boolean override, String X_API_KEY, PersonBody personBody) {
        log.debug("_postPerson apikey: {}", X_API_KEY);
        if (override == null) {
            override = false;
        }
        Person person = personService.postPerson(override, personBody); // Call the service method

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Version", version)
                .body(person); // Return 201 Created with the created entity
    }
}
