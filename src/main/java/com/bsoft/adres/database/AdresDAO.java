package com.bsoft.adres.database;

import jakarta.persistence.*;

import java.io.Serializable;

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

}
