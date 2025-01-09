package com.bsoft.adres.database;

import com.bsoft.adres.generated.model.Adres;
import com.bsoft.adres.generated.model.AdresBody;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "adres", schema = "public", catalog = "adres")
public class AdresDAO implements Serializable {
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

    public AdresDAO() {
    }

    public AdresDAO(Long id, String street, String housenumber, String zipcode, String city) {
        this.id = id;
        this.street = street;
        this.housenumber = housenumber;
        this.zipcode = zipcode;
        this.city = city;
        this.hash = hashCode();
    }

    public AdresDAO(final Adres adres) {
        this.id = adres.getId();
        this.street = adres.getStreet();
        this.housenumber = adres.getHousenumber();
        this.zipcode = adres.getZipcode();
        this.city = adres.getCity();
        this.hash = this.hashCode();
    }

    public AdresDAO(final AdresBody adres) {
        this.id = null;
        this.street = adres.getStreet();
        this.housenumber = adres.getHousenumber();
        this.zipcode = adres.getZipcode();
        this.city = adres.getCity();
        this.hash = this.hashCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHousenumber() {
        return housenumber;
    }

    public void setHousenumber(String housenumber) {
        this.housenumber = housenumber;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getHash() {
        return hash;
    }

    public void setHash(Integer hash) {
        this.hash = hash;
    }

    public Integer genHash() {
        this.hash = hashCode();
        return this.hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AdresDAO adresDAO = (AdresDAO) o;
        return Objects.equals(street, adresDAO.street) && Objects.equals(housenumber, adresDAO.housenumber) && Objects.equals(zipcode, adresDAO.zipcode) && Objects.equals(city, adresDAO.city);
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
