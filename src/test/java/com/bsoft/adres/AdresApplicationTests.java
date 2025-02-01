package com.bsoft.adres;

import com.bsoft.adres.database.AdresDTO;
import com.bsoft.adres.database.ApiKeyDTO;
import com.bsoft.adres.database.UserDTO;
import com.bsoft.adres.service.ApiKeyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

@Slf4j
@ActiveProfiles("${activeProfile}")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class AdresApplicationTests {

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private final ApiKeyService apiKeyService = new ApiKeyService();

    @DisplayName("contextLoads")
    @Test
    void contextLoads() {
    }

    @DisplayName("testUsers")
    @Test
    public void testUsers() {
        ArrayList<UserDTO> users = new ArrayList<>();
        users.add(new UserDTO("admin", bCryptPasswordEncoder.encode("12345"), "admin@gmail.com", "0612345678"));
        users.add(new UserDTO("user", bCryptPasswordEncoder.encode("12345"), "user@gmail.com", "0634567812"));
        users.add(new UserDTO("bvpelt", bCryptPasswordEncoder.encode("12345"), "bvpelt@gmail.com", "0656781234"));
        users.add(new UserDTO("guest", bCryptPasswordEncoder.encode("12345"), "guest@gmail.com", "0678123456"));

        users.forEach(user -> {
            log.info(user.toString());
        });

    }

    @DisplayName("testAPIKeys")
    @Test
    public void testAPIKeys() {
        int maxKeys = 15;

        for (int i = 0; i < maxKeys; i++) {
            ApiKeyDTO apiKeyDTO = apiKeyService.generateApiKey();
            log.info("Generated api key: {}", apiKeyDTO.toString());
        }
    }

    @DisplayName("testAdresses")
    @Test
    public void testAdresses() {
        int maxAdresses = 15;
        ArrayList<AdresDTO> adresses = new ArrayList<>();

        for (int i = 0; i < maxAdresses; i++) {
            AdresDTO adressDao = new AdresDTO();
            adressDao.setStreet("Kerkewijk");
            adressDao.setHousenumber(Integer.toString(i));
            adressDao.setZipcode("3904 KL");
            adressDao.setCity("Veenendaal");
            adresses.add(adressDao);
            log.info("Generated adres: {}", adresses.get(i));
        }
    }
}
