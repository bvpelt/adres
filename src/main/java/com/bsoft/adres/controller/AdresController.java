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
//        return null;
// }
//    @Override
//    public ResponseEntity<Void> _deleteAdres(Long adresId, String xApiKey, String authorization) {
        log.debug("_deleteAdres apikey: {} authorization: {}", X_API_KEY);
        boolean deleted = adresService.deleteAdres(id);
        if (!deleted) {
            throw new AdresNotDeletedException("Adres not deleted");
        }

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }



    @Override
    public ResponseEntity<Void> _deleteAllAdreses(String X_API_KEY) {
//        return null;
//    }

//    @Override
//    public ResponseEntity<Void> _deleteAllAdreses(String xApiKey, String authorization) {
        log.debug("_deleteAllAdreses apikey: {} authorization: {}", X_API_KEY);
        adresService.deleteAll();
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Adres> _getAdres(Long adresId, String xApiKey) {
        log.debug("_getAdres apikey: {}", xApiKey);
        Adres adres = adresService.getAdres(adresId);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Version", version)
                .body(adres); // Return 201 Created with the created entity
    }

    @Override
    public ResponseEntity<AdresPerson> _getAdresPerons(Long adresId, String xApiKey) {
        log.debug("_getAdresPerons apikey: {}", xApiKey);
        AdresPerson adresPerson = adresService.getAdresPerson(adresId);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Version", version)
                .body(adresPerson); // Return 201 Created with the created entity
    }

    @Override
    public ResponseEntity<List<Adres>> _getAdresses(Integer page, Integer size, String X_API_KEY, String sort ) {
        log.debug("_getAdresses apikey: {}", X_API_KEY);
        List<Sort.Order> sortParameter;
        PageRequest pageRequest;
        log.info("Get adresses for pagenumber: {} pagesize: {}, sort: {}, api-key: {}", page, size, sort, X_API_KEY);
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
//        return null;
//    }

//    @Override
//    public ResponseEntity<Adres> _patchAdres(Long adresId, String xApiKey, String authorization, AdresBody adresBody) {
        log.debug("_patchAdres apikey: {} authorization: {}", X_API_KEY);
        Adres adres = adresService.patch(id, adresBody);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Version", version)
                .body(adres); // Return 201 Created with the created entity

    }

    @Override
    public ResponseEntity<Adres> _postAdres(Boolean override, String X_API_KEY, AdresBody adresBody) {
//        return null;
//    }

//    @Override
//    public ResponseEntity<Adres> _postAdres(Boolean override, String X_API_KEY, String authorization, AdresBody adresBody) {
        log.debug("_postAdres apikey: {} authorization: {}", X_API_KEY);
        if (override == null) {
            override = false;
        }
        Adres adres = adresService.postAdres(override, adresBody); // Call the service method
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Version", version)
                .body(adres); // Return 201 Created with the created entity
    }
}
