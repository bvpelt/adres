package com.bsoft.adres.controller;

import com.bsoft.adres.exceptions.AdresNotDeletedException;
import com.bsoft.adres.exceptions.InvalidParameterException;
import com.bsoft.adres.generated.api.AdressesApi;
import com.bsoft.adres.generated.model.Adres;
import com.bsoft.adres.generated.model.AdresBody;
import com.bsoft.adres.generated.model.AdresPerson;
import com.bsoft.adres.service.AdresService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/adres/api/v1")
@Controller
public class AdresController implements AdressesApi {

    private final AdresService adresService;

    @Value("${info.project.version}")
    private String version;

    @Override
    public ResponseEntity<Void> _deleteAdres(Long id, String X_API_KEY) {
        log.debug("_deleteAdres apikey: {}", X_API_KEY);
        boolean deleted = adresService.deleteAdres(id);
        if (!deleted) {
            throw new AdresNotDeletedException("Adres not deleted");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Version", version);

        ResponseEntity<Void> responseEntity = new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);

        return responseEntity;
    }

    @Override
    public ResponseEntity<Void> _deleteAllAdreses(String X_API_KEY) {
        log.debug("_deleteAllAdreses apikey: {}", X_API_KEY);
        adresService.deleteAll();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Version", version);

        ResponseEntity<Void> responseEntity = new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);

        return responseEntity;
    }

    @Override
    public ResponseEntity<Adres> _getAdres(Long adresId, String X_API_KEY) {
        log.debug("_getAdres apikey: {}", X_API_KEY);
        Adres adres = adresService.getAdres(adresId);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Version", version)
                .body(adres); // Return 201 Created with the created entity
    }

    @Override
    public ResponseEntity<AdresPerson> _getAdresPerons(Long adresId, String X_API_KEY) {
        log.debug("_getAdresPerons apikey: {}", X_API_KEY);
        AdresPerson adresPerson = adresService.getAdresPerson(adresId);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Version", version)
                .body(adresPerson); // Return 201 Created with the created entity
    }

    @Override
    public ResponseEntity<List<Adres>> _getAdresses(Integer page, Integer size, String sort, String X_API_KEY) {
        log.debug("_getAdresses apikey: {}", X_API_KEY);
        List<Sort.Order> sortParameter;
        PageRequest pageRequest;
        log.trace("_getAdresses pagenumber: {} pagesize: {}, sort: {}, api-key: {}", page, size, sort, "");
        // Validate input parameters
        if (page == null) {
            page = 1;
        }
        if (page < 1) {
            throw new InvalidParameterException("Page number must be greater than 0");
        }
        if (page == null) { // set to default
            page = 25;
        }
        if (page < 1) {
            throw new InvalidParameterException("Page size must be greater than 0");
        }
        //
        if (sort != null && !sort.isEmpty()) {
            //sortParameter = ControllerSortUtil.getSortOrder(sortBy);
            //pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(sortParameter));
            pageRequest = PageRequest.of(page - 1, size, Sort.by(sort));
        } else {
            pageRequest = PageRequest.of(page - 1, size);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .header("Version", version)
                .body(adresService.getAdresses(pageRequest));
    }

    @Override
    public ResponseEntity<Adres> _patchAdres(Long id, String X_API_KEY, AdresBody adresBody) {
        log.debug("_patchAdres apikey: {}", X_API_KEY);
        Adres adres = adresService.patch(id, adresBody);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Version", version)
                .body(adres); // Return 201 Created with the created entity
    }

    @Override
    public ResponseEntity<Adres> _postAdres(Boolean override, AdresBody adresBody, String X_API_KEY) {
        log.debug("_postAdres apikey: {}", X_API_KEY);
        if (override == null) {
            override = false;
        }
        Adres adres = adresService.postAdres(override, adresBody); // Call the service method

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Version", version)
                .body(adres); // Return 201 Created with the created entity
    }
}
