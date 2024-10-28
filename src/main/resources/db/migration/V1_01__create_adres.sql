create table adres
(
    adresId bigint not null primary key,
    street text not null,
    housenumber text not null,
    zipcode text,
    city text not null
);

ALTER TABLE adres
    OWNER TO testuser;

ALTER TABLE adres
ALTER
COLUMN adresId ADD GENERATED BY DEFAULT AS IDENTITY (
        SEQUENCE NAME adres_id_seq
        START WITH 1
        INCREMENT BY 1
        NO MINVALUE
        NO MAXVALUE
        CACHE 1
);