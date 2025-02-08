package com.bsoft.adres.controller;

import io.swagger.v3.core.util.Json;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class AdresErrorController implements ErrorController {


    @RequestMapping(value = "/error", produces = {"text/plain", "application/json"})
    public ResponseEntity<Map<String, Object>> handleError(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        // getAttributes(request);

        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("status", status.value());
        errorAttributes.put("error", status.getReasonPhrase());
        errorAttributes.put("message", "An error occurred.");
        log.error("errorAttributes: {}", Json.pretty(errorAttributes));
        return new ResponseEntity<>(errorAttributes, status);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        log.info("===== statusCode =======: {}", statusCode);
        if (statusCode != null) {
            return HttpStatus.valueOf(statusCode);
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private void getAttributes(HttpServletRequest request) {
        request.getAttributeNames().asIterator().forEachRemaining(name -> {
            boolean handled = false;
            Object object = request.getAttribute(name);
            if (object != null) {
                if (object instanceof String value) {
                    log.info("===== attribute: {} =======: {}", name, value);
                    handled = true;
                }
                if (object instanceof Integer value) {
                    log.info("===== attribute: {} =======: {}", name, value);
                    handled = true;
                }
                if (object instanceof Boolean value) {
                    log.info("===== attribute: {} =======: {}", name, value);
                    handled = true;
                }
                if (!handled) {
                    log.info("===== object type: {}", object.getClass().getName());
                }
            } else {
                log.info("===== attribute name: {}", name);
            }
        });
    }

}
