create table adres_person
(
    id       bigint not null primary key,
    adresid  bigint not null,
    personid bigint not null
);

create index ap_adresperson_ix on adres_person (adresid, personid);