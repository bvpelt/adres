package com.bsoft.adres.service;

import com.bsoft.adres.database.AdresDAO;
import com.bsoft.adres.exceptions.AdresExistsException;
import com.bsoft.adres.exceptions.AdresNotExistsException;
import com.bsoft.adres.generated.model.Adres;
import com.bsoft.adres.generated.model.AdresBody;
import com.bsoft.adres.repositories.AdresRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AdresService {

    private final AdresRepository adresRepository;

    @Autowired
    public AdresService(final AdresRepository adresRepository) {
        this.adresRepository = adresRepository;
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
            log.error("Delete adres failed", e);
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

    public ResponseEntity<Adres> patchAdres(Long adresId, AdresBody adresBody) {
        return null;
    }

    public Adres postAdres(AdresBody adresBody) {
        AdresDAO adresDAO = new AdresDAO(adresBody);

        try {
            Optional<AdresDAO> optionalAdresDAO = adresRepository.findByHash(adresDAO.getHash());
            if (optionalAdresDAO.isPresent()) {
                throw new AdresExistsException("Adres " + adresDAO + " already exists cannot insert again");
            }

            adresRepository.save(adresDAO);
            Adres adres = AdresDAO2Adres(adresDAO);

            return adres; // Return 201 Created with the created entity
        } catch (Error e) {
            log.error("Error inserting adres: {}", e);
            throw e;
        }
    }

    public Adres patch(Long adresId, AdresBody adresBody) {
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

            adresRepository.save(foundAdres);

            adres = AdresDAO2Adres(foundAdres);
            return adres;
        } catch (Error e) {
            throw e;
        }

    }

    private Adres AdresDAO2Adres(AdresDAO adresDAO) {
        Adres adres = new Adres();
        adres.setAdresId(adresDAO.getAdresid());
        adres.setStreet(adresDAO.getStreet());
        adres.setHousenumber(adresDAO.getHousenumber());
        adres.setZipcode(adresDAO.getZipcode());
        adres.setCity(adresDAO.getCity());
        return adres;
    }
}
