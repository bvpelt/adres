create table adres_person
(
    id       bigint not null auto_increment primary key,
    adresid  bigint not null,
    personid bigint not null
);

create index ap_adres_ix on adres_person (adresid);
create index ap_person_ix on adres_person (personid);
