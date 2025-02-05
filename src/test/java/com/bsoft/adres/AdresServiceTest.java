package com.bsoft.adres;

import com.bsoft.adres.database.PersonDTO;
import com.bsoft.adres.generated.model.Adres;
import com.bsoft.adres.generated.model.AdresBody;
import com.bsoft.adres.generated.model.Person;
import com.bsoft.adres.repositories.PersonRepository;
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
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ActiveProfiles("${activeProfile}")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan("com.bsoft.adres")
@Rollback(true)
public class AdresServiceTest {

    @Autowired
    private AdresService adresService;
    @Autowired
    private PersonRepository personRepository;

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

    @Transactional
    @Test
    public void getAdresTest() {
        AdresBody adresBody = new AdresBody();
        adresBody.setStreet("street");
        adresBody.setCity("city");
        adresBody.setHousenumber("housenumber");
        adresBody.setZipcode("zipcode");

        Adres adres = adresService.postAdres(false, adresBody);
        log.info("Saved adres: {}", adres);

        Adres adres1 = adresService.getAdres(adres.getId());
        log.info("Retrieved adres: {}", adres);

        Assert.isTrue(adres.equals(adres1), "Adres not equal");
    }

    @Transactional
    @Test
    public void getNewAdresWithNewPersonTest() { // scenario 2

        AdresBody adresBody = new AdresBody();
        adresBody.setStreet("street");
        adresBody.setCity("city");
        adresBody.setHousenumber("housenumber");
        adresBody.setZipcode("zipcode");

        List<Person> persons = new ArrayList<>();

        LocalDate date = LocalDate.parse("2002-02-02");

        Person person = new Person();
        person.setFirstName("firstName");
        person.setInfix("infix");
        person.setLastName("lastName");
        person.setDateOfBirth(date);
        persons.add(person);

        adresBody.setPersons(persons);

        Adres adres = adresService.postAdres(false, adresBody);
        log.info("Saved adres: {}", adres);

        Adres adres1 = adresService.getAdres(adres.getId());
        log.info("Retrieved adres: {}", adres);
        Assert.isTrue(adres.equals(adres1), "Adres not equal");

    }

    @Transactional
    @Test
    public void getNewAdresWithNewAndExistingPersonTest() { // scenario 3

        AdresBody adresBody = new AdresBody();
        adresBody.setStreet("street");
        adresBody.setCity("city");
        adresBody.setHousenumber("housenumber");
        adresBody.setZipcode("zipcode");

        List<Person> persons = new ArrayList<>();

        LocalDate date = LocalDate.parse("2002-02-02");

        Person person1 = new Person();
        person1.setFirstName("firstName1");
        person1.setInfix("infix1");
        person1.setLastName("lastName1");
        person1.setDateOfBirth(date);
        PersonDTO personDTO1 = new PersonDTO(person1);

        personRepository.save(personDTO1);
        person1.setId(personDTO1.getId());

        Person person2 = new Person();
        person2.setFirstName("firstName2");
        person2.setInfix("infix2");
        person2.setLastName("lastName2");
        person2.setDateOfBirth(date);

        persons.add(person1);
        persons.add(person2);


        adresBody.setPersons(persons);

        Adres adres = adresService.postAdres(false, adresBody);
        log.info("Saved adres: {}", adres);

        Adres adres1 = adresService.getAdres(adres.getId());
        log.info("Retrieved adres: {}", adres);
        Assert.isTrue(adres.equals(adres1), "Adres not equal");

    }

}
