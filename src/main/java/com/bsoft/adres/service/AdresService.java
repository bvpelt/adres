package com.bsoft.adres.service;

import com.bsoft.adres.database.AdresDTO;
import com.bsoft.adres.database.PersonDTO;
import com.bsoft.adres.exceptions.AdresExistsException;
import com.bsoft.adres.exceptions.AdresNotExistsException;
import com.bsoft.adres.generated.model.Adres;
import com.bsoft.adres.generated.model.AdresBody;
import com.bsoft.adres.generated.model.AdresPerson;
import com.bsoft.adres.mappers.AdresMapper;
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

    public Adres postAdres(Boolean override, final AdresBody adresBody) {
        AdresDTO adresDTO = new AdresDTO(adresBody);

        try {
            if (!override) {
                Optional<AdresDTO> optionalAdresDAO = adresRepository.findByHash(adresDTO.getHash());
                if (optionalAdresDAO.isPresent()) {
                    throw new AdresExistsException("Adres " + adresDTO + " already exists cannot insert again");
                }
            }

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
