package com.bsoft.adres.api;

import com.bsoft.adres.service.AdresService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.bsoft.adres.generated.model.Adres;
import nl.bsoft.adres.generated.model.AdresBody;
import nl.bsoft.adres.generated.model.Deleted;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdresAPI implements nl.bsoft.adres.generated.api.AdressesApi {

    private final AdresService adresService;;

    @Override
    public ResponseEntity<Deleted> _deleteAdres(Long adresId) {
        return null;
    }

    @Override
    public ResponseEntity<Adres> _getAdres(Long adresId) {
        return null;
    }

    @Override
    public ResponseEntity<List<Adres>> _getAdresses() {
        return null;
    }

    @Override
    public ResponseEntity<Adres> _patchAdres(Long adresId, AdresBody adresBody) {
        return null;
    }

    @Override
    public ResponseEntity<Adres> _postAdres(AdresBody adresBody) {
        adresService.postAdres(adresBody);
        return null;
    }
}
