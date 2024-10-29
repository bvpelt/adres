package com.bsoft.adres.database;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.bsoft.adres.generated.model.Adres;
import com.bsoft.adres.generated.model.AdresBody;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "adres", schema = "public", catalog = "adres")
public class AdresDAO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adresId;
    @Column(name = "street")
    private String street;
    @Column(name = "housenumber")
    private String housenumber;
    @Column(name = "zipcode")
    private String zipcode;
    @Column(name = "city")
    private String city;

    public AdresDAO(final Adres adres) {
        this.adresId = adres.getAdresId();
        this.city = adres.getCity();
        this.housenumber = adres.getHousenumber();
        this.street = adres.getStreet();
        this.zipcode = adres.getZipcode();
    }

    public AdresDAO(final AdresBody adres) {
        this.city = adres.getCity();
        this.housenumber = adres.getHousenumber();
        this.street = adres.getStreet();
        this.zipcode = adres.getZipcode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdresDAO adresDAO)) return false;

        if (!getStreet().equals(adresDAO.getStreet())) return false;
        if (!getHousenumber().equals(adresDAO.getHousenumber())) return false;
        if (getZipcode() != null ? !getZipcode().equals(adresDAO.getZipcode()) : adresDAO.getZipcode() != null)
            return false;
        return getCity().equals(adresDAO.getCity());
    }

    @Override
    public int hashCode() {
        int result = getStreet().hashCode();
        result = 31 * result + getHousenumber().hashCode();
        result = 31 * result + (getZipcode() != null ? getZipcode().hashCode() : 0);
        result = 31 * result + getCity().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AdresDAO{" +
                "adresId=" + adresId +
                ", street='" + street + '\'' +
                ", housenumber='" + housenumber + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
