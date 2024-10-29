package com.bsoft.adres.controller;

import com.bsoft.adres.generated.api.AdressesApi;
import com.bsoft.adres.generated.model.Adres;
import com.bsoft.adres.generated.model.AdresBody;
import com.bsoft.adres.generated.model.Deleted;
import com.bsoft.adres.service.AdresService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.ErrorResponseException;

import java.net.URI;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdresController implements AdressesApi {

    private final AdresService adresService;
    private final String hostname = "localhost";

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
        try {
            Adres adres = adresService.postAdres(adresBody); // Call the service method
            return ResponseEntity.status(HttpStatus.CREATED).body(adres); // Return 201 Created with the created entity
        } catch (Exception e) {
            ProblemDetail pd = ProblemDetail
                    .forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                            "Null Pointer Exception");
            pd.setType(URI.create("http://my-app-host.com/errors/npe"));
            pd.setTitle("Null Pointer Exception");
            pd.setProperty("hostname", hostname);
            throw new ErrorResponseException(HttpStatus.NOT_FOUND, pd, e);
        }
    }
}
