package com.bsoft.adres;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("${activeProfile}")
@SpringBootTest
class AdresApplicationTests {

    @DisplayName("contextLoads")
    @Test
    void contextLoads() {
    }

}
