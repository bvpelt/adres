create table adres
(
    id          bigint not null primary key,
    street      text   not null,
    housenumber text   not null,
    zipcode     text,
    city        text   not null,
    hash        int
);

create unique index zipcodehousenr_ix on adres(zipcode, housenumber);

ALTER TABLE adres
ALTER
COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
        SEQUENCE NAME adres_id_seq
        START WITH 1
        INCREMENT BY 1
        NO MINVALUE
        NO MAXVALUE
        CACHE 1
);