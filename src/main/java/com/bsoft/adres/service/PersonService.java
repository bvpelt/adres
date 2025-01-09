package com.bsoft.adres.service;

import com.bsoft.adres.database.AdresDAO;
import com.bsoft.adres.database.PersonDAO;
import com.bsoft.adres.exceptions.PersonExistsException;
import com.bsoft.adres.exceptions.PersonNotExistsException;
import com.bsoft.adres.generated.model.Person;
import com.bsoft.adres.generated.model.PersonAdres;
import com.bsoft.adres.generated.model.PersonBody;
import com.bsoft.adres.mappers.PersonMapper;
import com.bsoft.adres.repositories.AdresRepository;
import com.bsoft.adres.repositories.PersonRepository;
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
public class PersonService {

    private final PersonRepository personRepository;
    private final AdresRepository adresRepository;
    private final PersonMapper personMapper;

    /*
    @Autowired
    public PersonService(final PersonRepository personRepository, final AdresRepository adresRepository) {
        this.personRepository = personRepository;
        this.adresRepository = adresRepository;
    }
     */

    public void deleteAll() {
        try {
            personRepository.deleteAll();
        } catch (Exception e) {
            log.error("Deleting all persons failed: {}", e);
            throw e;
        }
    }

    public boolean deletePerson(Long personId) {
        boolean deleted = false;
        try {
            Optional<PersonDAO> optionalPersonDAO = personRepository.findByPersonId(personId);
            if (!optionalPersonDAO.isPresent()) {
                throw new PersonNotExistsException("Person with id " + personId + " not found and not deleted");
            }
            personRepository.deleteById(personId);
            deleted = true;
        } catch (Exception e) {
            log.error("Delete person failed: {}", e);
            throw e;
        }
        return deleted;
    }

    public Person getPerson(Long personId) {
        Optional<PersonDAO> optionalPersonDAO = personRepository.findByPersonId(personId);
        if (!optionalPersonDAO.isPresent()) {
            throw new PersonNotExistsException("Person with id " + personId + " not found");
        }
        return personMapper.map(optionalPersonDAO.get());
    }


    public PersonAdres getPersonAdres(Long personId) {
        Optional<PersonDAO> optionalPersonDAO = personRepository.findByPersonId(personId);
        if (!optionalPersonDAO.isPresent()) {
            throw new PersonNotExistsException("Person with id " + personId + " not found");
        }
        List<Long> adresIds = new ArrayList<Long>();
        List<AdresDAO> adresList = adresRepository.findAdresByPersonId(personId);

        adresList.forEach(adres -> {
            adresIds.add(adres.getId());
        });

        PersonAdres personAdres = new PersonAdres();
        personAdres.setId(optionalPersonDAO.get().getId());
        personAdres.setFirstName(optionalPersonDAO.get().getFirstname());
        personAdres.setInfix(optionalPersonDAO.get().getInfix());
        personAdres.setLastName(optionalPersonDAO.get().getLastname());
        personAdres.setDateOfBirth(optionalPersonDAO.get().getDateofbirth());
        personAdres.setAdresses(adresIds);

        return personAdres;
    }

    public List<Person> getPersons() {
        List<Person> result = new ArrayList<Person>();
        Iterable<PersonDAO> iPerson = personRepository.findAll();

        iPerson.forEach(PersonDAO -> {
            Person newPerson = personMapper.map(PersonDAO);
            result.add(newPerson);
        });

        return result;
    }

    public List<Person> getPersons(final PageRequest pageRequest) {
        List<Person> PersonList = new ArrayList<Person>();
        Iterable<PersonDAO> PersonDAOIterable = personRepository.findAllByPaged(pageRequest);

        PersonDAOIterable.forEach(PersonDAO -> {
            PersonList.add(personMapper.map(PersonDAO));
        });

        return PersonList;
    }

    public Person postPerson(Boolean override, final PersonBody PersonBody) {
        PersonDAO PersonDAO = new PersonDAO(PersonBody);

        try {
            if (!override) {
                Optional<PersonDAO> optionalPersonDAO = personRepository.findByHash(PersonDAO.getHash());
                if (optionalPersonDAO.isPresent()) {
                    throw new PersonExistsException("Person " + PersonDAO + " already exists cannot insert again");
                }
            }

            personRepository.save(PersonDAO);

            return personMapper.map(PersonDAO); // Return 201 Created with the created entity
        } catch (Error e) {
            log.error("Error inserting Person: {}", e);
            throw e;
        }
    }

    public Person patch(Long PersonId, final PersonBody PersonBody) {

        try {
            Optional<PersonDAO> optionalPersonDAO = personRepository.findByPersonId(PersonId);
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
            foundPerson.setHash(foundPerson.genHash());

            personRepository.save(foundPerson);

            return personMapper.map(foundPerson);
        } catch (Error e) {
            throw e;
        }

    }

    /*
    private Person PersonDAO2Person(final PersonDAO personDAO) {
        Person person = new Person();
        person.setFirstName(personDAO.getFirstname());
        person.setId(personDAO.getId());
        person.setInfix(personDAO.getInfix());
        person.setLastName(personDAO.getLastname());
        person.setDateOfBirth(personDAO.getDateofbirth());
        return person;
    }

     */

    public Page<Person> getPersonsPage(PageRequest pageRequest) {
        Page<PersonDAO> foundPersonPage = personRepository.findAllByPage(pageRequest);

        List<Person> personList = new ArrayList<>();
        personList = foundPersonPage.getContent().stream()
                .map(personMapper::map) // Apply mapper to each PersonDAO
                .toList();

        return new PageImpl<>(personList, foundPersonPage.getPageable(), foundPersonPage.getTotalElements());
    }
}
