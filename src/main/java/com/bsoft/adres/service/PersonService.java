package com.bsoft.adres.service;

import com.bsoft.adres.database.AdresDTO;
import com.bsoft.adres.database.PersonDTO;
import com.bsoft.adres.exceptions.PersonExistsException;
import com.bsoft.adres.exceptions.PersonNotExistsException;
import com.bsoft.adres.generated.model.Person;
import com.bsoft.adres.generated.model.PersonAdres;
import com.bsoft.adres.generated.model.PersonBody;
import com.bsoft.adres.mappers.PersonMapper;
import com.bsoft.adres.repositories.AdresRepository;
import com.bsoft.adres.repositories.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AdresRepository adresRepository;

    @Autowired
    private PersonMapper personMapper;

    public void deleteAll() {
        try {
            personRepository.deleteAll();
        } catch (Exception e) {
            log.error("Deleting all persons failed: {}", e.toString());
            throw e;
        }
    }

    public boolean deletePerson(Long personId) {
        boolean deleted = false;
        try {
            Optional<PersonDTO> optionalPersonDAO = personRepository.findByPersonId(personId);
            if (!optionalPersonDAO.isPresent()) {
                throw new PersonNotExistsException("Person with id " + personId + " not found and not deleted");
            }
            personRepository.deleteById(personId);
            deleted = true;
        } catch (Exception e) {
            log.error("Delete person failed: {}", e.toString());
            throw e;
        }
        return deleted;
    }

    public Person getPerson(Long personId) {
        Optional<PersonDTO> optionalPersonDAO = personRepository.findByPersonId(personId);
        if (optionalPersonDAO.isEmpty()) {
            throw new PersonNotExistsException("Person with id " + personId + " not found");
        }
        return personMapper.map(optionalPersonDAO.get());
    }


    public PersonAdres getPersonAdres(Long personId) {
        Optional<PersonDTO> optionalPersonDAO = personRepository.findByPersonId(personId);
        if (optionalPersonDAO.isEmpty()) {
            throw new PersonNotExistsException("Person with id " + personId + " not found");
        }
        List<Long> adresIds = new ArrayList<Long>();
        List<AdresDTO> adresList = adresRepository.findAdresByPersonId(personId);

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
        Iterable<PersonDTO> iPerson = personRepository.findAll();

        iPerson.forEach(PersonDAO -> {
            Person newPerson = personMapper.map(PersonDAO);
            result.add(newPerson);
        });

        return result;
    }

    public List<Person> getPersons(final PageRequest pageRequest) {
        List<Person> PersonList = new ArrayList<Person>();
        Iterable<PersonDTO> PersonDAOIterable = personRepository.findAllByPaged(pageRequest);

        PersonDAOIterable.forEach(PersonDAO -> {
            PersonList.add(personMapper.map(PersonDAO));
        });

        return PersonList;
    }

    public Person postPerson(Boolean override, final PersonBody personBody) {
        PersonDTO personDTO = new PersonDTO(personBody);

        try {
            if (!override) {
                Optional<PersonDTO> optionalPersonDAO = personRepository.findByHash(personDTO.getHash());
                if (optionalPersonDAO.isPresent()) {
                    throw new PersonExistsException("Person " + personDTO + " already exists cannot insert again");
                }
            }

            personDTO.genHash();
            personRepository.save(personDTO);

            return personMapper.map(personDTO); // Return 201 Created with the created entity
        } catch (Error e) {
            log.error("Error inserting Person: {}", e.toString());
            throw e;
        }
    }

    public Person patch(Long PersonId, final PersonBody PersonBody) {

        Optional<PersonDTO> optionalPersonDAO = personRepository.findByPersonId(PersonId);
        if (!optionalPersonDAO.isPresent()) {
            throw new PersonNotExistsException("Person with id " + PersonId + " not found");
        }
        PersonDTO foundPerson = optionalPersonDAO.get();
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

    }

    public Page<Person> getPersonsPage(PageRequest pageRequest) {
        Page<PersonDTO> foundPersonPage = personRepository.findAllByPage(pageRequest);

        List<Person> personList = new ArrayList<>();
        personList = foundPersonPage.getContent().stream()
                .map(personMapper::map) // Apply mapper to each PersonDAO
                .toList();

        return new PageImpl<>(personList, foundPersonPage.getPageable(), foundPersonPage.getTotalElements());
    }
}
