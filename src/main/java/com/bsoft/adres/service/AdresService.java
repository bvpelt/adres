package com.bsoft.adres.service;

import com.bsoft.adres.database.AdresDAO;
import com.bsoft.adres.generated.model.Adres;
import com.bsoft.adres.generated.model.AdresBody;
import com.bsoft.adres.generated.model.Deleted;
import com.bsoft.adres.repositories.AdresRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AdresService {

    private AdresRepository adresRepository;

    public ResponseEntity<Deleted> deleteAdres(Long adresId) {
        return null;
    }

    public ResponseEntity<Adres> getAdres(Long adresId) {
        return null;
    }

    public ResponseEntity<List<Adres>> getAdresses() {
        return null;
    }

    public ResponseEntity<Adres> patchAdres(Long adresId, AdresBody adresBody) {
        return null;
    }

    public Adres postAdres(AdresBody adresBody) {
        AdresDAO adresDAO = new AdresDAO(adresBody);

        // Save entry
        try {
            adresRepository.save(adresDAO);
            Adres adres = new Adres();
            adres.setAdresId(adresDAO.getAdresId());
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
