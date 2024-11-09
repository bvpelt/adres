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

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class PersonController implements PersonsApi {

    private final PersonService personService;

    @Value("${info.project.version}")
    private String version;

    @Override
    public ResponseEntity<Void> _deleteAllPersons() {
        personService.deleteAll();
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> _deletePerson(Long personId) {

        boolean deleted = personService.deletePerson(personId);
        if (!deleted) {
            throw new PersonNotDeletedException("Person not deleted");
        }

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<PersonAdres> _getPeronsAdresses(Long personId) {
        PersonAdres personAdres = personService.getPersonAdres(personId);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Version", version)
                .body(personAdres);
    }

    @Override
    public ResponseEntity<Person> _getPerson(Long personId) {
        Person person = personService.getPerson(personId);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Version", version)
                .body(person); // Return 201 Created with the created entity
    }

    @Override
    public ResponseEntity<List<Person>> _getPersons(Integer pageNumber, Integer pageSize, String sortBy) {
        List<Sort.Order> sortParameter;
        PageRequest pageRequest;
        log.info("Get persons for pagenumber: {} pagesize: {}, sort: {}", pageNumber, pageSize, sortBy);
        // Validate input parameters
        if (pageNumber == null) {
            pageNumber = 1;
        }
        if (pageNumber < 1) {
            throw new InvalidParameterException("Page number must be greater than 0");
        }
        if (pageSize == null) { // set to default
            pageSize = 25;
        }
        if (pageSize < 1) {
            throw new InvalidParameterException("Page size must be greater than 0");
        }
        //
        if (sortBy != null && !sortBy.isEmpty()) {
            //sortParameter = ControllerSortUtil.getSortOrder(sortBy);
            //pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(sortParameter));
            pageRequest = PageRequest.of(pageNumber - 1, pageSize, Sort.by(sortBy));
        } else {
            pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .header("Version", version)
                .body(personService.getPersons(pageRequest));
    }

    @Override
    public ResponseEntity<Person> _patchPerson(Long adresId, PersonBody adresBody) {
        Person person = personService.patch(adresId, adresBody);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Version", version)
                .body(person); // Return 201 Created with the created entity

    }

    @Override
    public ResponseEntity<Person> _postPerson(Boolean override, PersonBody adresBody) {
        if (override == null) {
            override = false;
        }
        Person person = personService.postPerson(override, adresBody); // Call the service method
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Version", version)
                .body(person); // Return 201 Created with the created entity
    }
}
