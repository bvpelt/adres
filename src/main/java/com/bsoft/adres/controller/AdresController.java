package com.bsoft.adres.controller;

import com.bsoft.adres.exceptions.InvalidParameterException;
import com.bsoft.adres.generated.api.AdressesApi;
import com.bsoft.adres.generated.model.Adres;
import com.bsoft.adres.generated.model.AdresBody;
import com.bsoft.adres.generated.model.Deleted;
import com.bsoft.adres.service.AdresService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdresController implements AdressesApi {

    private final AdresService adresService;

    @Override
    public ResponseEntity<Deleted> _deleteAdres(Long adresId) {

        boolean deleted = adresService.deleteAdres(adresId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Adres> _getAdres(Long adresId) {
        Adres adres = adresService.getAdres(adresId);
        return ResponseEntity.status(HttpStatus.OK).body(adres); // Return 201 Created with the created entity
    }

    @Override
    public ResponseEntity<List<Adres>> _getAdresses(Integer pageNumber, Integer pageSize, String sortBy) {
        List<Sort.Order> sortParameter;
        PageRequest pageRequest;
        log.info("Get adresses for pagenumber: {} pagesize: {}, sort: {}", pageNumber, pageSize, sortBy);
        // Validate input parameters
        if (pageNumber == null) {
            pageNumber = 1;
        }
        if (pageNumber < 1) {
            throw new InvalidParameterException("Page number must be greater than 0");
        }
        if (pageSize == null) { // set to default
            pageSize = 25;
        }
        if (pageSize < 1) {
            throw new InvalidParameterException("Page size must be greater than 0");
        }
        //
        if (sortBy != null && !sortBy.isEmpty()) {
            //sortParameter = ControllerSortUtil.getSortOrder(sortBy);
            //pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(sortParameter));
            pageRequest = PageRequest.of(pageNumber - 1, pageSize, Sort.by(sortBy));
        } else {
            pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        }
        return ResponseEntity.status(HttpStatus.OK).body(adresService.getAdresses(pageRequest));
    }

    @Override
    public ResponseEntity<Adres> _patchAdres(Long adresId, AdresBody adresBody) {
        Adres adres = adresService.patch(adresId, adresBody);
        return ResponseEntity.status(HttpStatus.OK).body(adres); // Return 201 Created with the created entity

    }

    @Override
    public ResponseEntity<Adres> _postAdres(Boolean override, AdresBody adresBody) {
        if (override == null) {
            override = false;
        }
        Adres adres = adresService.postAdres(override, adresBody); // Call the service method
        return ResponseEntity.status(HttpStatus.CREATED).body(adres); // Return 201 Created with the created entity
    }
}
