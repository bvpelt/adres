package com.bsoft.adres.database;

import com.bsoft.adres.generated.model.Adres;
import com.bsoft.adres.generated.model.AdresBody;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "adres", schema = "public", catalog = "adres")
public class AdresDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "street")
    private String street;
    @Column(name = "housenumber")
    private String housenumber;
    @Column(name = "zipcode")
    private String zipcode;
    @Column(name = "city")
    private String city;
    @Column(name = "hash")
    private Integer hash;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // owning site
    @JoinTable(
            name = "adres_person",
            joinColumns = {
                    @JoinColumn(name = "adresid", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "personid", referencedColumnName = "id")
            }
    )
    private Collection<PersonDTO> persons = new ArrayList<>();

    public AdresDTO(Long id, String street, String housenumber, String zipcode, String city) {
        this.id = id;
        this.street = street;
        this.housenumber = housenumber;
        this.zipcode = zipcode;
        this.city = city;
        this.hash = hashCode();
        this.persons = new ArrayList<>();
    }

    public AdresDTO(final Adres adres) {
        this.id = adres.getId();
        this.street = adres.getStreet();
        this.housenumber = adres.getHousenumber();
        this.zipcode = adres.getZipcode();
        this.city = adres.getCity();
        this.hash = this.hashCode();
        this.persons = new ArrayList<>();

        if (adres.getPersons() != null) {
            adres.getPersons().forEach(p -> {
                PersonDTO personDTO = new PersonDTO(p);
                //personDTO.getAdresses().add(this);
                this.persons.add(personDTO);
            });
        }
    }

    public AdresDTO(final AdresBody adres) {
        //this.id = null;
        this.street = adres.getStreet();
        this.housenumber = adres.getHousenumber();
        this.zipcode = adres.getZipcode();
        this.city = adres.getCity();
        this.hash = this.hashCode();
        this.persons = new ArrayList<>();

        if (adres.getPersons() != null) {
            adres.getPersons().forEach(p -> {
                PersonDTO personDTO = new PersonDTO(p);
                this.persons.add(personDTO);
            });
        }
    }

    public void addPerson(PersonDTO person) {
        persons.add(person);
    }

    public void setPersons(Collection<PersonDTO> persons) {
        if (persons.isEmpty()) {
            this.persons = new ArrayList<>();
        } else {
            persons.forEach(person -> {
                person.addAdres(this);
                this.persons.add(person);
            });
        }
    }

    public Integer genHash() {
        this.hash = hashCode();
        return this.hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AdresDTO adresDTO = (AdresDTO) o;
        return Objects.equals(street, adresDTO.street) && Objects.equals(housenumber, adresDTO.housenumber) && Objects.equals(zipcode, adresDTO.zipcode) && Objects.equals(city, adresDTO.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, housenumber, zipcode, city);
    }

    @Override
    public String toString() {
        return "AdresDAO{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", housenumber='" + housenumber + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", city='" + city + '\'' +
                ", hash=" + hash +
                '}';
    }
}
