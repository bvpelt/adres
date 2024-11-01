package com.bsoft.adres.service;

import com.bsoft.adres.database.AdresDAO;
import com.bsoft.adres.exceptions.AdresExistsException;
import com.bsoft.adres.exceptions.AdresNotExistsException;
import com.bsoft.adres.generated.model.Adres;
import com.bsoft.adres.generated.model.AdresBody;
import com.bsoft.adres.generated.model.Deleted;
import com.bsoft.adres.repositories.AdresRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
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

    public ResponseEntity<Deleted> deleteAdres(Long adresId) {
        return null;
    }

    public Adres getAdres(Long adresId) {
        Optional<AdresDAO> optionalAdresDAO = adresRepository.findByAdresId(adresId);
        if (!optionalAdresDAO.isPresent()) {
            throw new AdresNotExistsException("Adres with id {}" + adresId + " not found");
        }
        Adres adres = new Adres();
        adres.setAdresId(optionalAdresDAO.get().getAdresid());
        adres.setStreet(optionalAdresDAO.get().getStreet());
        adres.setHousenumber(optionalAdresDAO.get().getHousenumber());
        adres.setZipcode(optionalAdresDAO.get().getZipcode());
        adres.setCity(optionalAdresDAO.get().getCity());

        return adres;
    }

    public List<Adres> getAdresses() {
        List<Adres> result = new ArrayList<Adres>();
        Iterable<AdresDAO> iadres = adresRepository.findAll();

        iadres.forEach(adresDAO -> {
            Adres newAdres = new Adres();
            newAdres.setAdresId(adresDAO.getAdresid());
            newAdres.setCity(adresDAO.getCity());
            newAdres.setHousenumber(adresDAO.getHousenumber());
            newAdres.setStreet(adresDAO.getStreet());
            newAdres.setZipcode(adresDAO.getZipcode());
            result.add(newAdres);
        });

        return result;
    }

    public ResponseEntity<Adres> patchAdres(Long adresId, AdresBody adresBody) {
        return null;
    }

    public Adres postAdres(AdresBody adresBody) {
        AdresDAO adresDAO = new AdresDAO(adresBody);

        // Save entry
        try {
            Optional<AdresDAO> optionalAdresDAO = adresRepository.findByHash(adresDAO.getHash());
            if (optionalAdresDAO.isPresent()) {
                throw new AdresExistsException("Adres " + adresDAO + " already exists cannot insert again");
            }

            adresRepository.save(adresDAO);
            Adres adres = new Adres();
            adres.setAdresId(adresDAO.getAdresid());
            adres.setStreet(adresDAO.getStreet());
            adres.setHousenumber(adresDAO.getHousenumber());
            adres.setZipcode(adresDAO.getZipcode());
            adres.setCity(adresDAO.getCity());

            return adres; // Return 201 Created with the created entity
        } catch (Error e) {
            log.error("Error inserting adres: {}", e);
            throw e;
        }
    }

}
