create table adres
(
    id          bigint not null auto_increment primary key,
    street      text   not null,
    housenumber text   not null,
    zipcode     text,
    city        text   not null,
    hash        int
);

create unique index zipcodehousenr_ix on adres(zipcode, housenumber);
