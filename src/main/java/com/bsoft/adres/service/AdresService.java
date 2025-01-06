package com.bsoft.adres.service;

import com.bsoft.adres.database.AdresDAO;
import com.bsoft.adres.database.AdresMapper;
import com.bsoft.adres.database.PersonDAO;
import com.bsoft.adres.exceptions.AdresExistsException;
import com.bsoft.adres.exceptions.AdresNotExistsException;
import com.bsoft.adres.generated.model.Adres;
import com.bsoft.adres.generated.model.AdresBody;
import com.bsoft.adres.generated.model.AdresPerson;
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
public class AdresService {
    private final AdresRepository adresRepository;
    private final PersonRepository personRepository;
    private final AdresMapper adresMapper;

    public void deleteAll() {
        try {
            adresRepository.deleteAll();
        } catch (Exception e) {
            log.error("Deleting all adresses failed: {}", e);
        }
    }

    public boolean deleteAdres(Long adresId) {
        boolean deleted = false;
        try {
            Optional<AdresDAO> optionalAdresDAO = adresRepository.findByAdresId(adresId);
            if (!optionalAdresDAO.isPresent()) {
                throw new AdresNotExistsException("Adres with id " + adresId + " not found and not deleted");
            }
            adresRepository.deleteById(adresId);
            deleted = true;
        } catch (Exception e) {
            log.error("Delete adres failed: {}", e);
            throw e;
        }
        return deleted;
    }

    public Adres getAdres(Long adresId) {
        Optional<AdresDAO> optionalAdresDAO = adresRepository.findByAdresId(adresId);
        if (!optionalAdresDAO.isPresent()) {
            throw new AdresNotExistsException("Adres with id " + adresId + " not found");
        }
        Adres adres = AdresDAO2Adres(optionalAdresDAO.get());
        return adres;
    }

    public List<Adres> getAdresses() {
        List<Adres> result = new ArrayList<Adres>();
        Iterable<AdresDAO> iadres = adresRepository.findAll();

        iadres.forEach(adresDAO -> {
            Adres newAdres = AdresDAO2Adres(adresDAO);
            result.add(newAdres);
        });

        return result;
    }


    public Page<Adres> getAdressesPage(final PageRequest pageRequest) {

        Page<AdresDAO> foundAdresPage = adresRepository.findAllByPage(pageRequest);

        List<Adres> adresList = new ArrayList<>();
        adresList = foundAdresPage.getContent().stream()
                .map(adresMapper::map) // Apply mapper to each AdresDAO
                .toList();

        return new PageImpl<>(adresList, foundAdresPage.getPageable(), foundAdresPage.getTotalElements());
    }

    public List<Adres> getAdresses(final PageRequest pageRequest) {
        List<Adres> adresList = new ArrayList<Adres>();
        Iterable<AdresDAO> adresDAOIterable = adresRepository.findAllByPaged(pageRequest);

        adresDAOIterable.forEach(adresDAO -> {
            adresList.add(AdresDAO2Adres(adresDAO));
        });

        return adresList;
    }

    public Adres postAdres(Boolean override, final AdresBody adresBody) {
        AdresDAO adresDAO = new AdresDAO(adresBody);

        try {
            if (!override) {
                Optional<AdresDAO> optionalAdresDAO = adresRepository.findByHash(adresDAO.getHash());
                if (optionalAdresDAO.isPresent()) {
                    throw new AdresExistsException("Adres " + adresDAO + " already exists cannot insert again");
                }
            }

            adresRepository.save(adresDAO);
            Adres adres = AdresDAO2Adres(adresDAO);

            return adres; // Return 201 Created with the created entity
        } catch (Error e) {
            log.error("Error inserting adres: {}", e);
            throw e;
        }
    }

    public Adres patch(Long adresId, final AdresBody adresBody) {
        Adres adres = new Adres();

        try {
            Optional<AdresDAO> optionalAdresDAO = adresRepository.findByAdresId(adresId);
            if (!optionalAdresDAO.isPresent()) {
                throw new AdresNotExistsException("Adres with id " + adresId + " not found");
            }
            AdresDAO foundAdres = optionalAdresDAO.get();
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

            foundAdres.setHash(foundAdres.getHash());

            adresRepository.save(foundAdres);

            adres = AdresDAO2Adres(foundAdres);
            return adres;
        } catch (Error e) {
            throw e;
        }

    }

    private Adres AdresDAO2Adres(final AdresDAO adresDAO) {
        Adres adres = new Adres();
        adres.setId(adresDAO.getId());
        adres.setStreet(adresDAO.getStreet());
        adres.setHousenumber(adresDAO.getHousenumber());
        adres.setZipcode(adresDAO.getZipcode());
        adres.setCity(adresDAO.getCity());

        return adres;
    }


    public AdresPerson getAdresPerson(Long adresId) {
        Optional<AdresDAO> adresDAO = adresRepository.findByAdresId(adresId);
        if (!adresDAO.isPresent()) {
            throw new AdresNotExistsException("Adres with id: " + adresId + " not found");
        }
        List<Long> personIds = new ArrayList<Long>();
        List<PersonDAO> personList = personRepository.findPersonsByAdresId(adresId);

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
