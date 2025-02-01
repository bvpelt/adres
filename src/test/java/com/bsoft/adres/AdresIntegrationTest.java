package com.bsoft.adres;

import com.bsoft.adres.database.AdresDTO;
import com.bsoft.adres.repositories.AdresRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@ActiveProfiles("${activeProfile}")
@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = AdresApplication.class)
@AutoConfigureMockMvc
@Rollback(true)
public class AdresIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private AdresRepository adresRepository;

    private final String street = "Kerkewijk";

    @BeforeEach
    public void setUp() {
        // Perform setup actions here, e.g.,
        // - Create test data
        // - Configure external services
        // - Initialize test variables

        createTestAdres(street);
    }

    @AfterEach
    public void tearDown() {
        // Perform teardown actions here, e.g.,
        // - Delete test data
        // - Reset external services
        // - Clear test variables

    }

    @Transactional
    @Test
    public void givenAdres_whenGetAdres_thenStatus200()
            throws Exception {

        mvc.perform(get("/adres/api/v1/adresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-api-key", "f0583805-03f6-4c7f-8e40-f83f55b7c077"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].street").value(street))
        ;

    }

    @Transactional
    @Test
    public void notExistendAdres_whenGetAdres_thenStatus404()
            throws Exception {

        mvc.perform(get("/adres/api/v1/adresses/1001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-api-key", "f0583805-03f6-4c7f-8e40-f83f55b7c077"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title").value("Adres not found"))
        ;
    }

    private void createTestAdres(String street) {
        log.info("Creating test adresses");
        AdresDTO adresDTO = new AdresDTO();
        AdresDTO savedAdresDTO;

        adresDTO.setStreet(street);
        adresDTO.setCity("Berlin");
        adresDTO.setHousenumber("23");
        adresDTO.setZipcode("4903 AB");
        adresDTO.genHash();
        savedAdresDTO =adresRepository.save(adresDTO);
        log.info("Created test adres: {}", savedAdresDTO);

        adresDTO = new AdresDTO();

        adresDTO.setStreet(street);
        adresDTO.setCity("London");
        adresDTO.setHousenumber("23");
        adresDTO.setZipcode("4903 AB");
        adresDTO.genHash();
        savedAdresDTO = adresRepository.save(adresDTO);
        log.info("Created test adres: {}", savedAdresDTO);
    }

}
