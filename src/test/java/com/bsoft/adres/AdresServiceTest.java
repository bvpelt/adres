package com.bsoft.adres;

import com.bsoft.adres.generated.model.Adres;
import com.bsoft.adres.generated.model.AdresBody;
import com.bsoft.adres.service.AdresService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@ActiveProfiles("${activeProfile}")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan("com.bsoft.adres")
@Rollback(true)
public class AdresServiceTest {

    @Autowired
    private AdresService adresService;

    @Transactional
    @Test
    public void createAdresTest() {
        AdresBody adresBody = new AdresBody();
        adresBody.setStreet("street");
        adresBody.setCity("city");
        adresBody.setHousenumber("housenumber");
        adresBody.setZipcode("zipcode");

        Adres adres = adresService.postAdres(false, adresBody);
        log.info("Saved adres: {}", adres);
    }

}
