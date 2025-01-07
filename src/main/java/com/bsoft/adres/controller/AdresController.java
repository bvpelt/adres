package com.bsoft.adres.controller;

import com.bsoft.adres.exceptions.AdresNotDeletedException;
import com.bsoft.adres.exceptions.InvalidParameterException;
import com.bsoft.adres.generated.api.AdressesApi;
import com.bsoft.adres.generated.model.Adres;
import com.bsoft.adres.generated.model.AdresBody;
import com.bsoft.adres.generated.model.AdresPerson;
import com.bsoft.adres.generated.model.PagedAdresses;
import com.bsoft.adres.service.AdresService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("${application.basePath}")
@Controller
public class AdresController implements AdressesApi {

    private final AdresService adresService;

    @Value("${info.project.version}")
    private String version;
/*
    @Value("${application.basePath}")
    private String basePath;
*/
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
    public ResponseEntity<PagedAdresses> _getAdresses(Integer page, Integer size, List<String> sort, String X_API_KEY) {
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
        if (sort != null && !sort.isEmpty()) {
            List<Sort.Order> orders = ControllerSortUtil.getSortOrder(sort);
            pageRequest = PageRequest.of(page - 1, size, Sort.by(orders));
        } else {
            pageRequest = PageRequest.of(page - 1, size);
        }

        PagedAdresses pageResponse = new PagedAdresses();
        Page<Adres> adresPage = adresService.getAdressesPage(pageRequest);
        pageResponse.setContent(adresPage.getContent());
        pageResponse.setPageNumber(adresPage.getNumber() + 1);
        pageResponse.setPageSize(adresPage.getSize());
        pageResponse.setTotalElements(BigDecimal.valueOf(adresPage.getTotalElements()));
        pageResponse.setTotalPages(adresPage.getTotalPages());


        return ResponseEntity.status(HttpStatus.OK)
                .header("Version", version)
                .body(pageResponse);
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
