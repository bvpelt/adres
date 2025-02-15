package com.bsoft.adres.service;


import com.bsoft.adres.database.AdresDTO;
import com.bsoft.adres.database.PersonDTO;
import com.bsoft.adres.exceptions.AdresExistsException;
import com.bsoft.adres.exceptions.AdresNotExistsException;
import com.bsoft.adres.generated.model.Adres;
import com.bsoft.adres.generated.model.AdresBody;
import com.bsoft.adres.generated.model.AdresPerson;
import com.bsoft.adres.generated.model.Person;
import com.bsoft.adres.mappers.AdresMapper;
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
public class AdresService {

    @Autowired
    private AdresRepository adresRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AdresMapper adresMapper;
    @Autowired
    private PersonMapper personMapper;

    public void deleteAll() {
        try {
            adresRepository.deleteAll();
        } catch (Exception e) {
            log.error("Deleting all adresses failed: {}", e.toString());
        }
    }

    public boolean deleteAdres(Long adresId) {
        boolean deleted = false;
        try {
            Optional<AdresDTO> optionalAdresDAO = adresRepository.findByAdresId(adresId);
            if (!optionalAdresDAO.isPresent()) {
                throw new AdresNotExistsException("Adres with id " + adresId + " not found and not deleted");
            }
            adresRepository.deleteById(adresId);
            deleted = true;
        } catch (Exception e) {
            log.error("Delete adres failed: {}", e.toString());
            throw e;
        }
        return deleted;
    }

    public Adres getAdres(Long adresId) {
        Optional<AdresDTO> optionalAdresDAO = adresRepository.findByAdresId(adresId);
        if (!optionalAdresDAO.isPresent()) {
            throw new AdresNotExistsException("Adres with id " + adresId + " not found");
        }

        return adresMapper.map(optionalAdresDAO.get());
    }

    public List<Adres> getAdresses() {
        List<Adres> result = new ArrayList<Adres>();
        Iterable<AdresDTO> iadres = adresRepository.findAll();

        iadres.forEach(adresDAO -> {
            Adres newAdres = adresMapper.map(adresDAO);
            result.add(newAdres);
        });

        return result;
    }

    public Page<Adres> getAdressesPage(final PageRequest pageRequest) {

        Page<AdresDTO> foundAdresPage = adresRepository.findAllByPage(pageRequest);

        List<Adres> adresList = new ArrayList<>();
        adresList = foundAdresPage.getContent().stream()
                .map(adresMapper::map) // Apply mapper to each AdresDAO
                .toList();

        return new PageImpl<>(adresList, foundAdresPage.getPageable(), foundAdresPage.getTotalElements());
    }

    public List<Adres> getAdresses(final PageRequest pageRequest) {
        List<Adres> adresList = new ArrayList<Adres>();
        Iterable<AdresDTO> adresDAOIterable = adresRepository.findAllByPaged(pageRequest);

        adresDAOIterable.forEach(adresDAO -> {
            adresList.add(adresMapper.map(adresDAO));
        });

        return adresList;
    }

    // Helper method to check if a Person already exists in the list
    private boolean personExistsInList(List<PersonDTO> personList, PersonDTO personToCheck) {
        for (PersonDTO person : personList) {
            if (person.equals(personToCheck)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Create a new Adres with possible existing persons
     *
     * @param override
     * @param adresBody
     *
     * @return the updated adres
     */
    public Adres postAdres(Boolean override, final AdresBody adresBody) {
        AdresDTO adresDTO = new AdresDTO(adresBody);

        try {
            if (!override) {
                Optional<AdresDTO> optionalAdresDTO = adresRepository.findByHash(adresDTO.getHash());
                if (optionalAdresDTO.isPresent()) {
                    throw new AdresExistsException("Adres " + adresDTO + " already exists cannot insert again");
                }
            }

            List<Person> inputPersons = adresBody.getPersons();
            List<PersonDTO> outputPersons = new ArrayList<>();

            inputPersons.forEach(person -> {
                if (person.getId() != null) {
                    Optional<PersonDTO> optionalPersonDTO = personRepository.findById(person.getId());
                    if (optionalPersonDTO.isPresent()) {
                        log.info("Person: {} already exists", person);
                        PersonDTO personDTO = optionalPersonDTO.get();
                        outputPersons.add(personDTO);
                    } else {
                        log.error("This person with id {} should exists - person {}", person.getId(), person);
                    }
                } else {
                    log.info("Person: {} not exists", person);
                    PersonDTO personDTO = personMapper.map(person);
                    personDTO.getAdresses().add(adresDTO);
                    personRepository.save(personDTO);
                    outputPersons.add(personDTO);
                }
            });

            adresDTO.setPersons(new ArrayList<>());

            outputPersons.forEach(person -> {
                person.getAdresses().add(adresDTO);
                adresDTO.getPersons().add(person);
            });

            adresRepository.save(adresDTO);

            return adresMapper.map(adresDTO); // Return 201 Created with the created entity
        } catch (Error e) {
            log.error("Error inserting adres: {}", e.toString());
            throw e;
        }
    }

    /**
     * Update an existing adres
     *
     * @param adres with a personlist. Each person in the list should exist!!
     *
     * @return the updated adres
     * [TODO] Finish code
     */
    public Adres updateAdres(final Adres adres) {

        try {

            Optional<AdresDTO> optionalAdresDTO = adresRepository.findById(adres.getId());
            if (optionalAdresDTO.isEmpty()) {
                throw new AdresNotExistsException("Adres " + adres.toString() + " does not exist");
            }

            AdresDTO adresDTO = optionalAdresDTO.get();        // list with exising persons

            List<Person> existingPersons = adres.getPersons(); // existing in input
            List<PersonDTO> newPersons = new ArrayList<>();    // found based on input

            existingPersons.forEach(person -> {
                if (person.getId() != null) {
                    Optional<PersonDTO> optionalPersonDTO = personRepository.findById(person.getId());
                    if (optionalPersonDTO.isPresent()) {
                        log.info("Person: {} already exists", person);
                        PersonDTO personDTO = optionalPersonDTO.get();
                        newPersons.add(personDTO);
                    } else {
                        log.error("This person with id {} should exists - person {}", person.getId(), person);
                    }
                } else {
                    log.error("Person: {} not exists == should not occur", person);
                    /*
                    PersonDTO personDTO = personMapper.map(person);
                    personDTO.getAdresses().add(adresDTO);
                    personRepository.save(personDTO);
                    newPersons.add(personDTO);
                     */
                }
            });

            adresDTO.setPersons(new ArrayList<>());

            newPersons.forEach(person -> {
                //
                // if person not in original list
                // add person
                // else skip person
                if (!personExistsInList(adresDTO.getPersons().stream().toList(), person)) {
                    person.getAdresses().add(adresDTO);
                    adresDTO.getPersons().add(person);
                }
            });

            // remove persons not in newpersons list from original list
            adresDTO.getPersons().forEach(person -> {
                if (!personExistsInList(newPersons.stream().toList(), person)) {

                }
            });

            adresRepository.save(adresDTO);

            return adresMapper.map(adresDTO); // Return 201 Created with the created entity
        } catch (Error e) {
            log.error("Error inserting adres: {}", e.toString());
            throw e;
        }
    }

    public Adres patch(Long adresId, final AdresBody adresBody) {

        Optional<AdresDTO> optionalAdresDAO = adresRepository.findByAdresId(adresId);
        if (!optionalAdresDAO.isPresent()) {
            throw new AdresNotExistsException("Adres with id " + adresId + " not found");
        }
        AdresDTO foundAdres = optionalAdresDAO.get();
        if (adresBody.getStreet() != null) {
            foundAdres.setStreet(adresBody.getStreet());
        }
        if (adresBody.getHousenumber() != null) {
            foundAdres.setHousenumber(adresBody.getHousenumber());
        }
        if (adresBody.getZipcode() != null) {
            foundAdres.setZipcode(adresBody.getZipcode());
        }
        if (adresBody.getCity() != null) {
            foundAdres.setCity(adresBody.getCity());
        }

        foundAdres.setHash(foundAdres.genHash());

        adresRepository.save(foundAdres);

        return adresMapper.map(foundAdres);

    }

    public Page<Person> getAdresPersonPage(Long adresId, final PageRequest pageRequest) {
        Page<PersonDTO> foundAdresPersonPage = personRepository.findAllByAdresIdAndPaged(adresId, pageRequest);

        List<Person> personList = new ArrayList<>();
        personList = foundAdresPersonPage.getContent().stream()
                .map(personMapper::map) // Apply mapper to each PersonDTO
                .toList();

        return new PageImpl<>(personList, foundAdresPersonPage.getPageable(), foundAdresPersonPage.getTotalElements());
    }

    public AdresPerson getAdresPerson(Long adresId) {
        Optional<AdresDTO> adresDAO = adresRepository.findByAdresId(adresId);
        if (!adresDAO.isPresent()) {
            throw new AdresNotExistsException("Adres with id: " + adresId + " not found");
        }
        List<Long> personIds = new ArrayList<Long>();
        List<PersonDTO> personList = personRepository.findPersonsByAdresId(adresId);

        personList.forEach(personDAO -> {
            personIds.add(personDAO.getId());
        });

        AdresPerson adresPerson = new AdresPerson();
        adresPerson.setId(adresDAO.get().getId());
        adresPerson.setStreet(adresDAO.get().getStreet());
        adresPerson.setHousenumber(adresDAO.get().getHousenumber());
        adresPerson.setZipcode(adresDAO.get().getZipcode());
        adresPerson.setCity(adresDAO.get().getCity());
        adresPerson.setPersons(personIds);

        return adresPerson;
    }

}
