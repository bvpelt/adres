package com.bsoft.adres;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@SpringBootTest
public class PatternTests {

    private final Pattern adresPattern = Pattern.compile("^(/adresses)");
    private final Pattern personsPattern = Pattern.compile("^(/persons)");
    private final Pattern usersPattern = Pattern.compile("^(/users)");
    private final Pattern rolesPattern = Pattern.compile("^(/roles)");

    /*
    /adresses
    /persons
    /users
    /roles
     */
    @Test
    public void test01() {

        ArrayList<String> testValues = new ArrayList<String>();
        testValues.add("/adresses");
        testValues.add("/adresses/id");
        testValues.add("/persons");
        testValues.add("/persons/id");
        testValues.add("/users");
        testValues.add("/users/myvalue");
        testValues.add("/roles");
        testValues.add("/roles/test");
        testValues.add("/login");
        testValues.add("/actuator");
        testValues.add("/swagger_ui/index.html");
        AtomicReference<Matcher> m = new AtomicReference<>();


        testValues.forEach(testvalue -> {
            boolean found = false;

            log.info("Start testing value: {}", testvalue);
            m.set(adresPattern.matcher(testvalue));

            if (m.get().find()) {
                found = true;
                final String s = m.get().group();
                log.info("Found: {} in testvalue: {}", s, testvalue);
            }
            if (!found) {
                m.set(personsPattern.matcher(testvalue));
                if (m.get().find()) {
                    found = true;
                    final String s = m.get().group();
                    log.info("Found: {} in testvalue: {}", s, testvalue);
                }
            }
            if (!found) {
                m.set(usersPattern.matcher(testvalue));
                if (m.get().find()) {
                    found = true;
                    final String s = m.get().group();
                    log.info("Found: {} in testvalue: {}", s, testvalue);
                }
            }
            if (!found) {

                m.set(rolesPattern.matcher(testvalue));
                if (m.get().find()) {
                    found = true;
                    final String s = m.get().group();
                    log.info("Found: {} in testvalue: {}", s, testvalue);
                }
            }
            log.info("Start testing value: {} -- result: {}\n", testvalue, found);

        });


    }
}
