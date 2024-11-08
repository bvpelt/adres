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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "adres", schema = "public", catalog = "adres")
public class AdresDAO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adresid")
    private Long adresid;
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

    public AdresDAO(final Adres adres) {
        this.adresid = adres.getAdresId();
        this.city = adres.getCity();
        this.housenumber = adres.getHousenumber();
        this.street = adres.getStreet();
        this.zipcode = adres.getZipcode();
        this.hash = this.hashCode();
    }

    public AdresDAO(final AdresBody adres) {
        this.adresid = null;
        this.city = adres.getCity();
        this.housenumber = adres.getHousenumber();
        this.street = adres.getStreet();
        this.zipcode = adres.getZipcode();
        this.hash = this.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdresDAO adresDAO)) return false;

        if (getStreet() != null ? !getStreet().equals(adresDAO.getStreet()) : adresDAO.getStreet() != null)
            return false;
        if (getHousenumber() != null ? !getHousenumber().equals(adresDAO.getHousenumber()) : adresDAO.getHousenumber() != null)
            return false;
        if (!getZipcode().equals(adresDAO.getZipcode())) return false;
        return getCity() != null ? getCity().equals(adresDAO.getCity()) : adresDAO.getCity() == null;
    }

    @Override
    public int hashCode() {
        int result = getStreet() != null ? getStreet().hashCode() : 0;
        result = 31 * result + (getHousenumber() != null ? getHousenumber().hashCode() : 0);
        result = 31 * result + getZipcode().hashCode();
        result = 31 * result + (getCity() != null ? getCity().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AdresDAO{" +
                "adresid=" + adresid +
                ", street='" + street + '\'' +
                ", housenumber='" + housenumber + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", city='" + city + '\'' +
                ", hash=" + hash +
                '}';
    }
}
