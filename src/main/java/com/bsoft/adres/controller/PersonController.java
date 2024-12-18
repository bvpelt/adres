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
//        return null;
//    }
//    @Override
//    public ResponseEntity<Void> _deleteAllPersons(String xApiKey, String authorization) {
        log.debug("_deleteAllPersons apikey: {}", X_API_KEY);
        personService.deleteAll();
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }


    @Override
    public ResponseEntity<Void> _deletePerson(Long id, String X_API_KEY) {
//        return null;
//    }
//    @Override
//    public ResponseEntity<Void> _deletePerson(Long personId, String xApiKey, String authorization) {
        log.debug("_deletePerson apikey: {}", X_API_KEY);
        boolean deleted = personService.deletePerson(id);
        if (!deleted) {
            throw new PersonNotDeletedException("Person not deleted");
        }

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<PersonAdres> _getPeronsAdresses(Long personId, String xApiKey) {
        log.debug("_getPeronsAdresses apikey: {}", xApiKey);
        PersonAdres personAdres = personService.getPersonAdres(personId);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Version", version)
                .body(personAdres);
    }

    @Override
    public ResponseEntity<Person> _getPerson(Long personId, String xApiKey) {
        log.debug("_getPerson apikey: {}", xApiKey);
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
        log.info("Get persons for pagenumber: {} pagesize: {}, sort: {}", page, size, sort);
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
//        return null;
//    }
//    @Override
//    public ResponseEntity<Person> _patchPerson(Long adresId, String xApiKey, String authorization, PersonBody adresBody) {
        log.debug("_patchPerson apikey: {}", X_API_KEY);
        Person person = personService.patch(id, personBody);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Version", version)
                .body(person); // Return 201 Created with the created entity

    }

    @Override
    public ResponseEntity<Person> _postPerson(Boolean override, String X_API_KEY, PersonBody personBody) {
//        return null;
//    }
//    @Override
//    public ResponseEntity<Person> _postPerson(Boolean override, String X_API_KEY, String authorization, PersonBody personBody) {
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
