package com.bsoft.adres.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Slf4j
@Getter
@Setter
public class AdresNotExistsException extends RuntimeException {

    private final String host = "http://localhost:8080";
    /**
     * De URI-referentie die het type van het probleem identificeert. Dit is de link die clients kunnen volgen om meer informatie te krijgen over het specifieke fouttype. Als een gebruiker bijvoorbeeld probeert toegang te krijgen tot een resource die niet bestaat, kan het type verwijzen naar een URL zoals https://example.com/not-found, waar documentatie voor de fout kan worden gevonden.
     */
    public URI type;
    public AdresNotExistsException() {
        super("Adres not found");
        try {
            type = new URI(host + "/not-found");
        } catch (Exception e) {
            log.error("Error parsing uri from: {}", host + "/not-found");
        }
    }

    public AdresNotExistsException(final String message) {
        super(message);
        try {
            type = new URI(host + "/already-existed");
        } catch (Exception e) {
            log.error("Error parsing uri from: {}", host + "/already-existed");
        }
    }
}
