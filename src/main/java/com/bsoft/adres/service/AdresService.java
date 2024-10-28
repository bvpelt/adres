package com.bsoft.adres.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdresService {

    public ResponseEntity<nl.bsoft.adres.generated.model.Deleted> deleteAdres(Long adresId) {
        return null;
    }

    public ResponseEntity<nl.bsoft.adres.generated.model.Adres> getAdres(Long adresId) {
        return null;
    }

    public ResponseEntity<List<nl.bsoft.adres.generated.model.Adres>> getAdresses() {
        return null;
    }

    public ResponseEntity<nl.bsoft.adres.generated.model.Adres> patchAdres(Long adresId, nl.bsoft.adres.generated.model.AdresBody adresBody) {
        return null;
    }

    public ResponseEntity<nl.bsoft.adres.generated.model.Adres> postAdres(nl.bsoft.adres.generated.model.AdresBody adresBody) {
        return null;
    }

}
