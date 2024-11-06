package com.bsoft.adres.service;

import com.bsoft.adres.database.PersonDAO;
import com.bsoft.adres.exceptions.PersonExistsException;
import com.bsoft.adres.exceptions.PersonNotExistsException;
import com.bsoft.adres.generated.model.Person;
import com.bsoft.adres.generated.model.PersonBody;
import com.bsoft.adres.repositories.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PersonService {

    private final PersonRepository PersonRepository;

    @Autowired
    public PersonService(final PersonRepository PersonRepository) {
        this.PersonRepository = PersonRepository;
    }

    public boolean deletePerson(Long personId) {
        boolean deleted = false;
        try {
            Optional<PersonDAO> optionalPersonDAO = PersonRepository.findByPersonId(personId);
            if (!optionalPersonDAO.isPresent()) {
                throw new PersonNotExistsException("Person with id " + personId + " not found and not deleted");
            }
            PersonRepository.deleteById(personId);
            deleted = true;
        } catch (Exception e) {
            log.error("Delete person failed", e);
            throw e;
        }
        return deleted;
    }

    public Person getPerson(Long personId) {
        Optional<PersonDAO> optionalPersonDAO = PersonRepository.findByPersonId(personId);
        if (!optionalPersonDAO.isPresent()) {
            throw new PersonNotExistsException("Person with id " + personId + " not found");
        }
        Person person = PersonDAO2Person(optionalPersonDAO.get());
        return person;
    }

    public List<Person> getPersons() {
        List<Person> result = new ArrayList<Person>();
        Iterable<PersonDAO> iPerson = PersonRepository.findAll();

        iPerson.forEach(PersonDAO -> {
            Person newPerson = PersonDAO2Person(PersonDAO);
            result.add(newPerson);
        });

        return result;
    }

    public List<Person> getPersons(final PageRequest pageRequest) {
        List<Person> PersonList = new ArrayList<Person>();
        Iterable<PersonDAO> PersonDAOIterable = PersonRepository.findAllByPaged(pageRequest);

        PersonDAOIterable.forEach(PersonDAO -> {
            PersonList.add(PersonDAO2Person(PersonDAO));
        });

        return PersonList;
    }

    public Person postPerson(Boolean override, final PersonBody PersonBody) {
        PersonDAO PersonDAO = new PersonDAO(PersonBody);

        try {
            if (!override) {
                Optional<PersonDAO> optionalPersonDAO = PersonRepository.findByHash(PersonDAO.getHash());
                if (optionalPersonDAO.isPresent()) {
                    throw new PersonExistsException("Person " + PersonDAO + " already exists cannot insert again");
                }
            }

            PersonRepository.save(PersonDAO);
            Person Person = PersonDAO2Person(PersonDAO);

            return Person; // Return 201 Created with the created entity
        } catch (Error e) {
            log.error("Error inserting Person: {}", e);
            throw e;
        }
    }

    public Person patch(Long PersonId, final PersonBody PersonBody) {
        Person Person = new Person();

        try {
            Optional<PersonDAO> optionalPersonDAO = PersonRepository.findByPersonId(PersonId);
            if (!optionalPersonDAO.isPresent()) {
                throw new PersonNotExistsException("Person with id " + PersonId + " not found");
            }
            PersonDAO foundPerson = optionalPersonDAO.get();
            if (PersonBody.getFirstName() != null) {
                foundPerson.setFirstname(PersonBody.getFirstName());
            }
            if (PersonBody.getInfix() != null) {
                foundPerson.setInfix(PersonBody.getInfix());
            }
            if (PersonBody.getLastName() != null) {
                foundPerson.setLastname(PersonBody.getLastName());
            }
            if (PersonBody.getDateOfBirth() != null) {
                foundPerson.setDateofbirth(PersonBody.getDateOfBirth());
            }

            PersonRepository.save(foundPerson);

            Person = PersonDAO2Person(foundPerson);
            return Person;
        } catch (Error e) {
            throw e;
        }

    }

    private Person PersonDAO2Person(final PersonDAO personDAO) {
        Person person = new Person();
        person.setFirstName(personDAO.getFirstname());
        person.setPersonId(personDAO.getPersonid());
        person.setInfix(personDAO.getInfix());
        person.setLastName(personDAO.getLastname());
        person.setDateOfBirth(personDAO.getDateofbirth());
        return person;
    }
}